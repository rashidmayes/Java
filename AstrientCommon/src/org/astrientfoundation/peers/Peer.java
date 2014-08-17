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
package org.astrientfoundation.peers;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Peer
{
    private InetAddress inetAddress;
    private int port;
    private long lastPing;
    private String name;
    private SocketAddress address;
    
    public InetAddress getInetAddress()
    {
        return this.inetAddress;
    }
    public void setInetAddress(InetAddress inetAddress)
    {
        this.inetAddress = inetAddress;
    }
    public long getLastPing()
    {
        return this.lastPing;
    }
    public void setLastPing(long lastPing)
    {
        this.lastPing = lastPing;
    }
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getPort()
    {
        return this.port;
    }
    public void setPort(int port)
    {
        this.port = port;
    }
    
    protected void refresh()
    {
        address = new InetSocketAddress(inetAddress,port);
    }
    
    public SocketAddress getAddress()
    {
        return address;
    }
}
