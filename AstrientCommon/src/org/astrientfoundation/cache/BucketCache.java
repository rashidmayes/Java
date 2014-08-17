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
import java.util.List;

public class BucketCache extends AbstractCache
{
    private CacheObject[] model;
    
    public BucketCache(int size)
    {
        model = new CacheObject[size];
    }
    
    public int slots()
    {
        return model.length;
    }
    
    private int keyIndex(Object key)
    {
        return Math.abs(key.hashCode() % model.length);
    }
    
    public void _put(Object key, Object value)
    {
        model[keyIndex(key)] = new CacheObject(key,value,Integer.MAX_VALUE);   
    }
    
    public Object _get(Object key)
    {
        int pos =  keyIndex(key);
        CacheObject object = model[pos];
        
        if ( object != null && object.key.equals(key) )
        {
            return object.getObject();
        }

        return null;
    }
    
    public int size()
    {
        int count = 0;
        for ( CacheObject object : model )
        {
            if ( object != null && object.get() != null ) count++;
        }
        
        return count;
    }
    
    public void clear()
    {
        for (int i = 0; i < model.length; i++)
        {
            model[i] = null;
        }
    }

    public List<CacheObject> cacheObjects()
    {
        synchronized (this)
        {
            List<CacheObject> list = new ArrayList<CacheObject>(model.length);
            for ( CacheObject object : model )
            {
                if ( object != null )
                {
                    list.add(object);
                }
            }            
            
            return list;
        }
    }

    public Object remove(Object key)
    {
        int pos =  keyIndex(key);
        CacheObject object = model[pos];
        
        if ( object != null && object.key.equals(key) )
        {
            model[pos] = null;
            return object.getObject();
        }
        
        return null;
    }
}