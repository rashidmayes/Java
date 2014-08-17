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

import java.util.Calendar;

public abstract class AbstractScheduledTask extends Task
{
	protected int[] minutes = null;
	protected int[] hours = null;
	protected int[] days = null;
	protected int[] daysOfWeek = null;
	protected int[] weeksOfMonth = null;
	protected int[] months = null;
	protected int[] years = null;

	protected boolean paused = false;
	protected long lastScheduledRun = 0;
	
	private String name;
	String[] args;

	public AbstractScheduledTask(String name, String[] args)
	{
	    this.name = name;
	    this.args = args;
	}
	
	public long getLastScheduledRun()
	{
		return this.lastScheduledRun;
	}

	public void setLastScheduledRun(long lastScheduledRun)
	{
		this.lastScheduledRun = lastScheduledRun;
	}

	public void setSchedule(int[] minutes, int[] hours, int[] days, int[] daysOfWeek, int[] weeksOfMonth, int[] months, int[] years)
	{
		setMinutes(minutes);
		setHours(hours);
		setDays(days);
		setMonths(months);
		setYears(years);
		setWeeksOfMonth(weeksOfMonth);
		setDaysOfWeek(daysOfWeek);
	}

	public void setPaused(boolean paused)
	{
		this.paused = paused;
	}

	public boolean paused()
	{
		return paused;
	}

	public boolean timeToExecute()
	{
		if (paused || ((this.lastScheduledRun / (60*1000)) == (System.currentTimeMillis()/(60*1000))))
		{
			return false;
		}

		Calendar c = Calendar.getInstance();

		if (isInArray(c.get(Calendar.YEAR), years)
			&& isInArray(c.get(Calendar.MONTH), months)
			&& isInArray(c.get(Calendar.WEEK_OF_MONTH), weeksOfMonth)
			&& isInArray(c.get(Calendar.DAY_OF_WEEK), daysOfWeek)
			&& isInArray(c.get(Calendar.DAY_OF_MONTH), days)
			&& isInArray(c.get(Calendar.HOUR_OF_DAY), hours)
			&& isInArray(c.get(Calendar.MINUTE), minutes))
		{
			return true;
		}

		return false;
	}

	protected boolean isInArray(int i, int[] ii)
	{
		if (ii == null)
		{
			return true;
		}

		for (int j = 0; j < ii.length; j++)
		{
			if (i == ii[j])
			{
				return true;
			}
		}

		return false;
	}


	/**
	 * @return
	 */
	public int[] getDays()
	{
		return days;
	}

	/**
	 * @return
	 */
	public int[] getDaysOfWeek()
	{
		return daysOfWeek;
	}

	/**
	 * @return
	 */
	public int[] getHours()
	{
		return hours;
	}

	/**
	 * @return
	 */
	public int[] getMinutes()
	{
		return minutes;
	}

	/**
	 * @return
	 */
	public int[] getMonths()
	{
		return months;
	}

	/**
	 * @return
	 */
	public int[] getWeeksOfMonth()
	{
		return weeksOfMonth;
	}

	/**
	 * @return
	 */
	public int[] getYears()
	{
		return years;
	}

	/**
	 * @param is
	 */
	public void setDays(int[] is)
	{
		days = is;
	}

	/**
	 * @param is
	 */
	public void setDaysOfWeek(int[] is)
	{
		daysOfWeek = is;
	}

	/**
	 * @param is
	 */
	public void setHours(int[] is)
	{
		hours = is;
	}

	/**
	 * @param is
	 */
	public void setMinutes(int[] is)
	{
		minutes = is;
	}

	/**
	 * @param is
	 */
	public void setMonths(int[] is)
	{
		months = is;
	}

	/**
	 * @param is
	 */
	public void setWeeksOfMonth(int[] is)
	{
		weeksOfMonth = is;
	}

	/**
	 * @param is
	 */
	public void setYears(int[] is)
	{
		years = is;
	}

	
    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return Returns the paused.
     */
    public boolean isPaused()
    {
        return paused;
    }
    
    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
}