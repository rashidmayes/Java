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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.astrientfoundation.logging.Log;


public class HashedFileStore extends FileStore
{
	public HashedFileStore(String root)
	{
	    super(root);
	}
	

	public String getHashDir(String context)
	{
	    return Integer.toString(( Math.abs(context.hashCode()) % MAX_DIRECTORIES ));
	}
	
	public String add(String context, String name, byte[] data)
	{
	    File contextRoot = new File(root,getHashDir(context));
		File dir = new File(contextRoot,context);
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
	    File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);
		dir.delete();
	}
	
	
    public File getFile(String context, String name) throws IOException
    {
        File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);
        File file = new File(dir,name);
        
        return file;
    }
	
	public FileInputStream getInputStream(String context, String name) throws IOException
	{
	    File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);
		File file = new File(dir,name);
		
		return new FileInputStream(file);
	}
	
	
	public String getLocation(String context, String name, boolean create)
	{
	    File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);

		if ( create && !dir.exists() )
		{
			dir.mkdirs();
		}
		
		File file = new File(dir,name);
		return file.getAbsolutePath();		
	}
	
	public boolean exists(String context,String name, long newerThanTimestamp)
	{
	    File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);
		File file = new File(dir,name);
		return ( file.exists() && file.lastModified() > newerThanTimestamp );
	}
	
	public boolean exists(String context,String name)
	{
	    File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);
		File file = new File(dir,name);
		return file.exists();		    
	}
	
	public void remove(String context, String name)
	{
	    File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);
		File file = new File(dir,name);
		file.delete();
	}
	
	public List<SmartFile> list(String context)
	{
	    List<SmartFile> files = new LinkedList<SmartFile>();
        File contextRoot = new File(root,getHashDir(context));
        File dir = new File(contextRoot,context);	    

	    if ( dir.exists() )
	    {
	        for ( File file : dir.listFiles() )
	        {
	            files.add(new SmartFile(file));
	        }
	    }

	    return files;
	}
}
