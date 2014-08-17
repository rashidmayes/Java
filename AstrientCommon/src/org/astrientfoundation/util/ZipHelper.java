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
package org.astrientfoundation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.astrientfoundation.cache.BucketCache;
import org.astrientfoundation.cache.Cache;
import org.astrientfoundation.cache.GdsCache;
import org.astrientfoundation.cache.GdsCacheObject;


public class ZipHelper
{
    private static final GdsCache gdsCache = new GdsCache(50, (1024 * 500), .10);
    private static final Cache cache = new BucketCache(5);
    
    private static int level = 0;

    public ZipHelper()
    {
    }

    public void compress(File f) throws FileNotFoundException, IOException
    {
        String name = f.getName();
        int dotIndex = name.lastIndexOf(".");

        if (dotIndex == -1)
        {
            name = name.substring(0, dotIndex);
        }

        compress(f, name + ".jar");
    }

    public void compress(File f, String name) throws FileNotFoundException, IOException
    {
        File[] files = { f };
        compress(files, name);
    }

    public void compress(File[] files, String name) throws FileNotFoundException, IOException
    {
        if (files.length > 0)
        {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            JarOutputStream jos = null;
            ZipEntry ze = null;

            try
            {
                fos = new FileOutputStream(name);
                jos = new JarOutputStream(fos);

                jos.setLevel(level);

                File f;
                for (int i = 0; i < files.length; i++)
                {
                    f = files[i];
                    if (f.canRead() && !f.isDirectory())
                    {
                        try
                        {
                            fis = new FileInputStream(f);
                            ze = new ZipEntry(f.getPath());
                            jos.putNextEntry(ze);

                            byte[] ba = new byte[1024];
                            for (int len = 0; (len = fis.read(ba)) != -1;)
                            {
                                jos.write(ba, 0, len);
                            }

                            jos.closeEntry();
                            fis.close();
                        }
                        finally
                        {
                            try
                            {
                                fis.close();
                            }
                            catch (Exception e)
                            {
                            }
                            try
                            {
                                fos.close();
                            }
                            catch (Exception e)
                            {
                            }
                        }
                    }
                }
            }
            finally
            {
                try
                {
                    jos.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }
    
    public static void writeTo(File file, String path, OutputStream os) throws IOException
    {
        String url = "jar:file:" +  file.getPath() + "!" + path;
        GdsCacheObject object = gdsCache.getGdsCacheObject(url);
        if (object == null)
        {
            object = new GdsCacheObject(url);
            gdsCache.put(path, object);
        }

        object.writeTo(os);
    }
    
    
    public static byte[] getBytes(File file, String path)
    {
        String url = "jar:file:" +  file.getPath() + "!" + path;
        GdsCacheObject object = gdsCache.getGdsCacheObject(url);
        if (object == null)
        {
            object = new GdsCacheObject(url);
            gdsCache.put(path, object);
        }

        return object.getContent();
    }
    
    
    @SuppressWarnings("unchecked")
    private static List<Entry> getAllFiles(File file) throws IOException
    {
        List<Entry> all = (ArrayList<Entry>)cache.get(file.getPath());
        if ( all == null )
        {
            all = new ArrayList<Entry>();
            
            ZipInputStream zis = null;
            FileInputStream fis = null;
            
            try
            {
                fis = new FileInputStream(file);
                zis = new ZipInputStream(fis);
                
                ZipEntry zipEntry;
                Entry entry;
                while ( (zipEntry = zis.getNextEntry()) != null )
                {
                    entry = new Entry(zipEntry);
                    all.add(entry);
                }
                
                cache.put(file.getPath(),all);
            }
            finally
            {
                try { zis.close(); } catch (Exception e) {}
                try { fis.close(); } catch (Exception e) {}
            }
        }
        
        return all;
    }
    
    
    @SuppressWarnings("unchecked")
    public static List<Entry> getFiles(File file, String dir) throws IOException
    {        
        String key = file.getPath() + ":" + dir;
        List<Entry> files = (ArrayList<Entry>)cache.get(key);
        
        if ( files == null )
        {
            List<Entry> all = getAllFiles(file);
            
            files = new ArrayList<Entry>();
            File root = new File(dir);
            for ( Entry entry : all )
            {
                //System.out.println(root.getPath() + " + " + entry.getParent() + " " + (entry.getParent().equals(root.getPath())));
                if ( entry.getParent().equals(root.getPath()) )
                {
                    //System.out.println(root.getPath() + " + " + entry.getParent());
                    files.add(entry);
                }
            }
            
            cache.put(key,files);
        }
        
        return files;
    }
}
class Entry
{
    String name;    // entry name
    long modified = -1; // modification time (in DOS time)
    long crc = -1;  // crc-32 of entry data
    long size = -1; // uncompressed size of entry data
    long compressedSize = -1;    // compressed size of entry data
    String comment;
    private String parent;
    boolean directory;
    private String path;
    
    protected Entry(ZipEntry zipEntry)
    {
        compressedSize = zipEntry.getCompressedSize();
        comment = zipEntry.getComment();
        size = zipEntry.getSize();
        directory = zipEntry.isDirectory();
        crc = zipEntry.getCrc();
        modified = zipEntry.getTime();

        path = "/"+zipEntry.getName();
        File f = new File(path);
        
        parent = f.getParent();
        name = f.getName();
    }

    public String getName()
    {
        return name;
    }

    public long getModified()
    {
        return modified;
    }

    public long getCrc()
    {
        return crc;
    }

    public long getSize()
    {
        return size;
    }

    public long getCompressedSize()
    {
        return compressedSize;
    }

    public String getComment()
    {
        return comment;
    }

    public boolean isDirectory()
    {
        return directory;
    }
    
    public String getParent()
    {
        return parent;
    }
    
    public String getPath()
    {
        return path;
    }
}