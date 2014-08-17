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

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class FormParser extends DefaultHandler 
{
    private StringBuffer buffer = new StringBuffer();
    
    private Form form;
    private Element element;
    private PageAction action;
    private boolean isForm;
    
    
    public Form parse(InputStream is) throws SAXException, IOException
    {
        return parse(new InputSource(is));
    }
    
    public Form parse(InputSource inputSource) throws SAXException, IOException
    {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        parser.setContentHandler(this);
        parser.parse(inputSource);

        return form;
    }


    public void startElement(String uri, String name, String qualifiedName, Attributes attributes)
    {
        if ( name.equals("form") )
        {
            isForm = true;
            form = new Form();
        }
        else if ( name.equals("elements") )
        {
            isForm = false;
        }
        else if ( name.equals("element") )
        {
            String type = attributes.getValue("type");
            if ( type.equals("textfield") )
            {
                element = new TextField();
            }
            else if ( type.equals("password") )
            {
                element = new Password();
            }
            else if ( type.equals("select") )
            {
                element = new Select();
            }
            else if ( type.equals("prompt") )
            {
                element = new Prompt();
            }
            else if ( type.equals("title") )
            {
                element = new Title();
            }
            else if ( type.equals("hidden") )
            {
                element = new Hidden();
            }
            else if ( type.equals("checkbox") )
            {
                element = new CheckBox();
            }
            else if ( type.equals("radio") )
            {
                element = new Radio();
            }
            else if ( type.equals("textbox") )
            {
            	element = new TextBox();
            }
            else if ( type.equals("break") )
            {
            	element = new Break();
            }
            else if ( type.equals("label") )
            {
            	element = new Label();
            }
            else if ( type.equals("link") )
            {
                element = new Link();
            }
            else if ( type.equals("image") )
            {
                element = new Image();
            }
            else if ( type.equals("selectfilter") )
            {
                element = new SelectFilter();
            }
            else if ( type.equals("filteredselect") )
            {
                element = new FilteredSelect();
            }
        }
        else if ( name.equals("label") )
        {
            String type = attributes.getValue("type");
            if ( "control".equalsIgnoreCase(type) )
            {
                String labelValue = attributes.getValue("value");
                if ( labelValue != null )
                {
                    String labelName = attributes.getValue("name");
                    if ( labelName != null )
                    {
                        form.controlLabels.put(labelName,labelValue);    
                    }
                }
            }
        }
        else if ( "action".equalsIgnoreCase(name) )
        {
            action = new PageAction();
            action.id = attributes.getValue("id");
            action.name = attributes.getValue("name");
            action.type = attributes.getValue("type");
            action.value = attributes.getValue("value");
        }
        
        buffer.setLength(0);
    }

    public void characters(char[] ch, int start, int length) throws SAXException
    {
        buffer.append(new String(ch,start,length));
    }
    
    
    public void endElement (String uri, String localName, String qName) throws SAXException
    {
        if ( "action".equalsIgnoreCase(localName) )
        {
            if ( action != null )
            {
                if ( action.id != null )
                {
                    action.message = buffer.toString().trim();
                    form.actions.put(action.id.toLowerCase(),action);    
                }
                action = null;
            }
        }
        else if ( isForm )
        {
            if ( localName.equals("name") )
            {
                form.name = buffer.toString();
            }
            else if ( localName.equals("id") )
            {
                form.id = buffer.toString();
            }
            else if ( localName.equals("description") )
            {
                form.description = buffer.toString();
            }
        }
        else if ( element != null )
        {
            if ( localName.equals("name") )
            {
                element.name = buffer.toString();
            }
            else if ( localName.equals("id") )
            {
                element.id = buffer.toString();
            }
            else if ( localName.equals("height") )
            {
                element.height = Integer.parseInt(buffer.toString());
            }
            else if ( localName.equals("width") )
            {
                element.width = Integer.parseInt(buffer.toString());
            }
            else if ( localName.equals("default") )
            {
                element.setValue(buffer.toString());
            }
            else if ( localName.equals("value") )
            {
                element.model.add(buffer.toString());
            }
            else if ( localName.equals("required") )
            {
                element.required = Boolean.valueOf(buffer.toString().trim());
            }
            else if ( localName.equals("showname") )
            {
                element.showName = Boolean.valueOf(buffer.toString().trim());
            }
            else if ( localName.equals("element") )
            {
                element.init(form);
                form.elements.add(element);
                element = null;
            }
            else if ( localName.equals("validationpattern") )
            {
                element.validationPattern = buffer.toString().trim();
            }
            else if ( localName.equals("cost") )
            {
                element.cost = Float.valueOf(buffer.toString().trim());
            }
            else if ( localName.equals("selected") )
            {
                element.selected = Boolean.valueOf(buffer.toString().trim());
            }
            else if ( localName.equals("doppelganger") )
            {
                element.doppelganger = buffer.toString().trim();
            }
        }

        
        buffer.setLength(0);
    }
    
    /*
    public static void main(String[] args)
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream(new File("c:/projects/workspace/clique/doc/dirkwizard.xml"));//FormParser.class.getResourceAsStream(new File("c:/projects/workspace/clique/doc/dirkwizard.xml").getCanonicalPath());
            Form form = new FormParser().parse(new InputSource(is));
            
            System.out.println(form.name);
            
            Form f2 = (Form) form.clone();
            
            System.out.println(f2.name);
            
            Element element;
            for ( Iterator iter = f2.elements(); iter.hasNext(); )
            {
                element = (Element)iter.next();
                //System.out.println(element.isRequired());    
                System.out.println(element.cost);   
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try { if ( is != null ) is.close(); } catch (Exception ignored) {}
        }
        
        
        System.exit(0);
    }*/
}
