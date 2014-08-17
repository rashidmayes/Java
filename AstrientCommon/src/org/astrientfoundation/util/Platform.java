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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

public final class Platform
{
    public static MemoryUsage getHeapMemoryUsage()
    {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    }
    
    public static MemoryUsage getNonHeapMemoryUsage()
    {
        return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
    }
    
    public static long getCurrentThreadCpuTime()
    {
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }
    
    public static long getCurrentThreadUserTime()
    {
        return ManagementFactory.getThreadMXBean().getCurrentThreadUserTime();
    }
    
    public static long getPeakThreadCount()
    {
        return ManagementFactory.getThreadMXBean().getPeakThreadCount();
    }
    
    public static long getStartTime()
    {
        return ManagementFactory.getRuntimeMXBean().getStartTime();
    }
    
    public static long getUptime()
    {
        return ManagementFactory.getRuntimeMXBean().getUptime();
    }
    
    public static int getAvailableProcessors()
    {
        return ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
    }
}
