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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.astrientfoundation.util.Strings;

public class SelectFilter extends Element
{
    protected Map<String,List<String>> datasets = new LinkedHashMap<String,List<String>>();
    
    public SelectFilter()
    {
        this.type = TYPE_SELECT_FILTER;
    }
    
    public void init(Form form)
    {
        super.init(form);
        
        datasets.clear();
        String[] parts;
        List<String> dataset;
        for ( String s : model )
        {
            parts = s.split("::");
            dataset = datasets.get(parts[0]);
            if ( dataset == null )
            {
                dataset = new ArrayList<String>();
                datasets.put(parts[0], dataset);
            }
            
            if ( !dataset.contains(parts[1]) )
            {
                dataset.add(parts[1]);
            }
        }
        
        model.clear();
        for ( String s : datasets.keySet() )
        {
            model.add(s);
        }
    }
    
    public String toHTMLString()
    {
        StringBuilder buffer = new StringBuilder("<span class=\"select\">");
        
        if ( showName )
        {
        	buffer.append("<span class=\"selectlabel\">").append(name).append("</span>\n");
        }
        
        buffer.append("<script language=\"javascript\">\n")
        .append("filterData.datasets['").append(id).append("'] = [\n");
        
        List<String> values;
        boolean firstSet = true;
        for ( String s : datasets.keySet() )
        {
            values = datasets.get(s);
            if ( firstSet )
            {
                firstSet = false;
                buffer.append("\t [ ");
            }
            else
            {
                buffer.append("\t,[ ");    
            }
            
            
            boolean firstVal = true;
            for ( String val : values )
            {
                if ( firstVal )
                {
                    firstVal = false;
                    buffer.append(" \"").append(val).append("\"");
                }
                else
                {
                    buffer.append(" ,\"").append(val).append("\"");
                }
            }
            
            buffer.append("] ");
        }
        
        buffer.append("];\n</script>");
        
        buffer.append("<select onchange=\"filterData(this,'").append(doppelganger).append("');\" class=\"select\" id=\"")
            .append(id).append("\" name=\"")
            .append(name).append("\" size=\"").append(height).append("\">\n");
        
        
        for ( String value : model )
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
    
    
    @SuppressWarnings("unchecked")
    protected SelectFilter clone(Form form) throws CloneNotSupportedException
    {
        SelectFilter clone = new SelectFilter();
        clone.height = this.height;
        clone.width = this.width;
        clone.model = this.model;
        clone.name = this.name;
        clone.setValue(this.value);
        clone.id = this.id;
        clone.required = this.required;
        clone.showName = this.showName;
        clone.validationPattern = this.validationPattern;
        clone.cost = this.cost;
        clone.selected = this.selected;
        clone.doppelganger = this.doppelganger;
        clone.form = form;
        clone.datasets = this.datasets;
        
        return clone;
    }

}
