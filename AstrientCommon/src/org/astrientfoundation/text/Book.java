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
import java.util.ArrayList;
import java.util.Locale;

public final class Book
{
	private int pageSize;
	private int offset;
	private int currentPage = 0;
	
	private BreakIterator breakIterator    = null;
	private ArrayList<String>     pages = null;
	
	public Book(int pageSize)
	{
		this(pageSize, 5);
	}
	
	public Book(int pageSize, int offset)
	{
		this(pageSize,offset,Locale.getDefault());
	}
		
	public Book(int pageSize, Locale locale)
	{
		this(pageSize,5,locale);
	}
	
	public Book(int pageSize, int offset, Locale locale)
	{
		this(pageSize, locale, offset, null);
	}
	
	
	public Book(int pageSize, Locale locale, String text)
	{
		this(pageSize,locale,5,text);
	}
	
	
	public Book(int pageSize, Locale locale, int offset, String text)
	{
		this.pageSize = pageSize;
		this.offset = offset;
		
		breakIterator = BreakIterator.getWordInstance(locale);
		pages = new ArrayList<String>(5);
		pages.add("");
		if ( text != null ) addText(text);
	}
	
	
	public void addText(String text)
	{
		int pageIndex = pages.size() - 1;
		String lastPageText = pages.get(pageIndex) + text;


		if (lastPageText.length() > (offset + pageSize))
		{
			breakIterator.setText(lastPageText);
			int end = pageSize;

			if (!breakIterator.isBoundary(end))
			{
				int prev = breakIterator.preceding(pageSize - 1);
				int next = breakIterator.following(pageSize - 1);


				if ((pageSize - 1) - prev < offset && prev > 0)
				{
					end = prev;
				}
				else if (next - (pageSize - 1) <= offset)
				{
					end = next;
				}
			}

			pages.set(pageIndex, lastPageText.substring(0, end));
			pages.add("");
			addText(lastPageText.substring(end));
		}
		else
		{
			pages.set(pageIndex, lastPageText);
		}
	}

	public String getPage(int pgNum)
	{
		return ((pgNum - 1) < pages.size() && (pgNum - 1) > -1) ? pages.get(pgNum - 1) : null;
	}

	public String lastPage()
	{
		return getPage(length());
	}

	public String firstPage()
	{
		return getPage(1);
	}
	
	
	public void next()
	{
		if ( currentPage < length() ) ++currentPage;
	}

	public String getPage()
	{
		return getPage( currentPage );
	}

	public void previous()
	{
		if ( currentPage > 1 ) --currentPage;
	}	
	
	
	public String nextPage()
	{
		return getPage( ++currentPage );
	}
	
	public String currentPage()
	{
		return getPage( currentPage );
	}
	
	public String previousPage()
	{
		return getPage( --currentPage );
	}
	

	public int length()
	{
		return pages.size();
	}


	public int getOffset()
	{
		return offset;
	}

	
	public void setOffset(int offset)
	{
		this.offset = offset;
	}
	
	
	public int pageNumber()
	{
		return currentPage;
	}
	
	public String getText()
	{
		StringBuffer buffer = new StringBuffer();
		
		for ( int i = 0; i < pages.size(); i++ )
		{
			buffer.append(pages.get(i));
		}
		
		return buffer.toString();
	}
	
	public void clear()
	{
		pages.clear();
		pages.add("");
		currentPage = 0;
	}
}
