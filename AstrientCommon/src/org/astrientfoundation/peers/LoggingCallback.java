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
package org.astrientfoundation.peers;

import java.util.logging.Level;

import org.astrientfoundation.logging.Log;

public class LoggingCallback<S,W> implements Callback<S,W>
{
    public void cancelled(S source, W what)
    {
        Log.info.logp(Level.WARNING, source.getClass().getName(), "cancelled()", String.valueOf(what)); 
    }

    public void error(S source, W what)
    {
        Log.info.logp(Level.WARNING, source.getClass().getName(), "error()", String.valueOf(what)); 
    }

    public void failed(S source, W what)
    {
        Log.info.logp(Level.SEVERE, source.getClass().getName(), "failed()", String.valueOf(what)); 
    }

    public void completed(S source, W what)
    {
        Log.info.exiting(source.getClass().getName(), String.valueOf(what));     
    }

    public void initialized(S source, W what)
    {
        Log.info.logp(Level.INFO, source.getClass().getName(), "initialized()", String.valueOf(what));      
    }

    public void started(S source, W what)
    {
        Log.info.entering(source.getClass().getName(), String.valueOf(what));
    }

    public void update(S source, W event)
    {
        Log.info.logp(Level.INFO, source.getClass().getName(), "update()", String.valueOf(event));
    }
}
