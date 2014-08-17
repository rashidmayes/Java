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
import java.util.Iterator;

import org.astrientfoundation.util.Strings;

public abstract class Element
{
    public static final int TYPE_TEXTBOX = 0;
    public static final int TYPE_TEXTFIELD = 1;
    public static final int TYPE_SELECT = 2;
    public static final int TYPE_CHECKBOX = 3;
    public static final int TYPE_HIDDEN = 4;
    public static final int TYPE_PASSWORD = 5;
    public static final int TYPE_PROMPT = 6;
    public static final int TYPE_RADIO = 7;
    public static final int TYPE_TITLE = 8;
    public static final int TYPE_BREAK = 9;
    public static final int TYPE_LABEL = 10;
    public static final int TYPE_IMAGE = 11;
    public static final int TYPE_LINK = 12;
    public static final int TYPE_SELECT_FILTER = 13;
    public static final int TYPE_FILTERED_SELECT = 14;
    public static final int TYPE_BUTTON = 15;
    
    protected Collection<String> model = new ArrayList<String>();
    protected int type;
    protected String name;
    protected String id;
    protected String value;
    protected int width;
    protected int height;
    protected boolean required;
    protected ElementError error;
    protected boolean showName = true;
    protected String validationPattern;
    protected ElementWriter elementWriter = null;
    protected float cost;
    protected boolean selected;
    protected String doppelganger;
    protected Form form;
    
    public void init(Form form)
    {
        this.form = form;
    }
    
    public String getDoppelganger()
    {
        return this.doppelganger;
    }

    public void setDoppelganger(String doppelganger)
    {
        this.doppelganger = doppelganger;
    }

    public boolean isSelected()
    {
        return this.selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean validValue()
    {   	
    	return ( validationPattern == null ) ? true : ( ( value == null ) ? !required : value.matches(validationPattern) );
    }
    
    public String getValidationPattern()
	{
		return this.validationPattern;
	}

	public void setValidationPattern(String validationPattern)
	{
		this.validationPattern = validationPattern;
	}



	public boolean showName()
    {
    	return showName;
    }
    
    public boolean hasError()
    {
    	return ( error != null );
    }
    
    public void setError(int code, String message)
    {
    	setError(new ElementError(code,message));
    }
    
    public void setError(ElementError error)
    {
    	this.error = error;
    }
    
    public String getErrorMessage()
    {
    	return ( error == null ) ? "" : error.message; 
    }
    
    public ElementError getError()
    {
    	return error;
    }
    
    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public Iterator<String> values()
    {
        return model.iterator();
    }
    
    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = Strings.fixQoutes(name);
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = Strings.fixQoutes(value);
    }

    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }

    public float getCost()
    {
        return this.cost;
    }

    public void setCost(float cost)
    {
        this.cost = cost;
    }
    
    
    @SuppressWarnings("unchecked")
    protected <T extends Element> T clone(Form form) throws CloneNotSupportedException
    {
        try
        {
            T clone = (T)getClass().newInstance();
            clone.height = this.height;
            clone.width = this.width;
            clone.model = this.model;
            clone.name = this.name;
            clone.setValue(this.value);
            clone.id = this.id;
            clone.required = this.required;
            clone.showName = this.showName;
            clone.validationPattern = this.validationPattern;
            clone.cost = this.cost;
            clone.selected = this.selected;
            clone.doppelganger = this.doppelganger;
            clone.init(form);
            
            return clone;
        }
        catch (InstantiationException e)
        {
            throw new CloneNotSupportedException(e.getMessage());
        }
        catch (IllegalAccessException e)
        {
            throw new CloneNotSupportedException(e.getMessage());
        }
    }

    public abstract String toHTMLString();
}
