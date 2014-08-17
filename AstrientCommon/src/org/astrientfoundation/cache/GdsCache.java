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
import java.util.Collections;
import java.util.Comparator;


public class GdsCache extends TimedCache
{
	private long tooLarge; 
	private double trim;
	private int min = 100;
	
	private static final GdsComparator comparator = new GdsComparator();

    public GdsCache(int time, long tooLarge, double trim)
    {
    	super(time);
    	this.tooLarge = tooLarge;
    	this.trim = trim;
    }
    
    public GdsCache(int time, long tooLarge, double trim, int min)
    {
    	super(time);
    	this.tooLarge = tooLarge;
    	this.trim = trim;
    	this.min = min;
    }

   
    public void postProcess()
    {
    	trim(trim);
    }
    
    public synchronized void trim(double d)
    {
    	if ( cachedObjects.size() > min )
    	{
    		ArrayList<CacheObject> list = new ArrayList<CacheObject>(cachedObjects.values());

    		Collections.sort(list,comparator);

    		long cut = Math.min(Math.round(list.size() * d),list.size());
    		
    		GdsCacheObject temp;
    		for ( int i = 0; i < cut; i++ )
    		{
    			temp = (GdsCacheObject)cachedObjects.remove(list.get(i).getKey());
    			temp.clear();
    			temp.ref.clear();
    		}	
    	}
    }

    public boolean needsRemoval(CacheObject co)
    {
    	if ( co.isOutDated() )
    	{
    		return true;
    	}
    	else
    	{    		
    		GdsCacheObject gdsco = (GdsCacheObject)co;
    		
    		if ( gdsco.get() == null )
    		{
    			return true;
    		}
    		
    		if ( gdsco.getSize() > tooLarge )
    		{
    			return true; 
    		}
    	} 
    	
    	return false;
    }
    
    
    public GdsCacheObject getGdsCacheObject(String url)
    {
    	GdsCacheObject obj = (GdsCacheObject)cachedObjects.get(url);
    	if ( obj == null )
    	{
    		obj = new GdsCacheObject(url,time);
    		obj.setMaxSize(this.tooLarge);
    		cachedObjects.put(url,obj);
    	}
    	
    	return obj;
    }


	protected Object _get(Object key)
	{
		return super._get(key);
	}
    
    
	public Object remove(Object key)
	{
		return super.remove(key);
	}
    

	protected void _put(Object key, Object value)
	{
		if ( value instanceof GdsCacheObject )
		{
			super._put(key,value);
		} 
	}    
}
class GdsComparator implements Comparator<CacheObject>
{
	public int compare(CacheObject o1, CacheObject o2)
	{
		if ( !(o1 instanceof GdsCacheObject) || !(o1 instanceof GdsCacheObject) )
		{
			return -1;
		}
		
		GdsCacheObject a = (GdsCacheObject)o1;
		GdsCacheObject b = (GdsCacheObject)o2;
		
		if ( a.cost() > b.cost() )
		{
			return 1;
		}
		else if ( a.cost() < b.cost() )
		{
			return -1;
		}
		else 
		{
			return 0;
		}
	}
}
