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
package org.astrientfoundation.util;

public class Timer
{
    private long startTime;
    private boolean end = false; 
    private long endt;

    public Timer()
    {
        reset();
    }

    public long elapsed()
    {
        return ( end ) ? endt : System.currentTimeMillis() - startTime;
    }
    
    
    public void reset()
    {
        startTime = System.currentTimeMillis();
        end = false;
    }
    
    
    public void end()
    {
    	endt = System.currentTimeMillis() - startTime;
    	end = true;
    }
}