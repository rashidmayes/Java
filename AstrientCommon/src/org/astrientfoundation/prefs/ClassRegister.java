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


public class ClassRegister<E>
{
    private String name = null;
    private Class<E> clazz = null;
    private transient int count = 0;

    public ClassRegister(String name, Class<E> clazz)
    {
        this.name = name;
        this.clazz = clazz;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Class<E> getClassObject()
    {
        count++;
        return clazz;
    }

    public void setClass(Class<E> clazz)
    {
        this.clazz = clazz;
    }

    public int getCount()
    {
        return count;
    }
}