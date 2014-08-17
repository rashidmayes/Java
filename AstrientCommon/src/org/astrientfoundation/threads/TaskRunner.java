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

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class TaskRunner
{
    public static ThreadGroup threadGroup = new ThreadGroup("DefferedProcessor");
    
    private final BlockingQueue<Task>  queue;
    private LinkedList<Worker> workers = new LinkedList<Worker>();
    private int initialWorkers = 0;
    
    public static final int DEFUALT_WORKER_COUNT = 3; 

    public TaskRunner()
    {
        this(DEFUALT_WORKER_COUNT);
    }


    public TaskRunner(int numworkers)
    {
        this(numworkers,new LinkedBlockingQueue<Task>());
    }


	public TaskRunner(int numworkers, BlockingQueue<Task> queue)
	{	
		this.queue = queue;
		this.initialWorkers = numworkers;
		start();
	}
	
	public void start()
	{
		for ( int i = 0; i < initialWorkers; i++ )
		{
			increase();
		}
	}


    public void decrease()
    {
        if ( workers.size() > 1 )
        {
            Worker w = workers.remove(0);
            w.shutdown();
        }
    }


    public void increase()
    {
        workers.add(new Worker(threadGroup,"worker."+threadGroup.activeCount(),queue));
    }


    public Task[] jobs()
    {
        return queue.toArray(new Task[0]);
    }
    
    
	public Worker[] workers()
	{
		return workers.toArray(new Worker[0]);
	}    


    public boolean execute(Task j)
    {
         if ( j != null )
         {
        	 try
			{
				queue.put(j);
				return true;
			}
			catch (InterruptedException e)
			{
			}
         }
        	 
         return false;
    }


    public static int activeCount()
    {
        return threadGroup.activeCount();
    }


	public int availableJobs()
	{
		return queue.size();
	}
	
	
    public void removeAllJobs()
    {
        queue.clear();
    }
    
    public void stop()
    {
        synchronized (workers)
        {
            removeAllJobs();
            for (Worker worker : workers )
            {
                worker.shutdown();
            } 
        }
    }
    
    
    public void list(PrintWriter out)
    {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        
        StringBuilder buffer = new StringBuilder();
        int total = threadGroup.enumerate(threads,false);
        for ( int i = 0; i < total; i++ )
        {
        	buffer.append(i)
        	.append(" [name: ")
        	.append(threads[i].getName())
        	.append(", priority: ")
        	.append(threads[i].getPriority())
        	.append(", state: ")
        	.append(threadBean.getThreadInfo(threads[i].getId()).getThreadState().name())
        	.append(", cputime: ")
        	.append(threadBean.getThreadCpuTime(threads[i].getId()))
        	.append("]");
        	
        	out.println(buffer.toString());
        	buffer.setLength(0);
        }
        
        out.flush();
    }
}
