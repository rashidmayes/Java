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

import java.util.concurrent.BlockingQueue;

import org.astrientfoundation.logging.Log;

public class Worker implements Runnable
{
	private final BlockingQueue<Task> queue;
    private Thread thread = null;

    public Worker(ThreadGroup threadGroup, String name, BlockingQueue<Task> queue)
    {
    	this.queue = queue;
    	this.thread = new Thread(threadGroup,this,name);
    	thread.start();
    }

    public void shutdown()
    {
        thread = null;
    }


    public void run()
    {
        Task job = null;
        while ( thread != null )
        {
            try
            {
                job = queue.take();
                job.execute();
            }
            catch (Exception e)
            {
                Log.errors.log(Worker.class,e);
            }
        }
    }
    
	/**
	 * Returns the queue.
	 * @return JobQueue
	 */
	protected BlockingQueue<Task> getQueue()
	{
		return queue;
	}
	
	public long getThreadId()
	{
	    return thread.getId();
	}
	
	public ThreadGroup getThreadGroup()
	{
	    return thread.getThreadGroup();
	}
}