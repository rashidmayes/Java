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
package org.astrientfoundation.web.ui;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.astrientfoundation.logging.Log;


public class BasicUIListable<E> extends LinkedList<E> implements UIListable<E>
{
    private static final long serialVersionUID = -7369036906003073472L;
    private int pageSize = 10;
	private int pageNumber = -1;
    private Random random = new Random();    
    
	public BasicUIListable()
    {
        super();
    }

    public BasicUIListable(Collection<? extends E> c)
    {
        super(c);
    }
    
    public BasicUIListable(int pageSize)
    {
        super();
        this.pageSize = pageSize;
    }

    public List<E> getItems(int pageNumber)
	{
		int start = pageNumber * pageSize;
		int end = start + pageSize;
		return getItems(start,end);
	}
	
	public List<E> items(int pageNumber, int pageSize)
	{
		int start = (pageNumber * pageSize);
		int end = start + pageSize;
		
		return getItems(start,end);
	}
	
    
    public List<E> getItems(int start, int end)
    {
        try
        {
            if ( start > -1 && start < size() )
            {
                if ( end > size() ) 
                {
                    end = size();
                }

                return this.subList(start,end);
            }
        }
        catch (Exception e)
        {
            Log.errors.log(getClass(),e);
        } 
        
        return this.subList(0,0); 
    }

	
	public void resetPageNumber()
	{
		pageNumber = -1;
	}
		
	public List<E> next()
	{
		return getItems(++pageNumber);
	}
	
	
	public List<E> current()
	{
		return ( pageNumber == -1 ) ? next() : getItems(pageNumber);
	}
	
	
	public List<E> previous()
	{
		return getItems(--pageNumber);
	}
	
	/**
	 * Returns the pageNumber.
	 * @return int
	 */
	public int getPageNumber()
	{
		return pageNumber;
	}

	/**
	 * Returns the pageSize.
	 * @return int
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * Sets the pageNumber.
	 * @param pageNumber The pageNumber to set
	 */
	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	/**
	 * Sets the pageSize.
	 * @param pageSize The pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}


	public int totalPages()
	{
		int size = size();
		int pageSize = getPageSize();
		
		int total = size / pageSize;
		if ( (size % pageSize ) > 0 ) 
		{
			total++;
		}
		
		return total;
	}
	
	public int totalPages(int pageSize)
	{
		int size = size();

		int total = size / pageSize;
		if ( (size % pageSize ) > 0 ) 
		{
			total++;
		}
		
		return total;
	}
    	
	public E random()
	{
		return this.get(random.nextInt(this.size()));
	}
}
