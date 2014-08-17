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
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.util.Base32;
import org.astrientfoundation.util.Strings;


public final class FileUtil
{
    public static final double KIB = new Double(Math.pow(2,10)).intValue();
    public static final double MIB = new Double(Math.pow(2,20)).intValue();
    public static final double GIB = new Double(Math.pow(2,30)).intValue();
    
    protected static List<Rulelet> rules = new ArrayList<Rulelet>();
       
    static
    {
        parse("magic.mime");
    }
    
    private FileUtil()
    {
        
    }
    
    protected static void parse(String path)
    {
        LineNumberReader br = null;
        try
        {
            InputStream is  = FileUtil.class.getResourceAsStream(path);
            if ( is == null )
            {
                throw new FileNotFoundException(path);
            }
            
            InputStreamReader isReader = new InputStreamReader(new DataInputStream(is));
            
            br = new LineNumberReader(isReader);
            String s = null;

            Rulelet r = null;
            while ((s = br.readLine()) != null)
            {
                if (s.startsWith("#") || s.trim().length() == 0)
                {
                    continue;
                }
                else
                {
                    s = s.replaceAll("\\ "," ").replaceAll("  +","\t");
                    if ( s.startsWith(">>") )
                    {
                        r = new Rulelet(r);
                        r.dc = true;
                        s = s.substring(2);
                    }
                    else if ( s.startsWith(">") )
                    {
                        r = new Rulelet(r);
                        s = s.substring(1);
                    }
                    else
                    {
                        if ( r != null ) rules.add(r);
                        
                        r = new Rulelet();
                    }
                    
                    String[] sa = s.split("\t+");
                    
                    if ( sa.length == 3 )
                    {
                        r.offset = Integer.parseInt(sa[0].trim());
                        r.type = sa[1];
                        r.match = sa[2];
                    }
                    else if ( sa.length == 4 )
                    {
                        r.offset = Integer.parseInt(sa[0].trim());
                        r.type = sa[1];
                        r.match = sa[2];
                        r.mimeType = sa[3];
                    }
                    else if ( sa.length == 5 )
                    {
                        r.offset = Integer.parseInt(sa[0].trim());
                        r.type = sa[1];
                        r.match = sa[2];
                        r.mimeType = sa[3];
                        r.mimeEncoding = sa[4];
                    }
                    
                    //System.out.println(br.getLineNumber() + ". '" + r.offset + "'\t'" + r.type + "'\t'" + r.match + "'\t'" + r.mimeType + "'\t'" + r.mimeEncoding + "'");
                }                
           }
        }
        catch (FileNotFoundException fnfe)
        {
            Log.errors.log(SmartFile.class, fnfe.getMessage());
        }
        catch (Exception e)
        {
            Log.errors.log(SmartFile.class, e);
        }
        finally
        {
            try
            {
                if (br != null)
                    br.close();
            }
            catch (Exception e)
            {
            }
        }
    }
        
    public static String typeOf(byte[] data) throws IOException
    {
        return typeOf(new ByteArrayInputStream(data));
    }
    
    public static String typeOf(InputStream is) throws IOException
    {    
        DataInputStream dis = new DataInputStream(is);
        for (Rulelet r : rules )
        {        
            if ( r.test(dis) )
            {
                return r.mimeType;
            }
        }
        
        return null;
    }

    public static long computeAdler32(byte[] data)
    {
        Adler32 adler32 = new Adler32();
        adler32.update(data);
        return adler32.getValue();
    }
    
    
    public static long computeCRC32(byte[] data)
    {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static String computeMD5(byte[] data) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        
        return new String(Strings.hexEncode(md.digest()));
    }    

    
    public static String getSizeString(long number, Locale locale)
    {
        String suffix;
        double sz;   
        
        if ( number > GIB )
        {
            //gb
            sz = ( number / GIB );
            suffix = "GB";
        }
        else if ( number > MIB )
        {
            //mb
            sz = ( number / MIB );
            suffix = "MB";
        }
        else if ( number > KIB )
        {
            //kb
            sz = number / KIB;
            suffix = "KB";
        }
        else
        {
            sz = number;
            suffix = " byte(s)";
        }
        
        
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(2);
        
        return nf.format(sz) + suffix;
    }
    
    
    public static boolean copyFile(File source, File destination,long chunkSize, boolean overwrite) throws IOException
    {
        if ( destination.exists() && !overwrite )
        {
            return false;
        }
        else if ( destination.exists() && !destination.canWrite() )
        {
            return false;
        }
        else
        {
            FileChannel sourceChannel = null;
            FileChannel destinationChannel = null;
            try
            {
                sourceChannel = new FileInputStream(source).getChannel();
                destinationChannel = new FileOutputStream(destination).getChannel();
                
                if ( chunkSize == 0 )
                {
                    chunkSize = sourceChannel.size();
                }
                
                long transferred = 0;
                long stop = sourceChannel.size();
                
                for (long maxReps = ((stop / chunkSize)+1)*2, reps = 0; (reps < maxReps) && (transferred < stop); reps++)
                {
                    transferred += sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
                }

                return ( transferred >= stop );
            }
            catch (IOException e)
            {
                throw e;
            }
            finally
            {
                try { if (sourceChannel != null) sourceChannel.close(); } catch (Exception e) {}
                try { if (destinationChannel != null) destinationChannel.close(); } catch (Exception e) {}
            }
        }
    }
    
    public static String safeName(String name)
    {
        return Base32.encode(name.getBytes());
    }
    
    public static String decodeSafeName(String name)
    {
        return new String(Base32.decode(name));
    }
    
    public static String prettyFileName(String fileName, String ext, boolean spaces)
	{
		String pattern = ( spaces ) ? "[^a-zA-Z0-9\\.\\-\\_\\ ]+" : "[^a-zA-Z0-9\\.\\-\\_]+";
		String name = fileName.replaceAll(pattern,"_").trim();
		if ( ext != null )
		{
			name = name  + "." + ext;
		}
		
		
		return name;
	} 
    
    
    public static void main(String[] args)
    {
		try
		{
			System.out.println(new SmartFile(new File("F:/Internet/downloads/egradelogo.png")).getMimeType());
	    	
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	System.exit(0);
    }
}
