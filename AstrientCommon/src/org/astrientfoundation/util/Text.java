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

import java.util.Locale;

import org.astrientfoundation.prefs.Preferences;

public class Text
{
    public static final Text instance = new Text();
    
	private TextProvider textProvider = new ResourceBundleTextProvider();
	
	private String defaultContext = Preferences.system.get("text.default.context","text");

    private Text()
    {
        refresh();
    }
    
    public void refresh()
    {
        textProvider.refresh();
    }
 
	public String getString(String key)
	{
		return textProvider.getString(key, null);
	}

	public String getString(String key, Locale locale)
	{
		return textProvider.getString(defaultContext, key, locale);
	}

	public String getString(String context, String key, Locale locale)
	{
	    return textProvider.getString(context, key, locale);
	}
	
	public String getString(String key, Locale locale, String def)
	{
	    return textProvider.getString(defaultContext, key, locale, def);
	}

	public String getString(String context, String key, Locale locale, String def)
	{
	    return textProvider.getString(context, key, locale, def);
	}
	
	
    /**
     * @return Returns the authoring.
     */
    public boolean isAuthoring()
    {
        return textProvider.isAuthoring();
    }
    
    /**
     * @param authoring The authoring to set.
     */
    public void setAuthoring(boolean authoring)
    {
        textProvider.setAuthoring(authoring);
    }

    public boolean isDebug()
    {
        return textProvider.isDebug();
    }

    public void setDebug(boolean debug)
    {
        textProvider.setDebug(debug);
    }
    
    public TextProvider getTextProvider()
    {
        return textProvider;
    }
    
    public void setTextProvider(TextProvider textProvider)
    {
        this.textProvider = textProvider;
    }
}