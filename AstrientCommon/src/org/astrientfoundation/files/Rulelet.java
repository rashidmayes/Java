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
package org.astrientfoundation.files;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.astrientfoundation.logging.Log;

public class Rulelet
{
    protected Rulelet parent = null;
    protected int offset;
    protected String type;
    protected String match;
    protected String mimeType;
    protected String mimeEncoding;
    protected boolean dc = false;

    public Rulelet()
    {
    }

    public Rulelet(Rulelet parent)
    {
        this.parent = parent;
    }

    public boolean test(DataInputStream dis) throws IOException
    {
        if ( parent == null )
        {
            return compare(dis);
        }
        else
        {
            return parent.compare(dis) && compare(dis);
        }
    }
    
    protected boolean compare(DataInputStream dis) throws IOException
    {
        boolean match = false;
        
        try
        {
            dis.mark(0);
            dis.skipBytes(offset);

            if (type.equals("short"))
            {
                if ( this.match.startsWith("&") )
                {
                    this.match = this.match.substring(1);
                }
                
                int s = dis.readUnsignedShort();
                int target = Integer.decode(this.match).intValue();
                
                match = ( s == target );
            }
            else if (type.equals("beshort"))
            {
                if ( this.match.startsWith("&") )
                {
                    this.match = this.match.substring(1);
                }
                
                
                int s = dis.readUnsignedShort();
                int target = Integer.decode(this.match).intValue();
                
                match = (s == target );         
            }
            else if (type.equals("belong"))
            {
                if ( this.match.startsWith("&") )
                {
                    this.match = this.match.substring(1);
                }
                
                
                long s = dis.readInt();
                long target = Long.decode(this.match.trim()).longValue();
                
                match = (s == target );      
            }
            else if (type.equals("string"))
            {         
                byte[] bytes = decode(this.match).getBytes();
                match = true;

                for ( int i = 0; i < bytes.length ; i++ )
                {
                    byte c = dis.readByte();
                    
                    if ( c == -1 || !(bytes[i] == c) )
                    {
                        match = false;
                        break;
                    }
                }
            }/*
            //Fix please
            else if (type.equals("lelong"))
            {
                if ( this.match.startsWith("&") )
                {
                    this.match = this.match.substring(1);
                }
                
                long s = dis.readInt();
                long target = littleToBigEndian(Long.decode(this.match).longValue());
                
                match = (s == target );      
            }
            else if ( type.startsWith("lelong&") )
            {
                if ( this.match.startsWith("&") )
                {
                    this.match = this.match.substring(1);
                }
                
                long mask = Long.decode(type.substring(7)).longValue();
                long s = littleToBigEndian(dis.readInt() & mask);
                long target = littleToBigEndian(Long.decode(this.match).longValue());
                
                match = (s == target );               
            }*/
        }
        catch (EOFException e)
        {
            
        }

        dis.reset();

        return match;
    }
    
    
    public long littleToBigEndian(long lelong) 
    {  
        ByteBuffer buf = ByteBuffer.allocate(8);  
        buf.order(ByteOrder.LITTLE_ENDIAN);  
        buf.putLong(lelong);  
        buf.order(ByteOrder.BIG_ENDIAN);  
        
        return buf.getLong(0);  
    }  
    
    
    public static String decode(String in)
    {        
        Pattern p = Pattern.compile("\\\\x[0-9,a-f,A-F]{2,8}|\\\\[0-9]{1,8}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(in);
        StringBuffer buffer = new StringBuffer();
        
        String cc;
        while (matcher.find())
        {
            try
            {
                cc = matcher.group();
                String decoded = new String( new byte[]{ Integer.decode("0"+cc.substring(1)).byteValue()} );

                matcher.appendReplacement(buffer, decoded);
            }
            catch (Exception e)
            {
                Log.errors.log(Rulelet.class,e);
            }
        }
        
        matcher.appendTail(buffer);

        return buffer.toString();
    }
    
    
}