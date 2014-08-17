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


public class MessagingException extends Exception
{
    private static final long serialVersionUID = 8023766464338166273L;

    public MessagingException()
    {
        super();
    }
    
    /**
     * @param message
     */
    public MessagingException(String message)
    {
        super(message);
    }
    
    /**
     * @param message
     * @param cause
     */
    public MessagingException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    /**
     * @param cause
     */
    public MessagingException(Throwable cause)
    {
        super(cause);
    }
}
