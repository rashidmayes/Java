/*******************************************************************************
 * Copyright (c) 2009 Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors:
 * 
 * Astrient Foundation Inc. 
 * www.astrientfoundation.org
 * rashid@astrientfoundation.org
 * Rashid Mayes 2009
 *******************************************************************************/
package org.astrientfoundation.comm.impl;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.text.MessageFormat;

import javax.swing.text.EditorKit;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTMLEditorKit;

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.ConnectionFailedException;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.comm.MessagingException;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.Preferences;


public class AOLCauseway extends Causeway implements Runnable
{
    private static final byte XOR_BYTES[] = "Tic/Toc".getBytes();
    public static final int REST_TIME = 60 * 1000;
    public static final int SIGNON = 1;
    public static final int DATA = 2;

    private Thread thread = null;

    private String host;
    private int port;

    private String loginHost;
    private int loginPort;

    private short sequence;
    private Socket connection;
    private InputStream is;
    private DataOutputStream os;
    

    private boolean html;
    private Preferences aimErrors = new Preferences("comp.comm.impl.aimerrors");

    public AOLCauseway()
    {
        Preferences preferences = new Preferences("comp.comm.impl.aim");

        host = preferences.get("toc.host", "toc.oscar.aol.com");
        port = Integer.parseInt(preferences.get("toc.port", "5190"));
        loginHost = preferences.get("login.host", "login.oscar.aol.com");
        loginPort = Integer.parseInt(preferences.get("login.port", "5190"));
    }
    
    
    public AOLCauseway(String host, int pot, String loginHost, String loginPort)
    {
    	
    }


    protected void _connect() throws ConnectionFailedException
    {
        try
        {
            if (thread != null)
            {
                throw new ConnectionFailedException("Call disconnect first");
            }

            String handle = getHandle();

            String username = handle.substring(0, handle.indexOf(':'));
            String password = handle.substring(handle.indexOf(':') + 1);

            connection = new Socket(host, port);

            os = new DataOutputStream(connection.getOutputStream());
            os.write("FLAPON\r\n\r\n".getBytes());

            is = connection.getInputStream();
            read();

            int length = 8 + username.length();
            sequence++;
            os.write((byte) '*');
            os.write((byte) SIGNON);
            os.writeShort(sequence);
            os.writeShort(length);
            os.write(0);
            os.write(0);
            os.write(0);
            os.write(1);
            os.write(0);
            os.write(1);
            os.writeShort(username.length());
            os.write(username.getBytes());
            os.flush();

            
            int sn = (int)username.charAt(0) - 96;
            int pw = (int)password.charAt(0) - 96;
            int a = sn * 7696 + 738816;
            int b = sn * 746512;
            int c = pw * a;
            
            int code = c - a + b + 71665152;
            
            
            StringBuffer buffer = new StringBuffer("toc2_signon ");
            buffer.append(loginHost);
            buffer.append(" ");
            buffer.append(loginPort);
            buffer.append(" ");
            buffer.append(username);
            buffer.append(" "); 
            buffer.append(roastPassword(password));
            buffer.append(" ");
            buffer.append("english");
            buffer.append(" ");
            buffer.append("TIC:TOC2");
            buffer.append(" ");     
            buffer.append("160");
            buffer.append(" "); 
            buffer.append(code);

            write(DATA, buffer.toString());
            String rc = read();

            Message message = parse(rc);

            if (message.getFrom().equals("Error"))
            {
                throw new ConnectionFailedException("(" + rc + ")"  + String.valueOf(message.getContent()));
            }

            write(DATA, "toc_add_buddy " + username);
            write(DATA, "toc_init_done");
            write(DATA, "toc_set_caps 09461343-4C7F-11D1-8222-444553540000 09461348-4C7F-11D1-8222-444553540000");
            write(DATA, "toc_add_permit ");
            write(DATA, "toc_add_deny ");

            thread = new Thread(this);
            thread.start();
        }
        catch (Exception e)
        {
            throw new ConnectionFailedException(e);
        }

    }


    protected void _disconnect()
    {
        thread = null;

        try
        {
            connection.close();
        }
        catch (Exception e)
        {
        }
        try
        {
            is.close();
        }
        catch (Exception e)
        {
        }
        try
        {
            os.close();
        }
        catch (Exception e)
        {
        }
    }


    protected void _send(Message message) throws MessagingException
    {
        try
        {
            write(DATA, "toc_send_im " + message.getTo().toLowerCase().replaceAll(" ", "") + " \"" + escape(String.valueOf(message.getContent())) + "\"");
            message.notifyDelivered(this,null);
        }
        catch (Exception e)
        {
            message.notifyError(this,e);
            throw new MessagingException("Problems encountered while trying to send message", e);
        }
    }

    protected String roastPassword(String str)
    {
        StringBuffer buffer = new StringBuffer("0x");

        int xorIndex = 0;

        for (int i = 0; i < str.length(); i++)
        {
            String hex = Integer.toHexString(XOR_BYTES[xorIndex] ^ (int) str.charAt(i));
            if (hex.length() == 1)
            {
                hex = "0" + hex;
            }

            buffer.append(hex);
            xorIndex++;
            if (xorIndex == XOR_BYTES.length)
            {
                xorIndex = 0;
            }

        }
        return buffer.toString();
    }

    protected void write(int type, String str) throws IOException
    {
        int length = str.length() + 1;
        sequence++;
        os.write((byte) '*');
        os.write((byte) type);
        os.writeShort(sequence);
        os.writeShort(length);
        os.write(str.getBytes());
        os.write(0);
        os.flush();
    }

    protected String read() throws IOException
    {
        String flap = null;

        if (is.read() == '*')
        {
            is.read();
            is.read();
            is.read();

            int length = (is.read() * 0x100) + is.read();
            byte b[] = new byte[length];
            is.read(b);
            flap = new String(b);
        }

        return flap;
    }

    public void run()
    {
        String message;
        while (thread != null)
        {
            try
            {
                message = read();

                if (message != null)
                {
                    Message incoming = parse(message);
                    handleReceive(incoming);
                }
            }
            catch (IOException ioe)
            {
                Log.errors.log(AOLCauseway.class, ioe);
                try
                {
                    Thread.sleep(REST_TIME);
                    this.disconnect();
                    this.connect();
                }
                catch (Exception e)
                {
                    Log.errors.log(AOLCauseway.class,e);
                }
            }
            catch (Exception e)
            {
                Log.errors.log(AOLCauseway.class, e);
            }
        }
    }
    
    public Message parse(String message)
    {
        Message incoming = new Message();   
        
        String[] parts = message.split(":");
                  
        
        if (parts[0].equalsIgnoreCase("IM_IN2"))
        {
            if (parts.length > 1 )
            {
                incoming.setFrom(parts[1]);
                
                incoming.setContent(parts[parts.length-1]);
                /*
                 * find out why this was here
                if (parts.length > 3 )
                {
                    incoming.setContent(Strings.join(parts,3,parts.length,":"));
                }*/
            }
        }
        else if (parts[0].equalsIgnoreCase("ERROR"))
        {
            incoming.setFrom("Error");
            String text = null;
            if (parts.length == 2 )
            {
                text = aimErrors.get(parts[1],"Unknown");
            }
            else if ( parts.length == 3 )
            {          
                MessageFormat mf = new MessageFormat(aimErrors.get(parts[1],"Unknown"));
                Object[] args = { parts[2] };

                text = mf.format(args);
            }
            
            incoming.setContent(text);
        }
        else
        {
            incoming.setFrom("Oscar");
            incoming.setContent(message);
        }
        
        if ( !html )
        {
            incoming.setContent(HTMLToText(String.valueOf(incoming.getContent())));
        }
        
         
        return incoming;
    }
    
    
    public String HTMLToText(String html)
    {
        StringBuffer buffer = new StringBuffer();
        try 
        {
            EditorKit kit = new HTMLEditorKit();
            javax.swing.text.Document doc = kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
                    
            ByteArrayInputStream bais = new ByteArrayInputStream(html.getBytes());
            Reader rd = new InputStreamReader(bais);
            kit.read(rd, doc, 0);
                 
            ElementIterator it = new ElementIterator(doc);
            javax.swing.text.Element elem;
            while ((elem = it.next()) != null) 
            {
                if ( elem.getName().equalsIgnoreCase("BODY") )
                {
                    buffer.append(doc.getText(elem.getStartOffset(),elem.getEndOffset()- elem.getStartOffset()));
                    break;
                }
            }
        } 
        catch (Exception e) 
        {
          Log.errors.log(getClass(),e);
          return html;
        }
        
        return (buffer.length() > 0 ) ? buffer.toString() : html;
               
    }

    protected static String escape(String s)
    {
        return s.replaceAll("\n", "<br>")
        .replaceAll("\\\\", "\\\\\\\\")
        .replaceAll("\\}", "\\\\}")
        .replaceAll("\\{", "\\\\{")
        .replaceAll("\"", "\\\\\\\\\"");
    }
}