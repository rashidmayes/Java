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

import java.io.DataInputStream;
import java.net.Socket;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.ClassMap;
import org.astrientfoundation.prefs.ClassRegister;
import org.astrientfoundation.threads.Task;

public class ProcessSocketJob extends Task
{
    private static final ClassMap<PeerMessageHandler> classMap = new ClassMap<PeerMessageHandler>("peer-handlers");
        
    private Socket socket;
    private Multicaster multicastPeer;
    
    public ProcessSocketJob(Multicaster multicastPeer, Socket socket)
    {
        this.socket = socket;
        this.multicastPeer = multicastPeer;
    }
    
    public void _execute()
    {
        DataInputStream dis = null;
        try
        {
            dis = new DataInputStream(socket.getInputStream());
            int version = dis.readInt();
            String command = dis.readUTF();
            
            ClassRegister<PeerMessageHandler> register = classMap.getRegister(command);    
            
            Log.network.log(getClass(), command + "," + version + " handled by " + register);
            PeerMessageHandler peerMessageHandler = null;
            
            if ( register == null )
            {
                peerMessageHandler = new NullHandler();
             
            }
            else
            {
                peerMessageHandler = register.getClassObject().newInstance();
            }
            
            peerMessageHandler.process(socket);
            socket.close();
        }
        catch (Exception e)
        {
            Log.errors.log(getClass(),e);
        }
        finally
        {
            multicastPeer.notifyComplete(this);
        }
    }
}
