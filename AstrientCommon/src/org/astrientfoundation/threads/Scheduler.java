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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.astrientfoundation.logging.Log;

public class Scheduler extends TimerTask
{
	private List<AbstractScheduledTask> jobs = Collections.synchronizedList(new ArrayList<AbstractScheduledTask>());
	private TaskRunner jobRunner = null;
	private boolean running = false;
	private long sleepTime = 1000 * 30;
	private long lastExamineTs = 0;
	
	protected Timer timer = null;
	
	public Scheduler()
	{
		this(1000 * 30);
	}

	public Scheduler(long sleepTime)
	{
		this(sleepTime, null,"/crontab");
	}

	public Scheduler(long sleepTime, TaskRunner jobRunner, String cronfile)
	{
	    this.sleepTime = sleepTime;
	    if ( cronfile != null ) loadSchedule(cronfile);
		this.jobRunner = (jobRunner == null) ? new TaskRunner(3) : jobRunner;
	}
	
	public void start()
	{
	    if ( timer == null )
	    {
	        timer = new Timer();
			timer.scheduleAtFixedRate(this,0,sleepTime);
	    }
	}

	public void stop()
	{
		timer.cancel();
		timer = null;
	}

	public void load(AbstractScheduledTask cj)
	{
		jobs.add(cj);
	}

	public void unload(AbstractScheduledTask cj)
	{
		jobs.remove(cj);
	}

	public List<AbstractScheduledTask> getCronJobs()
	{
		return jobs;
	}

	public TaskRunner getJobRunner()
	{
		return jobRunner;
	}

	public int size()
	{
		return jobs.size();
	}

	@SuppressWarnings("unchecked")
    public boolean loadSchedule(String schedFile)
	{
		LineNumberReader br = null;
		try
		{
			InputStream is  = this.getClass().getResourceAsStream(schedFile);
			if ( is == null )
			{
				throw new FileNotFoundException("Could not load schedule from " + schedFile);
			}
			
			InputStreamReader isReader = new InputStreamReader(is);
			
			br = new LineNumberReader(isReader);
			String s = null;
			String[] sa = null;
			AbstractScheduledTask cj = null;
			List<String> tempArgs = new ArrayList<String>();
			String[] args = new String[0];
			
			Class[] ca = { String.class, args.getClass() };
			String name;

			while ((s = br.readLine()) != null)
			{
				if (s.startsWith("#"))
					continue;

				try
				{
					sa = s.split(" ");
					Class<AbstractScheduledTask> clazz = (Class<AbstractScheduledTask>)Class.forName(sa[7]);
					java.lang.reflect.Constructor<AbstractScheduledTask> c = clazz.getConstructor(ca);

					
					if ( sa.length > 8 )
					{
					    name = sa[8];
					    tempArgs.clear();
					    for ( int i = 9; i < sa.length; i++ )
					    {
					        tempArgs.add(sa[i]);
					    }
					    
					    args = tempArgs.toArray(args);
					}
					else
					{
					    name = clazz.getSimpleName();   
					}
					
					cj = c.newInstance(name,args);

					cj.setMinutes(toIntArray(sa[0],60));
					cj.setHours(toIntArray(sa[1],24));
					cj.setDays(toIntArray(sa[2],31));
					cj.setDaysOfWeek(toIntArray(sa[3],7));
					cj.setWeeksOfMonth(toIntArray(sa[4],4));
					cj.setMonths(toIntArray(sa[5],12));
					cj.setYears(toIntArray(sa[6],Integer.MAX_VALUE));
					load(cj);
				}
				catch (Exception e)
				{
				    Log.errors.log(getClass(), "Scheduler: ctab file '" + schedFile + "', line no: " + br.getLineNumber() + ", " + e.toString());
				}
			}

			return true;
		}
		catch (FileNotFoundException fnfe)
		{
		    Log.errors.log(Scheduler.class, fnfe.getMessage());
		}
		catch (Exception e)
		{
		    Log.errors.log(Scheduler.class,e);
		}
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (Exception e)
			{
			}
		}

		return false;
	}

	public static int[] toIntArray(String s, int max)
	{
	    int[] ii = null;
	    if ( s != null )
	    {
	        String[] sa = s.split(",");
	        if ( sa[0].trim().startsWith("@") )
	        {
	            int timeStep = Integer.parseInt(sa[0].trim().substring(1));
	            ii = new int[max/timeStep];
	            for ( int i = 0; i < ii.length; i++)
	            {
	                ii[i] = i * timeStep;
	            }
	        }
	        else if (sa[0].trim().equals("*"))
	        {

	        }
	        else
	        {
	            ii = new int[sa.length];
	            for (int i = 0; i < sa.length; i++)
	            {
	                ii[i] = Integer.parseInt(sa[i]);
	            }
	        }
	    }
	    
	    return ii;
	}

	public void run()
	{
	    if ( running ) return;
	    
	    try
	    {
	        running = true;
	        lastExamineTs = System.currentTimeMillis();
	        
			AbstractScheduledTask cj = null;

			for (int i = 0; i < jobs.size(); i++)
			{
				try
				{
					cj = (AbstractScheduledTask) jobs.get(i);
					process(cj);
				}
				catch (Exception e)
				{
					Log.errors.log(Scheduler.class,e);
				}
			}	        
	    }
	    finally
	    {
	        running = false;
	    }
	}

	private void process(AbstractScheduledTask cj)
	{
		if (cj.timeToExecute())
		{
			cj.setLastScheduledRun(lastExamineTs);
			jobRunner.execute(cj);
		}
	}
}