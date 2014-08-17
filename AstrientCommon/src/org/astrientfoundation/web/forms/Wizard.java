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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Wizard
{ 
    protected List<Form> forms = new LinkedList<Form>();
    protected List<Form> safeList = Collections.unmodifiableList(forms);
    protected String startText;
    
    public Form getForm(String id)
    {
        for ( Form form : forms )
        {
            if ( form.getId().equals(id) )
            {
                return form;
            }
        }
        
        return null;
    }
    
    public int size()
    {
        return forms.size();
    }
    
    public List<Form> getForms()
    {
        return safeList;
    }
    
    public Iterator<Form> elements()
    {
        return forms.iterator();
    }
    
    public void addForm(Form form)
    {
        forms.add(form);
    }
    
    public void clear()
    {
        forms.clear();
    }
    
    public Form removeForm(int index)
    {
        return forms.remove(index);
    }
    
    public Form getForm(int index)
    {
        return forms.get(index);
    }

    public Wizard blankWizard() throws CloneNotSupportedException
    {
        return (Wizard)clone();
    }
    
    protected Object clone() throws CloneNotSupportedException
    {
        Wizard clone = new Wizard();
        clone.startText = this.startText;
                
        for ( Iterator<Form> iter = elements(); iter.hasNext(); )
        {
            clone.addForm((Form) iter.next().clone());
        }
        
        return clone;
    }
    
    public String getStartText()
    {
        return startText;
    }
}
