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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.astrientfoundation.peers.Callback;


public class Message
{
    private String id;
    private String from;
    private String to;
    private Object content;
    private List<Callback<Causeway,Message>> callbacks = new LinkedList<Callback<Causeway,Message>>();
    private Map<Object,Object> headers = new HashMap<Object,Object>();
    private List<Exception> exceptions = new ArrayList<Exception>();
    private String responseText;
    
    public String getResponseText()
    {
        return this.responseText;
    }
    
    public List<Exception> getExceptions()
    {
        return Collections.unmodifiableList(exceptions);
    }

    public String getId()
    {
        return this.id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * @return Returns the content.
     */
    public Object getContent()
    {
        return content;
    }
    
    /**
     * @param content The content to set.
     */
    public void setContent(Object content)
    {
        this.content = content;
    }
    
    /**
     * @return Returns the from.
     */
    public String getFrom()
    {
        return from;
    }
    
    /**
     * @param from The from to set.
     */
    public void setFrom(String from)
    {
        this.from = from;
    }
    
    /**
     * @return Returns the to.
     */
    public String getTo()
    {
        return to;
    }
    
    /**
     * @param to The to to set.
     */
    public void setTo(String to)
    {
        this.to = to;
    }
    
    public List<Callback<Causeway,Message>> getCallbacks()
    {
        return callbacks;
    }
    
    public void addCallback(Callback<Causeway,Message> callback)
    {
        callbacks.add(callback);
    }
    
    public void notifyInitialized(Causeway causeway)
    {
        for ( Callback<Causeway,Message> callback : callbacks )
        {
            callback.initialized(causeway,this);
        }
    }
    
    public void notifyFailed(Causeway causeway, Exception exception)
    {
        exceptions.add(exception);
        for ( Callback<Causeway,Message> callback : callbacks )
        {
            callback.failed(causeway,this);
        }
    }
    
    public void notifySending(Causeway causeway)
    {
        for ( Callback<Causeway,Message> callback : callbacks )
        {
            callback.started(causeway,this);
        }
    }
    
    public void notifyError(Causeway causeway, Exception exception)
    {
        exceptions.add(exception);
        for ( Callback<Causeway,Message> callback : callbacks )
        {
            callback.error(causeway,this);
        }
    }
    
    public void notifyCancelled(Causeway causeway)
    {
        for ( Callback<Causeway,Message> callback : callbacks )
        {
            callback.cancelled(causeway,this);
        }
    }
    
    public void notifyDelivered(Causeway causeway, String responseText)
    {
        this.responseText = responseText;
        
        for ( Callback<Causeway,Message> callback : callbacks )
        {
            callback.completed(causeway,this);
        }
    }
    
    public void setHeader(Object key, Object value)
    {
        headers.put(key,value);
    }
    
    public Map<Object,Object> getHeaders()
    {
        return headers;
    }
}
