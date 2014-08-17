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
package org.astrientfoundation.services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.ClassMap;
import org.astrientfoundation.prefs.ClassRegister;
import org.astrientfoundation.prefs.Preferences;

public final class ServiceManager
{
    public static final ServiceManager d = new ServiceManager();
    
    private Preferences preferences;
    private ClassMap<Service> classMap;
    private ServiceContext serviceContext;
    private Map<Class<Service>,Service> services;
    
    private ServiceManager()
    {
        preferences = new Preferences("comp.services");
        classMap = new ClassMap<Service>("comp.services.run");
        serviceContext = new ServiceContext(preferences);
        
        services = new HashMap<Class<Service>,Service>();
        Service service;
        Collection<Object> keys = classMap.getKeys();
        ClassRegister<Service> register;
        for ( Object object : keys )
        {
            try
            {
                register = classMap.getRegister(object.toString());
                service = register.getClassObject().newInstance();
                services.put(register.getClassObject(),service);
                
                service.start(serviceContext);        
            }
            catch (Exception e)
            {
                Log.services.log(getClass(),e);
            }
        }
    }
    
    public Map<Class<Service>,Service> getServices()
    {
        return Collections.unmodifiableMap(services);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> id)
    {
        return (T)services.get(id);
    }
      
    @SuppressWarnings("unchecked")
    public boolean isAlive(Class id)
    {
        synchronized (id)
        {
            Service service = services.get(id);
            if ( service != null )
            {
                return service.isAlive();
            }
        }
        
        return false;        
    }

    @SuppressWarnings("unchecked")
    public boolean restart(Class id)
    {
        synchronized (id)
        {
            Service service = services.get(id);
            if ( service != null )
            {
                return service.restart(serviceContext);
            }
        }
        
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean start(Class id)
    {
        synchronized (id)
        {
            Service service = services.get(id);
            if ( service != null )
            {
                return service.start(serviceContext);
            }
        }
        
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean stop(Class id)
    {
        synchronized (id)
        {
            Service service = services.get(id);
            if ( service != null )
            {
                return service.stop(serviceContext);
            }
        }
        
        return false;
    }
}