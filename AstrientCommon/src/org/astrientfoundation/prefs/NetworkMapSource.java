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

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import org.astrientfoundation.logging.Log;

public class NetworkMapSource extends MapSource
{
    public NetworkMapSource(String url)
    {
        super(url);
    }
    
    public NetworkMapSource(String url, Collection<String> map)
    {   
        super(url,map);
    }
  
    public void refresh()
    {
		InputStream is = null;
		try
		{
            prefs.clear();
            
            URL u = new URL(name);
            URLConnection uconn = u.openConnection();
             
			is = uconn.getInputStream();
			load(is);
		}
		catch (Exception e)
		{
			Log.errors.log(NetworkMapSource.class,e);
		}
		finally
		{
			try { if ( is != null ) is.close(); } catch (Exception ignored) {}
		}
    }
}
