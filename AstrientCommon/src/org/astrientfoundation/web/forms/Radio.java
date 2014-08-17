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
package org.astrientfoundation.web.forms;

import java.util.Iterator;

import org.astrientfoundation.util.Strings;

public class Radio extends Element
{
    public Radio()
    {
        this.type = TYPE_RADIO;
    }
    
    public String toHTMLString()
    {
        StringBuilder buffer = new StringBuilder();
        
        if ( showName )
        {
        	buffer.append("<span class=\"radio\"><span class=\"radiolabel\">").append(name).append("</span>");
        }
        
        
        String value;
        for ( Iterator<String> iter = model.iterator(); iter.hasNext(); )
        {
            value = iter.next();
            buffer.append("<span class=\"radiooption\"><input type=\"radio\" class=\"radio\" id=\"")
            .append(id).append("\" name=\"")
            .append(name).append("\" value=\"")
            .append(Strings.ifNull(value,""));
            
            
            if ( value.equals(this.value) )
            {
                buffer.append("\" checked/>");
            }
            else
            {
                buffer.append("\"/>");
            }
            
            buffer.append("<label>").append(value).append("</label></span>\n");
        }
        
        return buffer.append("</span>").toString();
    }

}
