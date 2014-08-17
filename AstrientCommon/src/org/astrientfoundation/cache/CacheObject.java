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

import java.lang.ref.SoftReference;
import java.util.Date;

public class CacheObject extends SoftReference<Object>
{
    public static final long DEFAULT_LIFE_SPAN = Long.MAX_VALUE;
	protected long createTime;
	protected long lastAccessedTime;
	protected long lifeSpan;
	protected Object key = null;

	public CacheObject(Object key, Object value)
	{
		this(key, value, DEFAULT_LIFE_SPAN);
	}

	public CacheObject(Object key, Object value, long lifeSpan)
	{
		super(value);
		
		this.key = key;
		this.lifeSpan = lifeSpan;
		
		this.createTime = System.currentTimeMillis();
		this.lastAccessedTime = createTime;
	}

	public long getLifeSpan()
	{
		return lifeSpan;
	}

	public void setLifeSpan(int lifeSpan)
	{
		this.lifeSpan = lifeSpan;
	}

	public long getCreateTime()
	{
		return createTime;
	}

	public long getLastAccessedTime()
	{
		return lastAccessedTime;
	}

	public Object getObject()
	{
		lastAccessedTime = System.currentTimeMillis();
		return get();
	}

	public Object getKey()
	{
		return key;
	}

	public boolean isOlderThan(long milliseconds)
	{
		return isOlderThan(milliseconds, createTime);
	}

	private boolean isOlderThan(long milliseconds, long t)
	{
		return ((System.currentTimeMillis() - t) > milliseconds);
	}

	public boolean isOutDated()
	{
		return isOlderThan(lifeSpan, lastAccessedTime);
	}

	public boolean isExpired()
	{
		return isOlderThan(lifeSpan);
	}
	
	public String toString()
	{
	    Date date = new Date();
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(key).append(" [ created: ");
	    date.setTime(createTime);
	    buffer.append(date).append(", last accessed: ");
	    date.setTime(lastAccessedTime);
	    buffer.append(date).append(", lifespan: ")
	    .append(this.getLifeSpan()).append(", object: ")
	    .append(this.getObject()).append(" ]");
	    
	    return buffer.toString();
	}
}