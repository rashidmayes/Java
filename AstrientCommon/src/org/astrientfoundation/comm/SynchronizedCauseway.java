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

public class SynchronizedCauseway extends Causeway
{
    private Causeway causeway;
    
    
    public SynchronizedCauseway(Causeway causeway)
    {
        this.causeway = causeway;
    }
    
    protected void _connect() throws ConnectionFailedException
    {
        this.causeway._connect();
    }
    
    protected void _disconnect()
    {
        this.causeway._disconnect();
    }
    
    protected void _send(Message message) throws MessagingException
    {
        this.causeway._send(message);
    }
    
    public void connect() throws ConnectionFailedException
    {
        this.causeway.connect();
    }
    
    public void disconnect()
    {
        this.causeway.disconnect();
    }
    
    public boolean equals(Object obj)
    {
        return this.causeway.equals(obj);
    }
    
    public String getAddress()
    {
        return this.causeway.getAddress();
    }
    
    public String getHandle()
    {
        return this.causeway.getHandle();
    }
    
    public ReceiveHandler getReceiveHandler()
    {
        return this.causeway.getReceiveHandler();
    }
    
    protected void handleReceive(Message message)
    {
        this.causeway.handleReceive(message);
    }
    
    public int hashCode()
    {
        return this.causeway.hashCode();
    }
    
    public boolean isConnected()
    {
        return this.causeway.isConnected();
    }
    
    public void send(Message message) throws MessagingException
    {
        this.causeway.send(message);
    }
    
    public void send(Message message, boolean defered) throws MessagingException
    {
        this.causeway.send(message, defered);
    }
    
    public void setAddress(String address)
    {
        this.causeway.setAddress(address);
    }
    
    public void setHandle(String handle)
    {
        this.causeway.setHandle(handle);
    }
    
    public void setReceiveHandler(ReceiveHandler recieveHandler)
    {
        this.causeway.setReceiveHandler(recieveHandler);
    }
    
    public String toString()
    {
        return this.causeway.toString();
    }
}
