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

import org.astrientfoundation.comm.Causeway;
import org.astrientfoundation.comm.Message;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.threads.Task;

public class SendMessageTask extends Task
{
    Message message;
    Causeway causeway;
    boolean disconnect = false;
    
    public SendMessageTask(Causeway causeway, Message message)
    {
        this.message = message;
        this.causeway = causeway;
    }
    
    public SendMessageTask(Causeway causeway, Message message, boolean disconnect)
    {
        this.message = message;
        this.causeway = causeway;
        this.disconnect = disconnect;
    }
    
    public void _execute()
    {
        try
        {
            causeway.send(message);
        }
        catch (Exception e)
        {
            Log.errors.log(SendMessageTask.class, e);
        }
        finally
        {
        	if ( disconnect )
        	{
        		causeway.disconnect();
        	}
        }
    }
}