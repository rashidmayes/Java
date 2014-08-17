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
package org.astrientfoundation.web.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Form
{
    protected String name;
    protected String id;
    protected String description;
    protected long modified;
    protected Map<String,String> controlLabels = new HashMap<String,String>();
    protected Map<String,PageAction> actions = new HashMap<String,PageAction>();
    
    protected Collection<Element> elements = new ArrayList<Element>();
    
    public Element getElement(String id)
    {
        for ( Element element : elements )
        {
            if ( element.getId().equals(id) )
            {
                return element;
            }
        }
        
        return null;
    }
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public long getModified()
    {
        return modified;
    }

    public void setModified(long modified)
    {
        this.modified = modified;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Iterator<Element> elements()
    {
        return elements.iterator();
    }

    public Form blankForm() throws CloneNotSupportedException
    {
        return (Form)clone();
    }
    
    protected Object clone() throws CloneNotSupportedException
    {
        Form clone = new Form();
        Element elementClone;
        
        clone.name = name;
        clone.description = description;
        clone.id = id;
        clone.modified = modified;
        clone.controlLabels = this.controlLabels;
        clone.actions = this.actions;
        
        try
        {
            for ( Element element : this.elements )
            {
                elementClone = element.clone(clone);
                clone.elements.add(elementClone);
            }
        }
        catch (Exception e)
        {
            throw new CloneNotSupportedException(e.getMessage());
        }        
        
        return clone;
    }
    
    public String getLabel(String id)
    {
        return controlLabels.get(id);
    }
    
    public PageAction getAction(String id)
    {
        return actions.get(id);
    }
    
    public Collection<PageAction> getActions()
    {
        return actions.values();
    }
}
