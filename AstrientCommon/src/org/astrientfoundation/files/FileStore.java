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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.util.Timer;


public class FileStore
{
    public static final int MAX_DIRECTORIES = 31995;
    private static final byte[] EMPTY_BYTES = new byte[0];
    
	protected boolean debug = false;
	File root = null;
	
	public FileStore(String root)
	{
		this.root = new File(root);
		if ( !this.root.exists() )
		{
			this.root.mkdirs();
		}
	}
	
	public File getRoot()
	{
	    return root;
	}
	
	public List<SmartFile> list()
	{
	    List<SmartFile> files = new LinkedList<SmartFile>();
	    File dir = root;
	    if ( dir.exists() )
	    {
	        for ( File file : dir.listFiles() )
	        {
	            files.add(new SmartFile(file));
	        }
	    }
	    
	    return files;
	}
	
	public List<SmartFile> list(String context)
	{
	    List<SmartFile> files = new LinkedList<SmartFile>();
	    File dir = new File(root,context);
	    if ( dir.exists() )
	    {
	        for ( File file : dir.listFiles() )
	        {
	            files.add(new SmartFile(file));
	        }
	    }

	    return files;
	}
	
	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}
	
	public boolean isDebug()
	{
		return debug;
	}
	
	public String add(String context, String name, byte[] data)
	{
		File dir = new File(root,context);
		dir.mkdirs();
		
		File file = new File(dir,name);
		
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(file);
			fos.write(data);
		}
		catch (Exception e)
		{
		    Log.errors.log(getClass(),e);
		}
		finally
		{
			try { fos.close(); } catch (Exception e){}
		}
		
		return file.getPath();	
	}
	
	public void removeContext(String context)
	{
		File file = new File(root,context);
		file.delete();
	}
	
	public byte[] get(String context, String name) throws IOException
	{
		Timer timer = new Timer();
		File file = getFile(context,name);
		if ( file.exists() && file.canRead() )
		{
            InputStream fis = null;
            
            try
            {
                fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                int i = 0;
                byte[] ba = new byte[4*1024];
                while ( ( i = fis.read(ba)) != -1 )
                {
                    bos.write(ba,0,i);
                }
                
                return bos.toByteArray(); 
            }
            finally
            {
                try { fis.close(); } catch ( Exception e ) {}
                Log.fileSystem.log(FileStore.class, file.getPath() + " get: " + timer.elapsed());
            }
		}
		
		return EMPTY_BYTES;
	}
	
	
    public File getFile(String context, String name) throws IOException
    {
        File dir = new File(root, context);
        File file = new File(dir,name);
        
        return file;
    }
	
	public InputStream getInputStream(String context, String name) throws IOException
	{
		File file = getFile(context,name);
		
		return new FileInputStream(file);
	}
	
	public String getLocation(String context, String name)
	{
		return getLocation(context,name,true);
	}
	
	public String getLocation(String context, String name, boolean create)
	{
		File dir = new File(root, context);
		if ( create && !dir.exists() )
		{
			dir.mkdirs();
		}
		
		File file = new File(dir,name);
		return file.getAbsolutePath();		
	}
	
	public boolean exists(String context,String name, long newerThanTimestamp)
	{
		File dir = new File(root, context);
		File file = new File(dir,name);
		return ( file.exists() && file.lastModified() > newerThanTimestamp );
	}
	
	public boolean exists(String context,String name)
	{
		File dir = new File(root, context);
		File file = new File(dir,name);
		return file.exists();		    
	}
	
	public void remove(String context, String name)
	{
		File dir = new File(root, context);
		File file = new File(dir,name);
		file.delete();
	}
	
    public void writeTo(OutputStream os, String context, String name) throws IOException
    {
        Timer timer = new Timer();

        FileInputStream fis = null;
        String path = getLocation(context, name);
        
        try
        {           
            fis = new FileInputStream(new File(path));
            
            byte[] buffer = new byte[4*1024];
            int len = 0;
            while ( (len = fis.read(buffer)) != -1)
            {
                os.write(buffer,0,len);
            }
        }
        finally
        {
            try { fis.close(); } catch (Exception e) {}
            Log.fileSystem.log(CachingFileStore.class, path + " writeTo.1: " + timer.elapsed());
        }
    }
    
    
    public void writeTo(OutputStream os, String context, String name, int flush) throws IOException
    {
        Timer timer = new Timer();
        String path = getLocation(context, name);
        
        FileInputStream fis = null;
        try
        {
            
            fis = new FileInputStream(new File(path));
            
            byte[] buffer = new byte[4*1024];
            int len = 0;
            int count = 0;
            while ( (len = fis.read(buffer)) != -1)
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
            try { fis.close(); } catch (Exception e) {}
            Log.fileSystem.log(CachingFileStore.class, path + " writeTo.2: " + timer.elapsed());
        }
    }
}
