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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.ConnectionFailedException;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.comm.MessagingException;
import org.astrientfoundation.logging.Log;


public class SMTPCauseway extends Causeway
{
    Session session;
    Transport transport;
    
    public SMTPCauseway()
    {
    }

    protected void _connect() throws ConnectionFailedException
    {
		Properties props = new Properties();
		props.put("mail.smtp.host", getAddress());
        props.put("mail.smtp.sendpartial", "true");
        
		session = Session.getInstance(props, null);
        try
        {
            transport = session.getTransport("smtp");
            transport.connect();
        }
        catch (javax.mail.MessagingException e)
        {
            throw new ConnectionFailedException(e);
        }
    }
    
    public boolean isConnected()
    {
        return ( transport != null ) && (transport.isConnected());
    }

    protected void _disconnect()
    {
        try
        {
            transport.close();
        }
        catch (javax.mail.MessagingException e)
        {
            Log.errors.log(getClass(),e);
            //throw new MessagingException(e);
        }
    }
    

    protected void _send(Message m) throws MessagingException
    {		
		try
		{
		    EmailMessage message = (EmailMessage)m;
		    message.notifySending(this);
			
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(message.getFrom()));
			mimeMessage.setReplyTo(mimeMessage.getFrom());
			mimeMessage.setSentDate(new Date());
			
			Map<Object,Object> headers = message.getHeaders();
			for (Object key : headers.keySet() )
			{
			    mimeMessage.setHeader(String.valueOf(key),String.valueOf(headers.get(key)));
			}
			
			Set<String> allAddresses = new HashSet<String>(); 

			List<String> addresses = message.getToAddresses();
			allAddresses.addAll(addresses);
			
			for (String address : addresses)
            {
                try
                {
                    mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(address));
                }
                catch (Exception e)
                {
                    message.notifyError(this,e);
                }
            }

			//CC
            addresses = message.getCCAddresses();
            for (String address : addresses)
            {
                if (allAddresses.contains(address))
                    continue;

                try
                {
                    allAddresses.add(address);
                    mimeMessage.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(address));
                }
                catch (Exception e)
                {
                    message.notifyError(this,e);
                }
            }

            //BCC
            addresses = message.getBCCAddresses();
            for (String address : addresses)
            {
                if (allAddresses.contains(address))
                    continue;

                try
                {
                    mimeMessage.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(address));
                    allAddresses.add(address);
                }
                catch (Exception e)
                {
                    message.notifyError(this,e);
                }
            }
			
			
			mimeMessage.setSubject(message.getSubject());

			DataSource[] attachments = message.getAttachments();
			if ( attachments.length > 0 )
			{			    
				Multipart mp = new MimeMultipart();
				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setContent(message.getContent(),message.getContentType());
				mp.addBodyPart(mbp);

				for ( int i = 0; i < attachments.length; i++ )
				{
					mbp = new MimeBodyPart();
					mbp.setDataHandler(new DataHandler(attachments[i]));
					mbp.setFileName(attachments[i].getName());

					mp.addBodyPart(mbp);
				}
				
			
				mimeMessage.setContent(mp);
			}
			else
			{
				if ( message.getContent() instanceof String )
				{
					mimeMessage.setContent(message.getContent(),message.getContentType());	
				}
				else if (  message.getContent() instanceof MimeBodyPart)
				{
				    Multipart mp = new MimeMultipart();
				    mp.addBodyPart((MimeBodyPart)message.getContent());
				    mimeMessage.setContent(mp);
				}
			}

			//transport.send(mimeMessage);
			transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients()); 
			message.notifyDelivered(this,"sent to " + allAddresses.size() + " recipients.");
		}
		catch (Exception e)
		{
		    m.notifyFailed(this,e);
		    throw new MessagingException("Problems encountered while trying to send message",e);
		}
    }
}
