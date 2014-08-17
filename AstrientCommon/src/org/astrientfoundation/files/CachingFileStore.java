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
package org.astrientfoundation.files;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.astrientfoundation.cache.BucketCache;
import org.astrientfoundation.cache.Cache;
import org.astrientfoundation.cache.GdsCache;
import org.astrientfoundation.cache.GdsCacheObject;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.Preferences;
import org.astrientfoundation.util.Timer;

public class CachingFileStore extends FileStore
{
    private static final Preferences preferences = new Preferences(CachingFileStore.class);
    private GdsCache cache = null;
    private Cache dirCache;

    public CachingFileStore(String root)
    {
        super(root);

        int time = preferences.getInt("time", 50);
        long tooLarge = preferences.getLong("max", (1024 * 1000));
        double trim = preferences.getDouble("trim", .10);

        cache = new GdsCache(time, tooLarge, trim);
        dirCache = new BucketCache(preferences.getInt("buckets",3));
    }
    
    public CachingFileStore(String root, int time, long tooLarge, double trim, int min)
    {
        super(root);
        cache = new GdsCache(time, tooLarge, trim, min);
    }
    
    @SuppressWarnings("unchecked")
    public List<SmartFile> list()
    {
        List<SmartFile> files = (List<SmartFile>)dirCache.get("");
        if ( files == null )
        {
            files = super.list();
            dirCache.put("",files);
        }
        
        return files;
    }
    
    @SuppressWarnings("unchecked")
    public List<SmartFile> list(String context)
    {
        List<SmartFile> files = (List<SmartFile>)dirCache.get(context);
        if ( files == null )
        {
            files = super.list(context);
            dirCache.put(context,files);
        }

        return files;
    }

    public String add(String context, String name, byte[] data)
    {
        String path = super.add(context, name, data);
        cache.remove(path);
        dirCache.remove(context);
        dirCache.remove("");
        
        return path;
    }

    public void removeContext(String context)
    {
        super.removeContext(context);
        cache.clear();
        dirCache.remove(context);
        dirCache.remove("");
    }

    public byte[] get(String context, String name) throws IOException
    {
        Timer timer = new Timer();
        String path = "file:" +  getLocation(context, name);
        
        try
        {
            GdsCacheObject object = cache.getGdsCacheObject(path);
            if (object == null)
            {
                object = new GdsCacheObject(path);
                cache.put(path, object);
            }

            return object.getContent();

        }
        finally
        {
            Log.fileSystem.log(FileStore.class, path + " get: " + timer.elapsed());
        }
    }

    public void writeTo(OutputStream os, String context, String name) throws IOException
    {
        String path = "file:" + getLocation(context, name);
        
        GdsCacheObject object = cache.getGdsCacheObject(path);
        if (object == null)
        {
            object = new GdsCacheObject(path);
            cache.put(path, object);
        }

        object.writeTo(os);
    }

    public void remove(String context, String name)
    {
        String path = "file:" + getLocation(context, name);
        super.remove(context, name);
        cache.remove(path);
        dirCache.remove(context);
    }
    
    public void invalidate(String context, String name)
    {
        String path = "file:" + getLocation(context, name);
        cache.remove(path);
        dirCache.remove(context);
    }
    
    public GdsCache getCache()
    {
        return cache;
    }
}