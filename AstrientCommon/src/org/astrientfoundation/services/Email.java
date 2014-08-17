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
package org.astrientfoundation.services;

import java.io.File;
import java.io.IOException;

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.comm.MessagingException;
import org.astrientfoundation.comm.impl.AuthSMTPCauseway;
import org.astrientfoundation.comm.impl.EmailMessage;
import org.astrientfoundation.comm.impl.SMTPCauseway;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.peers.Callback;

public final class Email extends BasicService<SMTPCauseway> implements Callback<Causeway,Message>
{  
    public Email()
    {
        super("comp.services.smtp");
        
        boolean isAuth = preferences.getBoolean("auth",false);
        if ( isAuth )
        {
            component = new AuthSMTPCauseway();
            component.setHandle(preferences.get("username")+":"+preferences.get("password"));
            component.setAddress(preferences.get("host")+":"+preferences.get("port"));
        }
        else
        {
            component = new SMTPCauseway();
            component.setAddress(preferences.get("host"));
        }
  
        this.description = "Email Service " + component.getAddress();
        this.name = "Email Service";
    }

    public boolean isAlive()
    {
        return component.isConnected();
    }

    public boolean _start(ServiceContext context)
    {
        return true;
        /*
        try
        {
            component.connect();
            return true;
        }
        catch (ConnectionFailedException e)
        {
            Log.errors.log(getClass(),e);
        }
        
        return false;*/
    }

    public boolean _stop(ServiceContext context)
    {
        component.disconnect();
        return true;
    }
    
    public void send(String from, String to, String subject, String content) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addCallback(this);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String subject, String content) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addCallback(callback);
        
        component.send(message, true);
    }
    
    public void send(String from, String to, String subject, String content, File file) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addAttachment(file);
        message.addCallback(this);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String subject, String content, File file) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addAttachment(file);
        message.addCallback(callback);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String subject, String content, String contentType, File file) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.setContentType(contentType);
        message.addAttachment(file);
        message.addCallback(callback);
        
        component.send(message, true);
    }
    
    
    public void send(String from, String to, String subject, String content, String attachmentName, byte[] data) throws MessagingException, IOException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addAttachment(attachmentName,data);
        message.addCallback(this);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String subject, String content, String attachmentName, byte[] data) throws MessagingException, IOException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addAttachment(attachmentName,data);
        message.addCallback(callback);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String subject, String content, String contentType, String attachmentName, byte[] data) throws MessagingException, IOException
    {
        EmailMessage message = new EmailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.setContentType(contentType);
        message.addAttachment(attachmentName,data);
        message.addCallback(callback);
        
        component.send(message, true);
    }
    
    
    public void send(String from, String to, String subject, String content, String contentType) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setContentType(contentType);
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addCallback(this);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String subject, String content, String contentType) throws MessagingException
    {
        EmailMessage message = new EmailMessage();
        message.setContentType(contentType);
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setContent(content);
        message.addCallback(callback);
        
        component.send(message, true);
    }
    
    public void send(Message message) throws MessagingException
    {
        component.send(message, true);
    }
    
    public void cancelled(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"cancelled : " + source +", " + what);
    }

    public void error(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"error : " + source +", " + what);
    }

    public void failed(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"failed : " + source +", " + what);
    }

    public void completed(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"completed : " + source +", " + what);
    }

    public void initialized(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"initialized : " + source +", " + what);
    }

    public void started(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"started : " + source +", " + what);
    }

    public void update(Causeway source, Message what)
    {
        Log.messaging.log(getClass(),"update : " + source +", " + what);
    }
}
