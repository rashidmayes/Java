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
import java.util.List;

import org.astrientfoundation.logging.Log;

public class SlotCache extends AbstractCache
{
    private ArrayList<CacheObject> list = null;
    private int maxSize = 0;

    public SlotCache(int size)
    {
        list = new ArrayList<CacheObject>(size);
        maxSize = size;
    }


    protected Object _get(Object key)
    {
    	try
    	{
    		CacheObject mo = null;
            for ( int i = 0; i < list.size(); i++ )
            {
                mo = (CacheObject)list.get(i);
                if ( mo != null && key.equals(mo.getKey()) )
                {
                	Object o = mo.getObject();
                	if ( o == null )
                	{
    					list.remove(i);
    					break;
                	}
                	else
                	{
                		if ( i > 0 )
                		{
                			list.add(i-1,list.remove(i));
                		}
                		
                		return o;
                	}
                }
            }    		
    	}
    	catch (Exception e)
    	{
    		Log.errors.log(getClass(),e);
    	}

        return null;
    }


	public Object remove(Object key)
	{
		CacheObject mo = null;
		for ( int i = 0; i < list.size(); i++ )
		{
			mo = (CacheObject)list.get(i);
			if ( mo.getKey().equals(key) )
			{
				Object o = mo.getObject();
				if ( o == null )
				{
					list.remove(i);
					break;
				}
				else				
				{
					list.remove(i);
					return o;
				}				
			}
		}

		return null;
	}


    protected void _put(Object key, Object value)
    {
        if ( list.size() == maxSize )
        {
            list.set(maxSize-1,new CacheObject(key,value));
        }
        else
        {
            list.add(new CacheObject(key,value));
        }
    }

    public void clear()
    {
        list.clear();
    }

    public int size()
    {
        return list.size();
    }

    public List<CacheObject> cacheObjects()
    {
        return Collections.unmodifiableList(list);
    }
}
