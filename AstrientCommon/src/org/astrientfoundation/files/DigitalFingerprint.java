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

import org.astrientfoundation.util.Strings;


public class DigitalFingerprint
{ 
    private String MD5 = "";
    private String name = "";
    private String path = "";
    private long length;
    private String mimeType  = "";
    private byte firstByte;
    private byte lastByte;
    private byte centerByte;
    private long created;
    private long modified;
    private String vendor  = "";
    private long adler32;
    private long crc32;

    public byte getCenterByte()
    {
        return centerByte;
    }
    
    public void setCenterByte(byte centerByte)
    {
        this.centerByte = centerByte;
    }
    
    public long getCreated()
    {
        return created;
    }
    
    public void setCreated(long created)
    {
        this.created = created;
    }
    
    public byte getFirstByte()
    {
        return firstByte;
    }
    
    public void setFirstByte(byte firstByte)
    {
        this.firstByte = firstByte;
    }
    
    public byte getLastByte()
    {
        return lastByte;
    }
    
    public void setLastByte(byte lastByte)
    {
        this.lastByte = lastByte;
    }
    
    public long getLength()
    {
        return length;
    }
    
    public void setLength(long length)
    {
        this.length = length;
    }
    
    public String getMD5()
    {
        return MD5;
    }
    
    public void setMD5(String md5)
    {
        MD5 = md5;
    }
    
    public String getMimeType()
    {
        return mimeType;
    }
    
    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }
    
    public long getModified()
    {
        return modified;
    }
    
    public void setModified(long modified)
    {
        this.modified = modified;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getVendor()
    {
        return vendor;
    }
    
    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }
    
    public long getAdler32()
    {
        return adler32;
    }

    public void setAdler32(long adler32)
    {
        this.adler32 = adler32;
    }

    public long getCrc32()
    {
        return crc32;
    }

    public void setCrc32(long crc32)
    {
        this.crc32 = crc32;
    }
    
    
    public double compareTo(DigitalFingerprint in)
    {
        double total = .50;
        
        if ( in.getAdler32() == this.getAdler32() ) total += .30;
        if ( this.getFirstByte() == in.getFirstByte() ) total += .05;
        if ( this.getLastByte() == in.getLastByte() ) total += .05;
        if ( this.getCenterByte() == in.getCenterByte() ) total += .05;
        if ( this.getCrc32() == in.getCrc32() ) total += .30;
        
        //long timeDelta = (1000L*60*60*24*30);//30 days
        total += ((1.0/(1+Math.abs(this.getCreated() - in.getCreated()))) * .05);
        total += ((1.0/(1+Math.abs(this.getLength() - in.getLength()))) * .10);
        
        if ( this.getMD5() == in.getMD5() ) total += .50;
        
        if ( this.getMimeType() == in.getMimeType() ) total += .10;
        
        total += ((1.0/(1+Math.abs(this.getModified() - in.getModified()))) * .05);
        
        if ( this.getName().equalsIgnoreCase(in.getName()) ) total += .10;
        if ( Strings.getSoundexCode(this.getName()) == Strings.getSoundexCode(in.getName()) ) total += .05;
        
        if ( this.getPath().equalsIgnoreCase(in.getPath()) ) total += .20;
        
        return total;
    }
}
