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
package org.astrientfoundation.threads;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.peers.Callback;


public abstract class Task
{
    private List<Callback<Task,Task>> callbacks = new ArrayList<Callback<Task,Task>>();
    private String jobId = String.valueOf(new UID());
    private long lastRun;

    public void execute()
    {
        try
        {
            notifyStarted();
            _execute();
        }
        catch (Exception e)
        {
        	Log.errors.log(getClass(),e);
        	notifyFailed();
        }
        finally
        {
            lastRun = System.currentTimeMillis();
        	notifyCompleted();
        }
    }

    public abstract void _execute();

    
    public void notifyStarted()
    {
        try
        {
            for ( Callback<Task,Task> callback : callbacks )
            {
                callback.started(this,this);
            }
        }
        catch (Exception e)
        {
            Log.errors.log(getClass(),e);
        }
    }
    
    public void notifyFailed()
    {
        try
        {
            for ( Callback<Task,Task> callback : callbacks )
            {
                callback.failed(this,this);
            }
        }
        catch (Exception e)
        {
            Log.errors.log(getClass(),e);
        }
    }
    
    public void notifyCompleted()
    {
        try
        {
            for ( Callback<Task,Task> callback : callbacks )
            {
                callback.completed(this,this);
            }
        }
        catch (Exception e)
        {
            Log.errors.log(getClass(),e);
        }
    }


    public void removeListener(Callback<Task,Task>  callback)
    {
        callbacks.remove(callback);
    }

    public void addListener(Callback<Task,Task>  callback)
    {
        callbacks.add(callback);
    }
    
    /**
     * @return Returns the jobId.
     */
    public String getJobId()
    {
        return jobId;
    }
    
    /**
     * @param jobId The jobId to set.
     */
    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }

    public long getLastRun()
    {
        return lastRun;
    }

    public void setLastRun(long lastRun)
    {
        this.lastRun = lastRun;
    }
}
