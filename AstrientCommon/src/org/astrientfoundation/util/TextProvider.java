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
package org.astrientfoundation.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import org.astrientfoundation.prefs.Preferences;

public abstract class TextProvider
{
    protected boolean authoring;
	protected boolean debug;
    protected Properties brokenKeys = new Properties();
	protected Preferences preferences;
    protected TextProvider parent;
    
    public TextProvider(String name, boolean refresh, TextProvider parent)
    {
        this.parent = parent;
        preferences = new Preferences("comp.text.impl."+name);

        if ( refresh ) refresh();
    }
    
    public void refresh()
    {
        preferences.refresh();
        authoring = preferences.getBoolean("authormode",false);
        authoring = preferences.getBoolean("debug",false);
    }
 
	public String getString(String key)
	{
		return getString(key, null);
	}

	public String getString(String key, Locale locale)
	{
		return getString("text", key, locale);
	}

	public String getString(String context, String key, Locale locale)
	{
		String value = _getString(context, key, locale);
		if ( value == null )
		{
		    if ( parent != null )
		    {
		        value = parent.getString(context, key, locale);
		    }
		}

        if ( authoring || debug )
        {
            brokenKeys.put(key,context);
        }

        if ( debug )
        {
            return key + "/" + value;
        }
        else
        {
            return ( authoring && value == null ) ? key + "/" + value : value;
        }
	}
	
	
	public String getString(String context, String key, Locale locale, String def)
    {
        String value = _getString(context, key, locale);
        if (value == null)
        {
            if (parent != null)
            {
                value = parent.getString(context, key, locale);
            }
        }

        return Strings.ifNull(value,def);
    }
	
    public boolean isAuthoring()
    {
        return authoring;
    }
    
    public void setAuthoring(boolean authoring)
    {
        this.authoring = authoring;
    }

    public boolean isDebug()
    {
        return debug;
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }
    
    public Properties getBrokenKeys()
    {
    	return brokenKeys;
    }
    
    protected abstract String _getString(String context, String key, Locale locale);
    public abstract Enumeration<String> keys(String context, Locale locale);
}