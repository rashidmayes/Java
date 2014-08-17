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
package org.astrientfoundation.web.frm.servlet;

import java.text.MessageFormat;
import java.util.Locale;

import org.astrientfoundation.util.Text;

public class CommandMessage
{
    private static final String defaultMessageContext = CommandHandler.PREFS.get("messagecontext","commands");
    private Exception exception;
    private Object[] args;
    private String message;
    private String messageContext;
    
    public CommandMessage(String message)
    {
        this(null,defaultMessageContext,message,null);
    }
    
    public CommandMessage(String message, Exception exception)
    {
        this(exception,defaultMessageContext,message,null);
    }

    public CommandMessage(boolean isError, String message)
    {
        this(null,defaultMessageContext,message,null);
    }
    
    public CommandMessage(String message, Object[] args)
    {
        this(null,defaultMessageContext,message,args);
    }
    
    public CommandMessage(String messageContext, String message)
    {
        this(null,messageContext,message,null);
    }
    
    public CommandMessage(String messageContext, String message, Object[] args)
    {
        this(null,messageContext,message,args);
    }

    public CommandMessage(Exception exception, String messageContext, String message, Object[] args)
    {
        this.exception = exception;
        this.args = args;
        this.message = message;
        this.messageContext = messageContext;
    }

    public Object[] getArgs()
    {
        return this.args;
    }

    protected void setArgs(Object[] args)
    {
        this.args = args;
    }

    public Exception getException()
    {
        return this.exception;
    }

    protected void setException(Exception exception)
    {
        this.exception = exception;
    }

    public String getMessage()
    {
        return this.message;
    }

    protected void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessageContext()
    {
        return this.messageContext;
    }

    protected void setMessageContext(String messageContext)
    {
        this.messageContext = messageContext;
    }

    public String getText(Locale locale)
    {
        String text = Text.instance.getString(messageContext, message, locale);
        if ( text != null )
        {
            if ( args == null )
            {
                
            }
            else
            {
                MessageFormat messageFormat = new MessageFormat(text,locale);
                text = messageFormat.format(args);
            }
        }

        return text;
    }
}
