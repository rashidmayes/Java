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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class NullHandler extends PeerMessageHandler
{
    public void _process(Socket socket) throws IOException
    {
        byte[] buffer =  new byte[4*1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(buffer.length);
        int read = 0;
        
        InputStream is = socket.getInputStream();
        while ( (read = is.read(buffer)) != -1 )
        {
            baos.write(buffer,0,read);
        }
        
        buffer = baos.toByteArray();
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        writeHeader(dos,RESPONSE_ECHO);
        dos.writeInt(buffer.length);
        dos.write(buffer);
        dos.flush();
    }
}