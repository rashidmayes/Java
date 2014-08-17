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
package org.astrientfoundation.cache;

import java.util.List;

public class SynchronizedCache implements Cache
{
    private Cache cache = null;

    public SynchronizedCache(Cache _cache)
    {
        cache = _cache;
    }


    public Object get(Object key)
    {
        synchronized (this)
        {
            return cache.get(key);
        }
    }

	public Object remove(Object key)
	{
		synchronized (this)
		{
			return cache.remove(key);
		}
	}


    public void put(Object key, Object value)
    {
        synchronized (this)
        {
            cache.put(key,value);
        }
    }


    public void clear()
    {
        synchronized (this)
        {
            cache.clear();
        }
    }


    public int size()
    {
        return cache.size();
    }


    public List<CacheObject> cacheObjects()
    {
        return cache.cacheObjects();
    }
    
    
    public void setEnabled(boolean enabled)
    {
    	cache.setEnabled(enabled);
    }
    
    public boolean isEnabled()
    {
    	return cache.isEnabled();
    }
}
