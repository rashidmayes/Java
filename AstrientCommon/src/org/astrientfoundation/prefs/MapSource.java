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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.astrientfoundation.logging.Log;

public class MapSource
{
    public static final String URL_PREFIX = "/prefs/maps";

    protected Collection<String> prefs;
    protected String name;
    
    public MapSource(String name)
    {
        this(name, new TreeSet<String>());
    }
    
    public MapSource(String name, Collection<String> map)
    {
        this.name = name;
        prefs = map;
        
        refresh();
    }
  
    public void refresh()
    {
		InputStream is = null;
		try
		{
            prefs.clear();
			is = MapSource.class.getResourceAsStream(URL_PREFIX + "/" + name + ".map");
			load(is);
		}
		catch (Exception e)
		{
			Log.errors.log(MapSource.class,e);
		}
		finally
		{
			try { if ( is != null ) is.close(); } catch (Exception ignored) {}
		}
    }
    
    
    public void load(InputStream is) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ( (line = br.readLine()) != null )
        {
        	prefs.add(line);
        }
    } 
   
    public boolean contains(String value)
    {
    	return prefs.contains(value);
    }
     
    public void add(String value)
    {
    	prefs.add(value);
    }
    
    public Iterator<String> values()
    {
    	return prefs.iterator();
    }
    
    public String getName()
    {
        return name;
    }
      
    public MapSource add(MapSource p)
    {
        prefs.addAll(p.prefs);
        
        return this;
    }
}
