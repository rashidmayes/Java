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

import org.astrientfoundation.threads.Scheduler;
import org.astrientfoundation.threads.TaskRunner;

public final class Cron extends Scheduler implements Service
{ 
    public Cron()
    {
        super(1000 * 30, new TaskRunner(3), "/crontab");
    }
    
    public String getDescription()
    {
        return "cron daemon " + toString();
    }

    public String getName()
    {
        return "cron";
    }

    public boolean isAlive()
    {
        return (timer != null);
    }

    public boolean restart(ServiceContext context)
    {
        stop();
        start();
        return true;
    }

    public boolean start(ServiceContext context)
    {
        start();
        return true;
    }

    public boolean stop(ServiceContext context)
    {
        stop();
        return true;
    }

    public Scheduler getComponent()
    {
        return this;
    }
}
