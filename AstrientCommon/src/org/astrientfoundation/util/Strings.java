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

import java.text.BreakIterator;
import java.util.Locale;


public final class Strings
{
    private static char[] HEXCHARS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};    
    
    public static char[] hexEncode(byte[] bytes)
    {
        char[] result = new char[bytes.length*2];
        int b;
        for (int i = 0, j = 0; i < bytes.length; i++)
        {
            b = bytes[i] & 0xff;
            result[j++] = HEXCHARS[b >> 4];
            result[j++] = HEXCHARS[b & 0xf];
        }
        return result;
    }
    
    public static String fixQoutes(String in)
    {
    	if ( in == null )
    	{
    		return in;
    	}
    	else
    	{
    		StringBuffer buffer = new StringBuffer(in);
    		for ( int i = 0; i < in.length(); i ++)
    		{
    			if ( in.charAt(i) == 8217 || in.charAt(i) == 8216)
    			{
    				buffer.setCharAt(i,'\'');
    			}
    			else if ( in.charAt(i) == 8220 || in.charAt(i) == 8221 )
    			{
    				buffer.setCharAt(i,'\"');
    			}
    		}
    		
    		return buffer.toString();
    	}
    	
    	
    	//return ( in == null ) ? in : in.replaceAll("[‘’]","\'").replaceAll("[“”]","\"");
    }
    
    
    public static String getText(String text, int len,Locale locale,String suffix)
    {
        if ( text != null )
        {
            if ( text.length() > len )
            {
                BreakIterator bi = BreakIterator.getWordInstance(locale);
                bi.setText(text);
                int brk = bi.preceding(len);
  
                return ( brk == 0 ) ? text.substring(0,len).trim() + suffix : text.substring(0,brk).trim() + suffix;
            }
            else
            {
                return text;
            }
        }
        
        
        return null;
    }
    
    public static String getText(String text, int len,Locale locale)
    {
        return getText(text,len,locale,"...");
    }
    
	public static String join(Object[] sa, String delim)
	{
		return (sa == null) ? "" : join(sa, 0, sa.length, delim);
	}

	public static String join(Object[] sa, int start, int stop, String delim)
    {
        if (sa != null)
        {
            if ( stop > sa.length-1 )
            {
                stop = sa.length-1;
            }
            
            if ( start <= stop )
            {
                StringBuffer buffer = new StringBuffer(sa[start].toString());

                start++;
                while (start <= stop)
                {
                    buffer.append(delim);
                    buffer.append(sa[start++]);
                }

                return buffer.toString();
            }
        }

        return "";
    }

	public static int getSoundexCode(char c)
	{
		switch ((int) c)
		{
			case (int) 'A' :
				return -1;
			case (int) 'B' :
				return 1;
			case (int) 'C' :
				return 2;
			case (int) 'D' :
				return 3;
			case (int) 'E' :
				return -1;
			case (int) 'F' :
				return 1;
			case (int) 'G' :
				return 2;
			case (int) 'H' :
				return -1;
			case (int) 'I' :
				return -1;
			case (int) 'J' :
				return 2;
			case (int) 'K' :
				return 2;
			case (int) 'L' :
				return 4;
			case (int) 'M' :
				return 5;
			case (int) 'N' :
				return 5;
			case (int) 'O' :
				return -1;
			case (int) 'P' :
				return 1;
			case (int) 'Q' :
				return 2;
			case (int) 'R' :
				return 6;
			case (int) 'S' :
				return 2;
			case (int) 'T' :
				return 3;
			case (int) 'U' :
				return -1;
			case (int) 'V' :
				return 1;
			case (int) 'W' :
				return -1;
			case (int) 'X' :
				return 2;
			case (int) 'Y' :
				return -1;
			case (int) 'Z' :
				return 2;
			default :
				return -1;
		}
	}

	public static String getSoundexCode(String s)
	{
		s = s.toUpperCase();
		StringBuffer soundex = new StringBuffer(4);
		soundex.append(s.charAt(0));

		int code, c = 1;
		for (int i = 1, len = s.length(); i < len && c < 4; i++)
		{
			if (s.charAt(i) != s.charAt(i - 1))
			{
				if ((code = getSoundexCode(s.charAt(i))) != -1)
				{
					soundex.append(code);
					c++;
				}
			}
		}

		for (; c < 4; c++)
		{
			soundex.append("0");
		}

		return soundex.toString();
	}
	
	
	public static boolean isNull(String s)
	{
		return (s == null || s.trim().length()== 0 );
	}
	
	
	public static String ifNull(Object o, String def)
	{
		if (o != null)
		{
			String s = String.valueOf(o);
			if (s.trim().length() > 0)
			{
				return s;
			}
		}

		return def;
	}
	
	public static String randomString(int length)
	{
	    StringBuffer buffer = new StringBuffer();
	    for ( int i = 0; i++ < length; buffer.append( (char)('a' + (new Double(Math.random()*26).intValue()))) );
	    
	    return buffer.toString();
	}
    
    public static String[] split(String s, int i)
    {
        int len = s.length();
        String[] sa = new String[len / i + (((len % i) > 0) ? 1 : 0)];
        for (int j = 0; j < (sa.length - 1) || ((sa[j] = s.substring(j * i)) == null); sa[j] = s.substring(j * i, j++ * i + i));
        return sa;
    }
    
    public static String noTags(String text)
    {
        return text.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}