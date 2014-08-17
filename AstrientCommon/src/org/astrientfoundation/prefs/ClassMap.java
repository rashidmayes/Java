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
package org.astrientfoundation.prefs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.astrientfoundation.logging.Log;

public class ClassMap<E>
{
    private HashMap<String, ClassRegister<E>> map = new HashMap<String, ClassRegister<E>>();

    private String id;
    private Preferences preferences;

    public ClassMap(String id)
    {
        this.id = id;
        preferences = new Preferences(id);
    }
    
    public String getId()
    {
        return id;
    }

    @SuppressWarnings("unchecked")
    public ClassRegister<E> getRegister(String name)
    {
        ClassRegister<E> cr = map.get(name);
        if (cr == null)
        {
            String className = preferences.get(name);
            if (className != null)
            {
                try
                {
                    Class<E> c = (Class<E>)Class.forName(className);
                    cr = new ClassRegister<E>(name, c );
                    map.put(name, cr);
                }
                catch (Exception e)
                {
                    Log.errors.log(ClassMap.class, e);
                }
            }
        }

        return cr;
    }
    
    public Collection<Object> getKeys()
    {
        return Collections.unmodifiableCollection(preferences.keySet());
    }

    public Collection<ClassRegister<E>> getRegisters()
    {
        return Collections.unmodifiableCollection(map.values());
    }
}