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

public class Image extends Element
{
    public Image()
    {
        this.type = TYPE_IMAGE;
    }
    
    public String toHTMLString()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("<span class=\"image\">");
        if ( showName )
        {
            buffer.append("<span class=\"imagelabel\">").append(name).append("</span>");
        }
        buffer.append("<img onclick=\"imageClicked(this);\" class=\"image\" id=\"")
        .append(id).append("\" alt=\"")
        .append(name).append("\"");
        
        if ( width > 0 ) buffer.append(" width=\"").append(width).append("\"");
        if ( height > 0 ) buffer.append(" height=\"").append(height).append("\"");
        
        buffer.append(" src=\"").append(Strings.ifNull(value,"")).append("\">").append("</span>");
        
        
        return buffer.toString();
    }
}
