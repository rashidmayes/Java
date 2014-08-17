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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.astrientfoundation.logging.Log;


public class GdsCacheObject extends CacheObject
{
	private long size = 1;
	private long loadTime = 1;
	private long lastUpdated;
	private int hits = 0;
	private String url = null;
	private long maxSize;
	
	protected SoftReference<byte[]> ref = null;
	
	public GdsCacheObject(String url)
	{
		this(url, 25);
	}

	public GdsCacheObject(String url, int lifeSpan)
	{
		super(url,null,lifeSpan);
		this.url = url;
	}
	
	
	public double cost()
	{
		return     (1.0/ (1+(size/(1+loadTime))))
		         * (1.0/Math.log(size+1))
		         * hits;
	}
	
	public byte[] getContent()
	{
		return getContent(0);
	}
	
	public byte[] getContent(long newerThan)
	{
		hits++;
		byte[] ba = ( ref == null ) ? null : (byte[])ref.get();
		if ( ba == null  || lastUpdated < newerThan )
		{
			try
			{
				ba = getBytes(url);
				this.ref = new SoftReference<byte[]>(ba);
			}
			catch (Exception e)
			{
				Log.errors.log(getClass(),e);
			}
			finally
			{
				lastUpdated = System.currentTimeMillis();
			}
			
		}
		
		return ba;
	}

	public void writeTo(OutputStream os) throws IOException
	{
		writeTo(os,0);
	}
	
	public void writeTo(OutputStream os, long newerThan) throws IOException
	{
		hits++;
		byte[] ba = ( ref == null ) ? null : (byte[])ref.get();
		if ( ba == null || lastUpdated < newerThan )
		{
			InputStream is = null;
			ByteArrayOutputStream bos = null;
				
			try
			{
				is = getInputStream(url);
				ba = new byte[1024 * 4];
				
				int i = 0;
				bos = new ByteArrayOutputStream(1024 * 4);
				
				boolean faulted = false;
				int totalRead = 0;
				while ( (i = is.read(ba)) != -1 )
				{
				    if ( !(faulted = ((totalRead+=i) > maxSize )) )
				    {
				        bos.write(ba,0,i);
				    }
						
					os.write(ba,0,i);
				}
				
				if ( faulted )
				{
					size = maxSize;
					this.ref = null;
				}
				else
				{
					size = bos.size();
					this.ref = new SoftReference<byte[]>(bos.toByteArray());   
				}
			}
			finally
			{
				if ( is != null ) { try { is.close(); } catch (Exception e) {} }
				loadTime = 0;
				lastUpdated = System.currentTimeMillis();
			}
		}
		else
		{
		    os.write(ba);
		}
	}
		
	public byte[] getBytes(String url) throws IOException
	{
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		
		try
		{
			is = getInputStream(url);
			byte[] ba = new byte[1024 * 10];
		
			int i = 0;
			bos = new ByteArrayOutputStream(512);
			while ( (i = is.read(ba)) != -1 )
			{
				bos.write(ba,0,i);
			}
			
			size = bos.size();
		}
		finally
		{
			if ( is != null ) { try { is.close(); } catch (Exception e) {} }
			loadTime = 0;
			Log.info.log(GdsCacheObject.class,url + "|READBYTES|" + cost());
		}
			
		return bos.toByteArray();
	}	
	
	
	public InputStream getInputStream(String url) throws IOException
	{
		URL u = new URL(url);
		URLConnection urlConn = u.openConnection();
		return urlConn.getInputStream();	
	}
	
	public Object get()
	{
		return ( ref == null ) ? null : ref.get();
	}
	
	/**
	 * @return
	 */
	public int getHits()
	{
		return hits;
	}

	/**
	 * @return
	 */
	public long getLoadTime()
	{
		return loadTime;
	}

	/**
	 * @return
	 */
	public long getSize()
	{
		return size;
	}

	/**
	 * @return
	 */
	public String getUrl()
	{
		return url;
	}
	
    /**
     * @return Returns the maxSize.
     */
    public long getMaxSize()
    {
        return maxSize;
    }
    
    /**
     * @param maxSize The maxSize to set.
     */
    public void setMaxSize(long maxSize)
    {
        this.maxSize = maxSize;
    }
    
    
    public String toString()
    {
        Date date = new Date();
        
        StringBuffer buffer = new StringBuffer();
        buffer.append(key).append(" [ created: ");
        date.setTime(createTime);
        buffer.append(date).append(", last accessed: ");
        date.setTime(lastAccessedTime);
        buffer.append(date).append(", hits: ")
        .append(this.hits).append(", maxSize: ")
        .append(this.maxSize).append(", size: ")
        .append(this.size).append(", cost: ")
        .append(this.cost()).append(", URL: ")
        .append(this.url).append(", loadTime: ")
        .append(this.loadTime).append(", lifespan: ")
        .append(this.getLifeSpan()).append(", object: ")
        .append(this.getObject()).append(" ]");
        
        return buffer.toString();
    }

	public long getLastUpdated() 
	{
		return lastUpdated;
	}
}
