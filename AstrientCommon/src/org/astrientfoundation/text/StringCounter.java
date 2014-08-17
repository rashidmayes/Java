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
package org.astrientfoundation.text;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.astrientfoundation.util.Strings;

public class StringCounter
{
	protected List<StringRegister> registers = new LinkedList<StringRegister>();
	
	public StringCounter()
	{	
	}
	
	public void add(String string, int max, boolean useSoundex)
	{
		StringRegister register = new StringRegister(string,max);
		register.useSoundex = useSoundex;
		registers.add(register);
	}
	
	public void add(String string, int max)
	{
		StringRegister register = new StringRegister(string,max);
		registers.add(register);
	}
	
	public Rating inspect(String in)
	{
		return inspect(in,false);
	}
	
	public Rating inspect(String in, boolean twoPass)
	{
		BreakIterator bi = BreakIterator.getWordInstance(Locale.US);
		bi.setText(in);
		
		StringRegister stringRegister;
		String word;
		double total = 0;
		
		int start = bi.first();
		for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) 
		{
			word = in.substring(start,end);
			total++;
			
			stringRegister = getRegister(word);
			
			if ( stringRegister != null )
			{
				stringRegister.count++;
			}
		}
		
		double totalHits = 0;
		double totalInstances = 0;
		double over = 0;
		for (StringRegister register : registers )
		{
			if ( twoPass && register.count == 0 )
			{
				if ( in.indexOf(register.string) != -1 )
				{
					System.out.println( register.string + " " + in );
					register.count++;
				}
			}
			
			if ( register.count > 0 )
			{
				totalHits += register.count;
				totalInstances++;
				
				if ( register.count > register.max )
				{
					over++;
				}
			}
		}
		
		Rating rating = new Rating();
		rating.hits = totalInstances/registers.size();
		rating.prevalence = (totalHits/total);
		rating.overage = over/registers.size();
			
		return rating;
	}
	
	public StringRegister getRegister(String word)
	{
		for (StringRegister register : registers )
		{
			if ( register.equals(word) )
			{
				return register;
			}
		}		
		
		return null;
	}
	
	/*
	public static void main(String[] args)
	{
		StringCounter stringCounter = new StringCounter();
		//stringCounter.add("brown", 2);
		stringCounter.add("ci@lis", 1);
		
		Rating rating = stringCounter.inspect("ci@lis THe THe the the The brown fox runs fast. But not fast enough to jump the not so grand canyon. Yeah you know. The one next to the lake. <a href=\"http://www.cnn.com\">hi</a>");
		System.out.println(rating.prevalence + " " + rating.overage + " " + rating.hits);
		
		System.exit(0);
	}*/
	
	public class Rating
	{
		protected double overage;
		protected double prevalence;
		protected double hits;
		
		protected Rating()
		{
			
		}
		
		public double getHits()
		{
			return hits;
		}
		
		public double getOverage()
		{
			return overage;
		}
		
		public double getPrevalence()
		{
			return prevalence;
		}
	}
}
class StringRegister
{
	protected String string;
	protected int max;
	protected int count;
	protected boolean useSoundex = false;
	
	public StringRegister(String string, int max)
	{
		this.string = string.toLowerCase();
		this.max = max;
	}
	
	public boolean equals(Object object)
	{
		String oString = String.valueOf(object).toLowerCase();
		return ( oString.equals(string) || (useSoundex && Strings.getSoundexCode(oString).equals(Strings.getSoundexCode(string))) ); 
	}
	
	public int hashCode()
	{
		return string.hashCode();
	}

	public String toString()
	{
		return string.toString();
	}
}