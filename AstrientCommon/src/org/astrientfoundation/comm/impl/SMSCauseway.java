/*
 * Copyright (C) 2005 Astrient Labs, LLC Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at 
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Astrient Labs, LLC. 
 * www.astrientlabs.com 
 * rashid@astrientlabs.com
 * Rashid Mayes 2005
 */
package org.astrientfoundation.comm.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.ConnectionFailedException;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.comm.MessagingException;
import org.astrientfoundation.prefs.Preferences;


public class SMSCauseway extends Causeway
{
    private Preferences preferences;
    
    
    public SMSCauseway()
    {
        preferences = new Preferences("comp.comm.sms");
    }
    
    
    /* (non-Javadoc)
     * @see com.astrientlabs.messaging.Session#_connect()
     */
    protected void _connect() throws ConnectionFailedException
    {
        // TODO Auto-generated method stub

    }
    
    /* (non-Javadoc)
     * @see com.astrientlabs.messaging.Session#_disconnect()
     */
    protected void _disconnect()
    {
        // TODO Auto-generated method stub

    }
    
    /* (non-Javadoc)
     * @see com.astrientlabs.messaging.Session#_send(com.astrientlabs.messaging.Message)
     */
    protected void _send(Message message) throws MessagingException
    {		
        message.notifySending(this);
		InputStream is = null;
		
		try
		{
			String url = preferences.get("clickatel.url","https://api.clickatell.com/http/sendmsg");
			String queryString = "api_id=" + preferences.get("clickatel.appid") + "&to=" + message.getTo() + "&from=" + message.getFrom() + "&user=" + preferences.get("clickatel.user") + "&password=" + preferences.get("clickatel.password") + "&text=" + URLEncoder.encode(String.valueOf(message.getContent()),"UTF8");

			URL u = new URL(url);
			URLConnection urlConn = u.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
			out.writeBytes(queryString);
			out.flush();
			out.close();
			
			is = urlConn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String s;
            StringBuilder buffer = new StringBuilder();
            while ((s = br.readLine()) != null)
            {
                buffer.append(s).append('\n');
            }
            s = buffer.toString().trim();
	
			message.notifyDelivered(this,s);
		}
		catch (Exception e)
		{
		    message.notifyFailed(this,e);
		    throw new MessagingException("Problems encountered while trying to send message",e);
		}
		finally
		{
			try { if ( is != null ) is.close(); } catch (Exception e) {}
		}
    }
}
