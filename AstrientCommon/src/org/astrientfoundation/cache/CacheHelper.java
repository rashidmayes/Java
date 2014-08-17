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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.astrientfoundation.prefs.Preferences;


public class CacheHelper
{
	public static enum Scope { 
		USER    { Cache create() { return makeCache(this); } }, 
		SYSTEM  { Cache create() { return makeCache(this); } }, 
		SESSION { Cache create() { return makeCache(this); } }, 
		SERVER  { Cache create() { return makeCache(this); } }, 
		SUBNET  { Cache create() { return makeCache(this); } }; 
		
		abstract Cache create();
	};
	
    @SuppressWarnings("unchecked")
    private Map<Object,Cache>[] maps = new Map[Scope.values().length];

	private static final Cache makeCache(Scope scope)
	{
	    Cache cache = null;
	    Preferences prefs = new Preferences("cachehelper."+scope.name());

	    switch ( prefs.getInt("type",0) )
	    {
	        case 1 : cache = new SlotCache(prefs.getInt("size",30));
	                break;
	        case 2 : cache = new TimedPurgedCache(prefs.getInt("time",60));
                    break;	                
            case 3 : cache = new ExpiringTimedCache(prefs.getInt("hours",0),prefs.getInt("minutes",30),prefs.getInt("seconds",0));
                    break;
            case 4 : cache = new BucketCache(prefs.getInt("size",10));
                    break;
            default : cache = new TimedCache(prefs.getInt("size",30));
                    break;                    
	    }
	      
	    return cache;
	}



	private Map<Object,Cache> getMap(Scope scope)
	{
		if ( maps[scope.ordinal()] == null )
		{
			return (maps[scope.ordinal()] = new HashMap<Object,Cache>());
		}
		else
		{
			return  maps[scope.ordinal()];
		}
	}
	
	public Map<Object,Cache> getCaches(Scope scope)
	{
	    return Collections.unmodifiableMap(getMap(scope));
	}
	

	public Cache getCache(Scope scope, Object id)
	{
	    synchronized (scope)
	    {
	        Cache cache = getMap(scope).get(id);
	        if ( cache == null )
	        {
	            if ( cache == null )
	            {
	                cache = scope.create();
	                getMap(scope).put(id,cache);   
	            }  
	        }
	        
	        return cache;
	    }
	}
	
	public void destroy (Object id, Scope ...scopes)
	{
		synchronized ( id )
		{
			for (Scope scope : scopes )
			{
				if ( maps[scope.ordinal()] != null )
				{
					maps[scope.ordinal()].remove(id);
				}
			}	
		}
	}
	
	public synchronized void destory (Scope ...scopes)
	{
		for (Scope scope : scopes )
		{
			synchronized ( scope )
			{
				maps[scope.ordinal()] = null;
			}
		}
	}
}
