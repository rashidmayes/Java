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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimedPurgedCache extends AbstractCache
{
    private static Timer timer = new Timer();

    protected HashMap<Object,CacheObject> cachedObjects = new HashMap<Object,CacheObject>();
    protected boolean running = false;

    public TimedPurgedCache(int time)
    {
        timer.scheduleAtFixedRate(new CacheTimer(this),time*1000,60*1000);
    }

    protected Object _get(Object key)
    {
        CacheObject co = (CacheObject)cachedObjects.get(key);
        if ( co != null )
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

        return null;
    }
    
    
	public Object remove(Object key)
	{
		CacheObject co = (CacheObject)cachedObjects.remove(key);
		return ( co == null ) ? null : co.getObject();
	}  

    protected void _put(Object key, Object value)
    {
        cachedObjects.put(key,new CacheObject(key,value));
    }

    public void clear()
    {
        cachedObjects.clear();
    }


    public int size()
    {
        return cachedObjects.size();
    }

    public List<CacheObject> cacheObjects()
    {
        return new ArrayList<CacheObject>(cachedObjects.values());
    }

    public void run()
    {
        if (!running)
        {
            try
            {
                running = true;
                clear();
                postProcess();
            }
            finally
            {
                running = false;
            }
        }
    }
    
    public void postProcess()
    {
    }
    
	public boolean needsRemoval(CacheObject co)
	{
		return co.isOutDated();
	}
	
	class CacheTimer extends TimerTask
	{
		TimedPurgedCache cache;
		
		CacheTimer(TimedPurgedCache cache)
		{
			this.cache = cache;
		}
		
		public void run()
		{
			cache.run();
		}
	}
}
