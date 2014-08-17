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


public class Break extends Element
{
	public static final int BREAK_TYPE_LINE = 0;
	public static final int BREAK_TYPE_SECTION = 1;
	public static final int BREAK_TYPE_PAGE = 2;
	
	private int breakType;

    public Break()
    {
        this.type = TYPE_BREAK;
    }
    
    public int getBreakType()
    {
    	return breakType;
    }
    

    public void setValue(String value)
    {
    	String normalizedValue = ( value == null ) ? null : value.toLowerCase();
    	if ( normalizedValue != null )
    	{
    		if ( "sectionbreak".equals(normalizedValue) )
    		{
    			breakType = BREAK_TYPE_SECTION;
    		}
    		else if ( "pagebreak".equals(normalizedValue) )
    		{
    			breakType = BREAK_TYPE_PAGE;
    		}
    	}
    	 	
        super.setValue(normalizedValue);
    }

    
    public String toHTMLString()
    {
    	String style;
    	
    	switch ( breakType )
    	{
    		case BREAK_TYPE_SECTION :
    		{
    			style = "sectionbreak";
    			break;
    		}
    		case BREAK_TYPE_PAGE :
    		{
    			style = "pagebreak";
    			break;
    		}
    		default :
    		{
    			style = "linebreak";
    			break;
    		}
    	}
    		
        StringBuilder text = new StringBuilder("<div id=\"")
        .append(id).append("\" class=\"")
        .append(style).append("\" name=\"").append(name).append("\"");
        
        if ( width > 0 )
        {
            text.append(" width=\"").append(width).append("\"");
        }
        
        if ( height > 0 )
        {
            text.append(" height=\"").append(height).append("\"");
        }
        
        
        text.append("></div>")
        .toString();
        
        return text.toString();
    }

}
