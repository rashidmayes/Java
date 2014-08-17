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
package org.astrientfoundation.prefs;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.astrientfoundation.logging.Log;

public class Preferences extends Properties
{
    private static final long serialVersionUID = 1L;
    
    public static final Preferences system = new Preferences("system");
    public static final String URL_PREFIX = "/prefs/xml";

    private String name;
    protected String url;    

    public Preferences(String name)
    {
        this(name,null);
    }
    
    @SuppressWarnings("unchecked")
    public Preferences(Class clazz)
    {
        this("comp-"+clazz.getSimpleName(),null);
    }
    
    public Preferences(String name, Preferences parent)
    {
        this(name,parent,true);
    }
    
    public Preferences(String name, Preferences parent, boolean refresh)
    {   
        super(parent);
        
        this.name = name;
        this.url = URL_PREFIX + "/" + name + ".xml";

        if ( refresh )
        {
            refresh();
        }
    }
 
    public void refresh()
    {
		InputStream is = null;
		try
		{
			is = Preferences.class.getResourceAsStream(url);
			if ( is == null )
			{
			    throw new FileNotFoundException(url);
			}
			loadFromXML(is);
		}
		catch (FileNotFoundException e)
		{
		    Log.errors.log(getClass(),url + " not found.");
		}
		catch (Exception e)
		{
			Log.errors.log(Preferences.class,e);
		}
		finally
		{
			try { if ( is != null ) is.close(); } catch (Exception ignored) {}
		}
    }
            
    public String get(String name)
    {
    	return getProperty(name);
    }
    
    
    public String get(String name, String def)
    {
    	return getProperty(name,def);
    }
    
    
    public void set(String name, String value)
    {
    	setProperty(name,value);
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public int getInt(String key, int def)
    {
        String value = get(key);
        return ( value == null ) ? def : Integer.parseInt(value);
    }
    
    
    public long getLong(String key, long def)
    {
        String value = get(key);
        return ( value == null ) ? def : Long.parseLong(value);
    }
    
    
    public double getDouble(String key, double def)
    {
        String value = get(key);
        return ( value == null ) ? def : Double.parseDouble(value);
    }
    
    
    public boolean getBoolean(String key, boolean def)
    {
        String value = get(key);
        return ( value == null ) ? def : new Boolean(value).booleanValue();
    }
}