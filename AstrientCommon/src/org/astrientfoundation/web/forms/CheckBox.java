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

import org.astrientfoundation.util.Strings;

public class CheckBox extends Element
{
    public CheckBox()
    {
        this.type = TYPE_CHECKBOX;
    }
    
    public void setValue(String value)
    {
        if ( value == null || model.contains(value) )
        {
            super.setValue(value);
        }
    }
    
    public String toHTMLString()
    {
        String tempValue = (model.isEmpty()) ? value : model.iterator().next().toString();
        StringBuilder buffer = new StringBuilder("<span class=\"checkbox\"><input type=\"checkbox\" class=\"checkbox\" id=\"")
        .append(id).append("\" name=\"")
        .append(name).append("\" value=\"")
        .append(tempValue);
        
        if ( !Strings.isNull(value) )
        {
            buffer.append("\" selected/>");
        }
        else
        {
            buffer.append("\"/>");
        }
        
        return buffer.append(name).append("</span>").toString();
    }

}