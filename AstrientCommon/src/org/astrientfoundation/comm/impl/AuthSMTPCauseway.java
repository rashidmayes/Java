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

import java.util.Properties;

import javax.mail.Session;

import org.astrientfoundation.comm.ConnectionFailedException;


public class AuthSMTPCauseway extends SMTPCauseway
{    
    protected void _connect() throws ConnectionFailedException
    {       
		
		String[] hostPort = getAddress().split(":");
		String[] userPass = getHandle().split(":");
		
		Properties props = new Properties();
		props.put("mail.smtp.host", hostPort[0]);
        props.put("mail.smtp.sendpartial", "true");
        props.put("mail.smtp.auth", "true");
        
		session = Session.getInstance(props, null);
				
		try
		{
			transport = session.getTransport("smtp");
			transport.connect(hostPort[0], Integer.parseInt(hostPort[1]), userPass[0], userPass[1]);
		}
		catch (javax.mail.MessagingException e)
		{
			throw new ConnectionFailedException(e);
		}
    }
}
