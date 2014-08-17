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

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.comm.MessagingException;
import org.astrientfoundation.comm.impl.SMSCauseway;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.peers.Callback;

public final class SMS extends BasicService<SMSCauseway> implements Callback<Causeway,Message>
{  
    public SMS()
    {
        super("comp.services.sms");
        
        component = new SMSCauseway();
        
        this.description = "SMS Service " + component.getAddress();
        this.name = "SMS Service";
    }

    public boolean isAlive()
    {
        return true;
    }

    public boolean _start(ServiceContext context)
    {
        return true;
    }

    public boolean _stop(ServiceContext context)
    {
        return false;
    }
    
    public void send(String from, String to, String content) throws MessagingException
    {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setContent(content);
        message.addCallback(this);
        
        component.send(message, true);
    }
    
    public void send(Callback<Causeway,Message> callback, String from, String to, String content) throws MessagingException
    {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
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
