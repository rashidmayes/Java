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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.astrientfoundation.cache.BucketCache;

public final class Dates
{
    private static BucketCache cache = new BucketCache(10);
    private static boolean lenient;
    
    private Dates()
    {
    }
    
    public static String format(long milliseconds, String format)
    {
        return format(new Date(milliseconds),format);
    }
    
    public static String format(Date date, String format)
    {
        return format(date,format,Locale.US);
    }
    
    public static String format(Date date, String format, Locale locale)
    {
        return format(date,format,null,locale);
    }   
    
    public static String format(Date date, String format, String nullmask)
    {
        return format(date, format, nullmask, Locale.US);
    }   
    
    public static SimpleDateFormat getSimpleDateFormat(String format)
    {
        return getSimpleDateFormat(format,Locale.getDefault());
    }
     
    public static SimpleDateFormat getSimpleDateFormat(String format,Locale locale)
    {
        SimpleDateFormat sdf = (SimpleDateFormat)cache.get(format);
        if ( sdf == null )
        {
            sdf = new SimpleDateFormat(format,locale);
            sdf.setLenient(lenient);
            cache.put(format,sdf);
        }
        
        return sdf;     
    }

    
    public static String format(Date date, String format, String nullmask, Locale locale)
    {
        SimpleDateFormat sdf = getSimpleDateFormat(format);

        try
        {
            return sdf.format(date);
        }
        catch (Exception e)
        {
            return nullmask;
        }
    }
    
    
    public static Date parse(String dateStr, String format)
    {
        String[] formats = { format };
        return parse(dateStr,formats);
    }
    
    public static Date parse(String dateStr, String[] formats)
    {
        SimpleDateFormat sdf;

        for (int i = 0; i < formats.length; i++)
        {
            sdf = getSimpleDateFormat(formats[i]);
            try
            {
                return sdf.parse(dateStr);
            }
            catch (Exception e)
            {
            }
        }

        return null;
    }

    public static boolean isLenient()
    {
        return lenient;
    }
    
    public static void setLenient(boolean b)
    {
        lenient = b;
    }
    
    public static String getDateString(Date date, String longPattern, String shortPattern, Locale locale)
    {
        Calendar today = Calendar.getInstance();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        if ( today.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
                && today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
        )
        {
            return Text.instance.getString("text.today",locale) + " " + Dates.format(date,shortPattern,Locale.US);
        }
        else if ( (today.get(Calendar.DAY_OF_YEAR)-1) == calendar.get(Calendar.DAY_OF_YEAR)
                && today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
        )
        {
            return Text.instance.getString("text.yesterday",locale) + " " + Dates.format(date,shortPattern,Locale.US);
        }
        else
        {
            return Dates.format(date,longPattern,Locale.US); 
        }
    }
    
    
    
    public static String format(long milliseconds, String format, TimeZone timeZone)
    {
        return format(new Date(milliseconds),format,timeZone);
    }
    
    public static String format(Date date, String format, TimeZone timeZone)
    {
        return format(date,format,Locale.US, timeZone);
    }
    
    public static String format(Date date, String format, Locale locale, TimeZone timeZone)
    {
        return format(date,format,null,locale, timeZone);
    }   
    
    public static String format(Date date, String format, String nullmask, TimeZone timeZone)
    {
        return format(date, format, nullmask, Locale.US, timeZone);
    }   
    
    public static SimpleDateFormat getSimpleDateFormat(String format, TimeZone timeZone)
    {
        return getSimpleDateFormat(format,Locale.getDefault(),timeZone);
    }
     
    public static SimpleDateFormat getSimpleDateFormat(String format,Locale locale, TimeZone timeZone)
    {
        String key = timeZone + "tz" + locale + "l" + format;
        SimpleDateFormat sdf = (SimpleDateFormat)cache.get(key);
        if ( sdf == null )
        {
            sdf = new SimpleDateFormat(format,locale);
            sdf.setLenient(lenient);
            sdf.setTimeZone(timeZone);
            cache.put(key,sdf);
        }
        
        return sdf;     
    }

    
    public static String format(Date date, String format, String nullmask, Locale locale, TimeZone timeZone)
    {
        SimpleDateFormat sdf = getSimpleDateFormat(format,timeZone);

        try
        {
            return sdf.format(date);
        }
        catch (Exception e)
        {
            return nullmask;
        }
    }
    
    
    public static Date parse(String dateStr, String format, TimeZone timeZone)
    {
        String[] formats = { format };
        return parse(dateStr,formats, timeZone);
    }
    
    public static Date parse(String dateStr, String[] formats, TimeZone timeZone)
    {
        SimpleDateFormat sdf;

        for (int i = 0; i < formats.length; i++)
        {
            sdf = getSimpleDateFormat(formats[i],timeZone);
            try
            {
                return sdf.parse(dateStr);
            }
            catch (Exception e)
            {
            }
        }

        return null;
    }

   
    public static String getDateString(Date date, String longPattern, String shortPattern, Locale locale, TimeZone timeZone)
    {
        Calendar today = Calendar.getInstance();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        if ( today.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
                && today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
        )
        {
            return Text.instance.getString("text.today",locale) + " " + Dates.format(date,shortPattern,Locale.US,timeZone);
        }
        else if ( (today.get(Calendar.DAY_OF_YEAR)-1) == calendar.get(Calendar.DAY_OF_YEAR)
                && today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
        )
        {
            return Text.instance.getString("text.yesterday",locale) + " " + Dates.format(date,shortPattern,Locale.US,timeZone);
        }
        else
        {
            return Dates.format(date,longPattern,Locale.US,timeZone); 
        }
    }
    
    /*
    public static void main(String[] args)
    {
        Date d = new Date();
        System.out.println(d);
        System.out.println(format(d,"EEEE M/d/yy h:mm a",Locale.CANADA));
        
        String s = (format(d,"EEEE M/d/yy h:mm a",Locale.CANADA, TimeZone.getTimeZone("PST")));
        System.out.println(s);
        System.out.println("\n");
        
        System.out.println(parse(s,"EEEEE M/d/yy h:mm a"));
        System.out.println(parse(s,"EEEEE M/d/yy h:mm a",TimeZone.getTimeZone("PST")));
        
        
        System.exit(0);
    }*/
}
