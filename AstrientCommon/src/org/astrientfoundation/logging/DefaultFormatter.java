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
package org.astrientfoundation.logging;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.astrientfoundation.util.Dates;

public class DefaultFormatter extends Formatter
{
    private static final int REC_SIZE = 1024;
    
    public String format(LogRecord rec) 
    {
        StringBuilder buf = new StringBuilder(REC_SIZE);
        buf.append(Dates.format(new Date(),"MM/dd/yyyy HH:mm:ss"));
        buf.append(' ');
        buf.append(nameForLevel(rec.getLevel()));
        buf.append(' ');
        buf.append(formatMessage(rec));
        buf.append('\n');
        
        return buf.toString();
    }
    
    private String nameForLevel(Level level)
    {
        if ( level == Level.ALL )
        {
            return "ALL";
        }
        else if ( level == Level.CONFIG )
        {
            return "CFG";
        }
        else if ( level == Level.FINE )
        {
            return "FNE";
        }
        else if ( level == Level.FINER )
        {
            return "FNR";
        }
        else if ( level == Level.FINEST )
        {
            return "FNT";
        }
        else if ( level == Level.INFO )
        {
            return "INF";
        }
        else if ( level == Level.OFF )
        {
            return "OFF";
        }
        else if ( level == Level.SEVERE )
        {
            return "SEV";
        }
        else if ( level == Level.WARNING )
        {
            return "WRN";
        }
        else
        {
            return "UNK";
        }
    }
}
