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

import org.astrientfoundation.logging.Log;

public class Metric extends Timer
{
    private String what;
    private Object who;
    
    public Metric(Object who, String what)
    {
        this.who = who;
        this.what = what;
    }
        
    public long elapsed()
    {
        long t = super.elapsed();
        Log.info.log(who.getClass(),what + ": " + t);
        return t;
    }
}
