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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public final class Locales
{
    private Map<String,Locale> localeMap;
    
    private static Locales instance;
    
    private Locales()
    {
        localeMap = new HashMap<String,Locale>();
        String key = null;
        for (Locale locale : Locale.getAvailableLocales())
        {
            key = (locale.getCountry() == "") ? locale.getLanguage()
                    : locale.getLanguage() + "-" + locale.getCountry();

            localeMap.put(key.toLowerCase(), locale);
        }
    }
      
    public static Locales getInstance()
    {
        if ( instance == null )
        {
            instance = new Locales();
        }
        
        return instance;
    }

    public Locale getLocale(String lstr)
    {
        Locale locale = null;
        int index = 0;
        String[] al = lstr.toLowerCase().split(",");
        String[] lc = null;
        while (locale == null && index < al.length)
        {
            lc = al[index++].split(";");
            locale = (Locale) localeMap.get(lc[0]);
        }

        return (locale == null) ? Locale.getDefault() : locale;
    }


    public Locale parseLocale(String locale)
    {
        int index = locale.indexOf("_");

        if (index < 0)
            return new Locale(locale, "", "");

        String lang = locale.substring(0, index);

        locale = locale.substring(index + 1);
        index = locale.indexOf("_");

        if (index < 0)
            return new Locale(lang, locale, "");

        String country = locale.substring(0, index);
        String var = locale.substring(index + 1);

        return new Locale(lang, country, var);
    }
}