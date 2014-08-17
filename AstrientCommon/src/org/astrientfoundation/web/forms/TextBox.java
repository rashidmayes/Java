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

public class TextBox extends Element
{
    public TextBox()
    {
        this.type = TYPE_TEXTBOX;
    }
    
    public String toHTMLString()
    {
        StringBuilder buffer = new StringBuilder();
        
        if ( showName )
        {
        	buffer.append("<span class=\"textarealabel\">").append(name).append("</span>\n");
        }
    
    	buffer.append("<span class=\"textarea\"><textarea class=\"textbox\" id=\"")
        .append(id).append("\" name=\"")
        .append(name).append("\" cols=\"")
        .append(width).append("\" rows=\"")
        .append(height).append("\">")
        .append(Strings.ifNull(value,"")).append("</textarea></span>");
        
        
        return buffer.toString();
    }

}
