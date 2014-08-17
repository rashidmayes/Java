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

public class ExpiringTimedCache extends TimedCache
{
    public ExpiringTimedCache(int hours, int minutes, int seconds)
    {
        super((hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000));
    }


    protected Object _get(Object key)
    {
        CacheObject co = (CacheObject)cachedObjects.get(key);
        if ( co != null )
        {
        	if ( co.isExpired() )
        	{
        		cachedObjects.remove(co);
        	}
        	else
        	{
				Object o = co.getObject();
        		if ( o == null )
        		{
        			cachedObjects.remove(co);
        		}
        		else
        		{
        			return o;
        		}
        	}
        }

        return null;
    }
    
    
	public Object remove(Object key)
	{
		CacheObject co = (CacheObject)cachedObjects.remove(key);
		if ( co != null )
		{
			if ( !co.isExpired() )
			{
				return co.getObject();
			}
		}

		return null;
	}
    
    
	public boolean needsRemoval(CacheObject co)
	{
		return co.isExpired();
	}
}