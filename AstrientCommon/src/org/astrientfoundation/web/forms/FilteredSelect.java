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

import java.util.Collection;

import org.astrientfoundation.util.Strings;

public class FilteredSelect extends Element
{
    public FilteredSelect()
    {
        this.type = TYPE_FILTERED_SELECT;
    }
    
    public String toHTMLString()
    {
        StringBuilder buffer = new StringBuilder("<span class=\"select\">");
        
        if ( showName )
        {
        	buffer.append("<span class=\"selectlabel\">").append(name).append("</span>\n");
        }
        
        buffer.append("<select class=\"select\" id=\"")
            .append(id).append("\" name=\"")
            .append(name).append("\" size=\"").append(height).append("\">\n");
        
        Collection<String> data = model;
        if ( !Strings.isNull(doppelganger) )
        {
            Element element = this.form.getElement(doppelganger);
            if ( element != null )
            {
                if ( element instanceof SelectFilter && element.value != null )
                {
                    Collection<String> tempData = ((SelectFilter)element).datasets.get(element.value);
                    if ( tempData != null )
                    {
                        data = tempData;
                    }
                }
            }
        }
        
        for ( String value : data )
        {
            buffer.append("<option value=\"")
            .append(Strings.ifNull(value,""));
            
            
            if ( value.equals(this.value) )
            {
                buffer.append("\" selected/>");
            }
            else
            {
                buffer.append("\"/>");
            }
            
            buffer.append(value).append("</option>\n");
        }
        
        return buffer.append("</select></span>").toString();
    }

}
