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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.astrientfoundation.cache.GdsCache;
import org.astrientfoundation.cache.GdsCacheObject;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.Preferences;
import org.astrientfoundation.util.Timer;

public class GenericCachingFileStore extends FileStore
{
    private static final Preferences preferences = new Preferences(GenericCachingFileStore.class);
    
    private FileStore fileStore;
    private GdsCache cache = null;

    public GenericCachingFileStore(String root)
    {
        super(root);

        this.fileStore = new FileStore(root);
        
        int time = preferences.getInt("time", 50);
        long tooLarge = preferences.getLong("max", (1024 * 1000));
        double trim = preferences.getDouble("trim", .10);

        cache = new GdsCache(time, tooLarge, trim);
    }
    
    public File getRoot()
    {
        return fileStore.getRoot();
    }
    
    public GenericCachingFileStore(String root, int time, long tooLarge, double trim, int min)
    {
        super(root);
        
        this.fileStore = new FileStore(root);
        cache = new GdsCache(time, tooLarge, trim, min);
    }
    
    public GenericCachingFileStore(FileStore fileStore, int time, long tooLarge, double trim, int min)
    {
        super(fileStore.root.getPath());
        
        this.fileStore = fileStore;
        cache = new GdsCache(time, tooLarge, trim, min);
    }
    
    public GenericCachingFileStore(FileStore fileStore, GdsCache gdsCache)
    {
        super(fileStore.root.getPath());
        
        this.fileStore = fileStore;
        this.cache = gdsCache;
    }

    public String add(String context, String name, byte[] data)
    {
        String path = fileStore.add(context, name, data);
        cache.remove(path);

        return path;
    }

    public void removeContext(String context)
    {
        fileStore.removeContext(context);
        cache.clear();
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
            Log.fileSystem.log(GenericCachingFileStore.class, path + " read: " + timer.elapsed());
        }
    }

    public void writeTo(OutputStream os, String context, String name) throws IOException
    {
        Timer timer = new Timer();
        String path = "file:" + getLocation(context, name);
        
        try
        {
            GdsCacheObject object = cache.getGdsCacheObject(path);
            if (object == null)
            {
                object = new GdsCacheObject(path);
                cache.put(path, object);
            }

            object.writeTo(os);

        }
        finally
        {
        	Log.fileSystem.log(GenericCachingFileStore.class, path + " writeTo: " + timer.elapsed());
        }
    }
    
    
    public void writeTo(OutputStream os, String context, String name, int flush) throws IOException
    {
        InputStream is = null;
        try
        {
            is = getInputStream(context,name);
            
            byte[] buffer = new byte[4*1024];
            int len = 0;
            int count = 0;
            while ( (len = is.read(buffer)) != -1)
            {
                os.write(buffer,0,len);
                if ( (count+=len) > flush )
                {
                    os.flush();
                    count -= flush;
                }
            }
        }
        finally
        {
            try { is.close(); } catch (Exception e) {}
        }
    }

    public void remove(String context, String name)
    {
        String path = "file:" + getLocation(context, name);
        fileStore.remove(context, name);
        cache.remove(path);
    }
    
    public void invalidate(String context, String name)
    {
        String path = "file:" + getLocation(context, name);
        cache.remove(path);
    }

    public boolean exists(String context, String name, long newerThanTimestamp)
    {
        return fileStore.exists(context, name, newerThanTimestamp);
    }

    public boolean exists(String context, String name)
    {
        return fileStore.exists(context, name);
    }
    
    public File getFile(String context, String name) throws IOException
    {
        return fileStore.getFile(context, name);
    }

    public InputStream getInputStream(String context, String name) throws IOException
    {
        String path = "file:" + getLocation(context, name);

        GdsCacheObject object = cache.getGdsCacheObject(path);
        if (object != null)
        {
            return new ByteArrayInputStream(object.getContent());
        }
        else
        {
            return fileStore.getInputStream(context, name);   
        }
    }

    public String getLocation(String context, String name, boolean create)
    {
        return fileStore.getLocation(context, name, create);
    }

    public String getLocation(String context, String name)
    {
        return fileStore.getLocation(context, name);
    }

    public GdsCache getCache()
    {
        return cache;
    }

    public List<SmartFile> list()
    {
        return fileStore.list();
    }

    public List<SmartFile> list(String context)
    {
        return fileStore.list(context);
    }
    
    
    public byte[] get(String context, String name, long newerThan) throws IOException
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

            return object.getContent(newerThan);

        }
        finally
        {
            Log.fileSystem.log(GenericCachingFileStore.class, path + " read: " + timer.elapsed());
        }
    }

    public void writeTo(OutputStream os, String context, String name, long newerThan) throws IOException
    {
        Timer timer = new Timer();
        String path = "file:" + getLocation(context, name);
        
        try
        {
            GdsCacheObject object = cache.getGdsCacheObject(path);
            if (object == null)
            {
                object = new GdsCacheObject(path);
                cache.put(path, object);
            }

            object.writeTo(os,newerThan);

        }
        finally
        {
        	Log.fileSystem.log(GenericCachingFileStore.class, path + " writeTo: " + timer.elapsed());
        }
    }
}