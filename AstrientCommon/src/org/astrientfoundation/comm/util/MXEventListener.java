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
package org.astrientfoundation.comm.util;

import javax.management.Notification;
import javax.management.NotificationListener;

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.comm.MessagingException;
import org.astrientfoundation.logging.Log;

public class MXEventListener implements NotificationListener
{
    private String from;
    private String to;
    private Causeway causeway;
    
    public MXEventListener(Causeway causeway, String from, String to)
    {
        this.causeway=causeway;
        this.from=from;
        this.to=to;
    }
    
    public void handleNotification(Notification notification, Object handback)
    {
        Message message = new Message();
        message.setTo(to);
        message.setFrom(from);
        message.setContent(notification);
      
        try
        {
            causeway.send(message);
        }
        catch (MessagingException e)
        {
            Log.errors.log(getClass(),e);
        }      
    }
    
    /*
     * MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
     * NotificationEmitter emitter = (NotificationEmitter) mbean; MyListener
     * listener = new MyListener(); emitter.addNotificationListener(listener, null, null);
     */
}