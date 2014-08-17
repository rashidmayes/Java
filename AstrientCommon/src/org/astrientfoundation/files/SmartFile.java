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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.security.NoSuchAlgorithmException;


public class SmartFile extends File
{
    private static final long serialVersionUID = 4121553531253342825L;
    
    
    public SmartFile(File file)
    {
        super(file.getPath());
    }
    
    /**
     * @param parent
     * @param child
     */
    public SmartFile(File parent, String child)
    {
        super(parent, child);
    }

    /**
     * @param pathname
     */
    public SmartFile(String pathname)
    {
        super(pathname);
    }

    /**
     * @param parent
     * @param child
     */
    public SmartFile(String parent, String child)
    {
        super(parent, child);
    }
    
    /**
     * @param uri
     */
    public SmartFile(URI uri)
    {
        super(uri);
    }
    
    public String getMimeType() throws IOException
    {
        if ( this.isDirectory() )
        {
            return "application/x-not-regular-file";
        }
        else
        {
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream(this);
                return FileUtil.typeOf(new BufferedInputStream(fis,63792));
            }
            finally
            {
                try { fis.close(); } catch (Exception e) {}
            }
        }
    }
    
    public DigitalFingerprint getFingerprint() throws IOException, NoSuchAlgorithmException
    {
        DigitalFingerprint fingerprint = new DigitalFingerprint();
        fingerprint.setName(getName());
        fingerprint.setPath(getPath());
        fingerprint.setLength(length());
        fingerprint.setModified(lastModified());
        
        
        RandomAccessFile raf = null;
        try
        {
            raf = new RandomAccessFile(this,"r");
            byte[] data = new byte[(int)length()];
            raf.readFully(data);
        
            fingerprint.setMimeType(FileUtil.typeOf(data));
            fingerprint.setAdler32(FileUtil.computeAdler32(data));
            fingerprint.setCrc32(FileUtil.computeCRC32(data));
            fingerprint.setMD5(FileUtil.computeMD5(data));
            
            if ( data.length > 0 )
            {
                fingerprint.setFirstByte(data[0]);
                fingerprint.setLastByte(data[data.length-1]);
                fingerprint.setCenterByte(data[data.length/2]);   
            }
        }
        finally
        {
            
            if ( raf != null ) { try { raf.close(); } catch (Exception e) {} }
        }
        
        return fingerprint;
    }

	/*
    public static void main(String[] args)
    {
        try
        {
            File dir = new File("c:/downloads/");
            
            File[] files = dir.listFiles();
            for ( int i = 0; i < files.length; i++ )
            {
                if ( files[i].isDirectory() ) continue;
                
                AstrientFile sf = new AstrientFile(files[i].getPath());
                
                System.out.println(sf.getName() + " " + sf.getMimeType());
                
            }
            
            Long.reverse(8);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
            
        
        System.exit(0);
    }*/
}
