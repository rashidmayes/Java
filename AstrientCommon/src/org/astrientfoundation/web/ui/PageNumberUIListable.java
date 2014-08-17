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



public class PageNumberUIListable<E> extends BasicUIListable<E>
{
    private static final long serialVersionUID = 1L;
    
    public PageNumberUIListable()
    {
        super();
    }

    public PageNumberUIListable(Collection<? extends E> c)
    {
        super(c);
    }

    public PageNumberUIListable(int pageSize)
    {
        super(pageSize);
    }
    
    public int[] numbering(int pageSize, int current, int deviation, int size)
    {
        return new PageNumbers(this.totalPages(pageSize),current,size,deviation).numbers();
    }

    public int[] numbering(int current, int deviation, int size)
    {
        return new PageNumbers(this.totalPages(),current,size,deviation).numbers();
    }
    
    public int[] numbering(int current, int size)
    {
        return new PageNumbers(this.totalPages(),current,size,2).numbers();
    }
    
    public int[] numbering(int current)
    {
        return new PageNumbers(this.totalPages(),current,10,2).numbers();
    }
}
