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
package org.astrientfoundation.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Log extends Logger
{
    public static final Log info = new Log("astrientfoundation.info");
    public static final Log errors = new Log("astrientfoundation.errors");
    public static final Log UI = new Log("astrientfoundation.ui");
    public static final Log fileSystem = new Log("astrientfoundation.filesystem");
    public static final Log db = new Log("astrientfoundation.db");
    public static final Log network = new Log("astrientfoundation.network");
    public static final Log messaging = new Log("astrientfoundation.messaging");
    public static final Log services = new Log("astrientfoundation.services");
    
    public Log(String name)
    {
        super(name, null);
        LogManager.getLogManager().addLogger(this);
    }
    
    protected Log(String name, String resourceBundleName)
    {
        super(name, resourceBundleName);
    }

    @SuppressWarnings("unchecked")
    public void log(Class clazz, Throwable t)
    {
        log(Level.SEVERE,clazz.getName(),t);
    }
     
    @SuppressWarnings("unchecked")
    public void log(Class clazz, String msg)
    {
        logp(Level.INFO,clazz.getName(),"unknown",msg);
    }
    
    @SuppressWarnings("unchecked")
    public void debug(Class clazz, String msg)
    {
        logp(Level.FINEST,clazz.getName(),"unknown",msg);
    }
    
    public static void disableConsole()
    {
        Logger rootLogger = Logger.getLogger("");
        for ( Handler handler : rootLogger.getHandlers() )
        {
            if (handler instanceof ConsoleHandler) {
                rootLogger.removeHandler(handler);
            }
        } 
    }
}
