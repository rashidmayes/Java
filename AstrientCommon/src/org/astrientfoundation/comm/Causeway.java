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
package org.astrientfoundation.comm;

import org.astrientfoundation.comm.util.SendMessageTask;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.ClassMap;
import org.astrientfoundation.prefs.ClassRegister;
import org.astrientfoundation.prefs.Preferences;
import org.astrientfoundation.threads.TaskRunner;


public abstract class Causeway
{
    protected static final TaskRunner taskRunner = new TaskRunner(4);
    private static final ClassMap<Causeway> classMap = new ClassMap<Causeway>("comp.comm.impl");
    private static final Preferences preferences = new Preferences("comp.comm.config");
    
    private String handle;
    private boolean connected = false;
    private ReceiveHandler receiveHandler;
    private String address;
       
    public void connect() throws ConnectionFailedException
    {
        if ( !isConnected() )
        {
            Log.messaging.log(getClass(),"Opening connection to " + address);
            _connect();
            connected = true;
        }
    }
    
    
    public void send(Message message, boolean defered) throws MessagingException
    {
        connect();
        
        if ( defered )
        {
            taskRunner.execute(new SendMessageTask(this,message));
        }
        else
        {
            _send(message); 
        }        
    }
    
    public void send(Message message) throws MessagingException
    {
        message.notifySending(this);
        _send(message);
    }
       
    public void disconnect()
    {
        try
        {
            _disconnect();
            connected = false;
        }
        finally
        {
            Log.messaging.log(getClass(),"Disconnected from " + address);
        }
    }
    
    
    protected void handleReceive(Message message)
    {
        if ( receiveHandler != null )
        {
            receiveHandler.handle(message);
        }
    }  
    
    /**
     * @return Returns the recieveHandler.
     */
    public ReceiveHandler getReceiveHandler()
    {
        return receiveHandler;
    }
    
    /**
     * @param recieveHandler The recieveHandler to set.
     */
    public void setReceiveHandler(ReceiveHandler recieveHandler)
    {
        this.receiveHandler = recieveHandler;
    }
    
    /**
     * @return Returns the connected.
     */
    public boolean isConnected()
    {
        return connected;
    }
       
    /**
     * @return Returns the handle.
     */
    public String getHandle()
    {
        return handle;
    }
    
    /**
     * @param handle The handle to set.
     */
    public void setHandle(String handle)
    {
        this.handle = handle;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    /**
     * @return Returns the address.
     */
    public String getAddress()
    {
        return address;
    }
    
    protected abstract void _connect() throws ConnectionFailedException;
    protected abstract void _send(Message message) throws MessagingException;
    protected abstract void _disconnect();
    
    
    public static Causeway getCauseway(String type, String address, String args[]) throws MessagingException
    {
        try
        {
            ClassRegister<Causeway> classRegister = classMap.getRegister(type);

            if (classRegister == null)
            {
                throw new MessagingException("Cant find session handler for type " + type);
            }
            else
            {
                Causeway causeway = classRegister.getClassObject().newInstance();
                causeway.setAddress(address);
                return (preferences.getBoolean("sychronous",false)) ? new SynchronizedCauseway(causeway) : causeway;
            }
        }
        catch (Exception e)
        {
            throw new MessagingException("Cant create session handler for type " + type, e);
        }

    }
}
