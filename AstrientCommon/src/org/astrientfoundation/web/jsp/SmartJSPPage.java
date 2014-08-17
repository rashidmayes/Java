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
package org.astrientfoundation.web.jsp;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.astrientfoundation.files.FileUtil;
import org.astrientfoundation.util.Dates;
import org.astrientfoundation.util.Strings;
import org.astrientfoundation.util.Text;


public abstract class SmartJSPPage implements HttpJspPage
{
	protected boolean log = false;
    protected ServletConfig servletConfig;
    
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		_jspService(request, response);			
	}
		
	public String getFileSize(long size, Locale locale)
	{
		return FileUtil.getSizeString(size, locale);
	}
	
	public String getString(String key, Locale locale)
	{
	    return Text.instance.getString(key,locale);
	}
	
	public String getString(String context, String key, Locale locale)
	{
	    return Text.instance.getString(context,key,locale);
	}
	
	public String getString(String key, Locale locale, String[] args)
	{
	    String fmt = Text.instance.getString(key,locale);
	    if ( fmt == null )
	    {
	        return null;
	    }
	    else
	    {
	        MessageFormat format = new MessageFormat(fmt);
	        return format.format(args);
	    }
	}
	
	public String getString(String context, String key, Locale locale, String[] args)
	{
	    String fmt = Text.instance.getString(context,key,locale);
	    if ( fmt == null )
	    {
	        return null;
	    }
	    else
	    {
	        MessageFormat format = new MessageFormat(fmt);
	        return format.format(args);
	    }
	}
	
    public void forward(HttpServletRequest req, HttpServletResponse res, String url) throws ServletException, IOException
    {
        RequestDispatcher dis = req.getRequestDispatcher(url);
        dis.forward(req, res);
    }

    public String decode(String text)
    {
        return Strings.ifNull(text,"").replaceAll("<br/>","\n");
    }
    
	public String fixString(String text)
	{
		return (text == null ) ? "" : text.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n","<br/>");
	}
	
	public String fixString(String text, boolean noHTML)
	{
		return (text == null ) ? "" : text.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	public final ServletConfig getServletConfig()
	{
		return servletConfig;
	}
	
	public String getServletInfo()
    {
        return getClass().getSimpleName();
    }

    public void init() throws ServletException 
	{
	}
	
	public final void init(ServletConfig servletConfig) throws ServletException 
	{
		this.servletConfig = servletConfig;
		jspInit();
	}

	public void destroy()
	{
		jspDestroy();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
	}

	public void jspDestroy() 
	{
	}
	
	public void jspInit() 
	{
	}
	
	
    public static String fixQoutes(String in)
    {
    	return Strings.fixQoutes(in);
    }
    
    public boolean isNull(String string)
    {
        return Strings.isNull(string);
    }
    
    public static String getText(String text, int len,Locale locale,String suffix)
    {
        return Strings.getText(text, len, locale, suffix);
    }
    
    public static String getText(String text, int len,Locale locale)
    {
        return Strings.getText(text,len,locale,"...");
    }

	public static String ifNull(Object o, String def)
	{
		return Strings.ifNull(o, def);
	}
	
	public static String randomString(int length)
	{    
	    return Strings.randomString(length);
	}
    
    public String[] split(String s, int i)
    {
        return Strings.split(s, i);
    }
	
	public static String format(Date date, String format)
	{
		return Dates.format(date,format,Locale.US);
	}
	
	public static String format(Date date, String format, Locale locale)
	{
		return Dates.format(date,format,null,locale);
	}	

	public static String format(Date date, String format, String nullmask)
	{
		return Dates.format(date, format, nullmask);
	}
	
	public String formatCurrency(double in, Locale locale)
	{
		return NumberFormat.getCurrencyInstance(locale).format(in);
	}
	
	public String formatNumber(long in, Locale locale)
	{
		return NumberFormat.getNumberInstance(locale).format(in);
	}

	public abstract void _jspService(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException;
}
