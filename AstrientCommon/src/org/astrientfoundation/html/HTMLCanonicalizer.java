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
package org.astrientfoundation.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.DTD;
import javax.swing.text.html.parser.DTDConstants;
import javax.swing.text.html.parser.ParserDelegator;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.Preferences;
import org.astrientfoundation.util.Strings;

public class HTMLCanonicalizer extends HTMLEditorKit.ParserCallback
{
    private static final HashMap<String,HashMap<HTML.Tag,List<HTML.Attribute>>> RULES_CACHE = new HashMap<String,HashMap<HTML.Tag,List<HTML.Attribute>>>();
    
    protected HTML.Tag currentTag;
    protected StringBuffer buffer = new StringBuffer();
    protected Pattern pattern = Pattern.compile("http[s]?://[\\w\\.\\-#~%/\\?=\\&&&[^,\\s]]*|\\bwww\\.[\\w\\.\\-#~%/\\?=\\&&&[^,\\s]]*",Pattern.CANON_EQ|Pattern.CASE_INSENSITIVE);
    protected boolean consumeTags = true;
    protected boolean consumeText = false;
       
    protected HashMap<HTML.Tag,List<HTML.Attribute>> tags;
    protected List<HTML.Tag> excludes = new ArrayList<HTML.Tag>(0);
    
    protected boolean eat = false;

    
    public HTMLCanonicalizer(String rulesFile)
    {
        tags = RULES_CACHE.get(rulesFile);
        if ( tags == null )
        {
            tags = new HashMap<HTML.Tag,List<HTML.Attribute>>();
            Preferences preferences = new Preferences(rulesFile);
            String name;
            HTML.Tag tag;
            String[] attributes;
            List<HTML.Attribute> list;
            //List<String> list;
            
            for (Enumeration<Object> keys = preferences.keys(); keys.hasMoreElements(); )
            {
                name = keys.nextElement().toString();
                try
                {
                	try
                	{
                		tag = (HTML.Tag)HTML.Tag.class.getField(name).get(HTML.Tag.A);
                	}
                    catch (NoSuchFieldException nsfe)
                    {
                    	tag = new HTML.UnknownTag(name);
                    }
                	
                    
                    attributes = preferences.get(name).split(" ");
                    list = new ArrayList<HTML.Attribute>(attributes.length);
                    //list = new ArrayList<String>(attributes.length);
                    
                    for ( int i = 0; i < attributes.length; i++ )
                    {
                        if ( attributes[i].length() != 0 ) 
                        {
                        	list.add( (HTML.Attribute)HTML.Attribute.class.getField(attributes[i]).get(HTML.Attribute.ACTION) );
                        	//list.add( attributes[i].toLowerCase() );
                        }
                    }
                    
                    tags.put(tag,list);
                }
                catch (Exception e)
                {
                    Log.errors.log(HTMLCanonicalizer.class,e);
                }
            }
            
            RULES_CACHE.put(rulesFile,tags);
        }        
    }
    
    public static void init()
    {
    	//new al.javax.swing.text.html.parser.ParserDelegator();
        new ParserDelegator();
        try
        {
            DTD dtd = DTD.getDTD("html32");
            
            //AttributeList atts = new AttributeList(", int type, int modifier, String value, Vector<?> values, AttributeList next);
            dtd.defineElement("marquee",DTDConstants.ANY, false, false, null, null, null, null);
            dtd.defineElement("embed",DTDConstants.ANY, false, false, null, null, null, null);
            
            Properties htmlEntities = new Properties();
            InputStream is = null;
            try
            {
                is = HTMLCanonicalizer.class.getResourceAsStream("html-4.1-ext-prefs.xml");
                htmlEntities.loadFromXML(is);
            }
            catch (IOException e)
            {
                Log.errors.log(HTMLCanonicalizer.class,e);
            }
            finally
            {
                if ( is != null ) { try { is.close(); } catch (Exception e) { } }
            }

                        
            String key;
            String value;
            int ch;
            for (Enumeration<Object> e = htmlEntities.keys(); e.hasMoreElements(); )
            {
                key = e.nextElement().toString();
                value = Strings.ifNull(htmlEntities.get(key),"&" + key + ";");
                
                if ( value.matches("^[0-9]{1,5}$") )
                {
                    ch =  Integer.parseInt(value);
                    value = new String(new char[] { (char)ch });
                }

                dtd.defineEntity(key,DTDConstants.GENERAL,value.toCharArray());
            }
            
            
        }
        catch (Exception e)
        {
            Log.errors.log(HTMLCanonicalizer.class,e);
        }
    }
    
    public void exclude(HTML.Tag tag)
    {
    	excludes.add(tag);
    }
    
    public boolean isConsumeText()
    {
        return consumeText;
    }

    public void setConsumeText(boolean consumeText)
    {
        this.consumeText = consumeText;
    }

    public boolean isConsumeTags()
    {
        return consumeTags;
    }

    public void setConsumeTags(boolean consumeTags)
    {
        this.consumeTags = consumeTags;
    }

    /**
     * Use this method for strict HTML src files
     * 
     * @param text
     * @return
     * @throws IOException
     */
    public String canonicalize(String text) throws IOException
    {
        buffer.setLength(0);
        if ( text != null )
        {
            Reader reader = new StringReader(text);
            
            new ParserDelegator().parse(reader,this,true);
        }
        

        return buffer.toString();
    }
    
    
    public String encode(String text) throws IOException
    {
        buffer.setLength(0);
        if ( text != null )
        {
            Reader reader = new StringReader(text.replaceAll("\r\n", "<br>").replaceAll("\n","<br>"));//.replaceAll(" ","&nbsp;"));
            new ParserDelegator().parse(reader,this,true);
        }
        

        return buffer.toString();
    }

        
    @SuppressWarnings("unchecked")
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position) 
    {
    	//System.out.println(tag + " " + tags.containsKey(tag) + " " + position);
        if ( tags.containsKey(tag) )
        {
            currentTag = tag;           
            buffer.append("<");
            buffer.append(tag);
            
            List<HTML.Attribute> list = tags.get(tag);
            
            Object o;
            for ( Enumeration e = attributes.getAttributeNames(); e.hasMoreElements(); )
            {
                o = e.nextElement();
                if ( list.contains(o) )
                {
                    if ( !(o == HTML.Attribute.HREF && attributes.getAttribute(o).toString().toLowerCase().trim().startsWith("javascript")) )
                    {
                        buffer.append(" ");
                        buffer.append(o);
                        buffer.append("=\"");
                        buffer.append(attributes.getAttribute(o));
                        buffer.append("\"");                    
                    }
                }
            }
            
            buffer.append(">");
        }
        else if ( !consumeTags )
        {
            currentTag = tag; 
            buffer.append("&lt;");
            buffer.append(tag);
            
            Object o;
            for ( Enumeration e = attributes.getAttributeNames(); e.hasMoreElements(); )
            {
                o = e.nextElement();
                
                buffer.append(" ");
                buffer.append(Strings.noTags(o.toString()));
                buffer.append("=\"");
                buffer.append(Strings.noTags(attributes.getAttribute(o).toString()));
                buffer.append("\"");
            }
            
            buffer.append("&gt;");            
        }
    }
    
    
    
    public void handleText(char[] chars, int position)
    {
    	
    	if ( eat ) return;
    	
    	String text = new String(chars);

    	if ( text.length() > 0 && text.charAt(0) == '>' ) text = text.substring(1);
    	

        if (  currentTag == null || !tags.containsKey(currentTag) || !tags.get(currentTag).contains(HTML.Attribute.HREF) )
        {
            Matcher matcher = pattern.matcher(text);

            String href;
            while (matcher.find())
            {
                try
                {
                    href = matcher.group();
                    
                    if ( href.endsWith(".") || href.endsWith(",") )
                    {        
                        char c = href.charAt(href.length()-1);

                        href = href.substring(0,href.length()-1);
                        String orig = href;
                        if ( !(href.toLowerCase().startsWith("http://") || href.toLowerCase().startsWith("https://")) )
                        {
                            href = "http://" + href;
                        }
                        
                        href = "<a href=\"" + href + "\">" + orig + "</a>" + c;
                        matcher.appendReplacement(buffer, href);
                    }
                    else
                    {
                        String orig = href;
                        if ( !(href.toLowerCase().startsWith("http://") || href.toLowerCase().startsWith("https://")) )
                        {
                            href = "http://" + href;
                        }
                        
                        href = "<a href=\"" + href + "\">" + orig + "</a>";
                        matcher.appendReplacement(buffer, href);
                    }

                }
                catch (Exception e)
                {
                    Log.errors.log(HTMLCanonicalizer.class,e);
                }
            }
            
            matcher.appendTail(buffer);
        }
        else if ( currentTag != null )
        {
        	//do nothing
        	buffer.append(Strings.noTags(text));
        }
        else
        {
        	buffer.append(Strings.noTags(text));	
        }
    }
    
    
    public void handleComment(char[] text, int position)
    {
        if ( !consumeTags )
        {
            buffer.append("<!-- ");
            buffer.append(Strings.noTags(new String(text)));
            buffer.append(" -->");   
        }
    }
    
    
    
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) 
    {
    	if ( excludes.contains(tag) )
    	{
    		eat = true;
    		currentTag = tag;
    	}
    	else
    	{
    		handleSimpleTag(tag,attributes,position);
    	}  	
    }  
    
    
    public void handleEndOfLineString(String s) 
    {
    	//System.out.println( (int) s.charAt(0) + " " + s.length());
        buffer.append(s);    		
    }  
    
    
    public void handleEndTag(HTML.Tag tag, int position) 
    {
    	if ( excludes.contains(tag) )
    	{
    		eat = false;
    	}
    	else if ( tags.containsKey(tag) )
        {
            buffer.append("</");
            buffer.append(tag);
            buffer.append(">");
        }
        else if ( !consumeTags )
        {
            buffer.append("&lt;/");
            buffer.append(tag);
            buffer.append("&gt;");            
        }
        
        currentTag = null;
    }  
    
    
    public static void main(String[] args)
    {
        try
        {
            init();
            
            
            //RandomAccessFile raf = new RandomAccessFile("c:/downloads/test.html","r");
            //byte[] ba = new byte[(int) raf.length()];
            //raf.readFully(ba);
            //raf.close();
            //System.out.println("<pre>rashid\r\n</pre>");
            String in = "<object width=\"425\" height=\"350\"><param name=\"movie\" value=\"http://www.youtube.com/v/coV06ChYWJo\"></param><param name=\"wmode\" value=\"transparent\"></param><embed src=\"http://www.youtube.com/v/coV06ChYWJo\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" width=\"425\" height=\"350\"></embed></object>";//"<marquee a=b DIRECTION=up>hello</marquee> <img/>>try this <a></a>";// http://feeds.feedburner.com/~r/OmMalik/~3/102837392/%2Dl    <pre>\r\nrashid\nhi\nman</pre>\n             <a onclick=sdfsf href=\"sdf\"> click here </a> http://www.cliqcafe.com/www/galleryview.html?fid=3606&a=b <a href=\"this.html\"><img src=\"http://sdfs.jpg\"></a>  http://www.cnn.com/index.html#games <a href=''>www.rashid-mayes.com</a>\n      http://www.yahoo.com is a good site";//new String(ba);

            HTMLCanonicalizer  canonicalizer = new HTMLCanonicalizer("safe-tags");
            //canonicalizer.exclude(HTML.Tag.STYLE);
            //canonicalizer.setConsumeText(false);
            
            //in = new String(ba);
            in = "jjjkjl <a href=\"http://www.active.com/page/Event_Details.htm?event_id=1527151&assetId=0e3d4b79-ff9d-4af8-b2b8-4abe9c26227d\"><p>Snowbird</p></a>";
            System.out.println(canonicalizer.encode(in));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        System.exit(0);
    }
}