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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class IconExtractor
{
    private String MANIFEST = "META-INF/MANIFEST.MF";
    
    public byte[] extract(String path) throws ZipException, IOException
    {
        return extract(new File(path));
    }
    
    public byte[] extract(File jarFile) throws ZipException, IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        ZipFile zipFile = new ZipFile(jarFile);
        try
        {
            /*for ( Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements(); )
            {
                System.out.println(e.nextElement().getName());
            }*/
            
            ZipEntry entry = zipFile.getEntry(MANIFEST);

            InputStream is = zipFile.getInputStream(entry);
            if ( is != null )
            {
                Properties props = new Properties();
                props.load(is);
                
                String iconFile = props.getProperty("MIDlet-Icon");
                if ( Strings.isNull(iconFile) )
                {
                    props.list(System.out);
                    String midlet1 = props.getProperty("MIDlet-1");
                    if ( !Strings.isNull(midlet1) )
                    {
                        String[] parts = midlet1.split(",");
                        if ( parts.length > 2 )
                        {
                            iconFile = parts[1].trim();
                            if ( iconFile.startsWith("/") )
                            {
                                iconFile = iconFile.substring(1);
                            }
                        }
                    }
                }
                
                if ( iconFile != null )
                {
                    ZipEntry iconEntry = zipFile.getEntry(iconFile);
                    
                    if ( iconEntry != null )
                    {
                        FileOutputStream fos = new FileOutputStream("/tmp/icon.png");
                        InputStream zis = zipFile.getInputStream(iconEntry);
                        int r = 0;
                        byte[] buffer = new byte[4*1024];
                        while( (r = zis.read(buffer)) != -1 )
                        {
                            baos.write(buffer,0,r);
                            fos.write(buffer,0,r);
                        }
                        
                        fos.close();
                        zis.close();
                    }
                }
            }    
        }
        finally
        {
            zipFile.close();    
        }
                
        return baos.toByteArray();
    }
}
