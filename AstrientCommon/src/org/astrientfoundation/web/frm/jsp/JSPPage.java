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
package org.astrientfoundation.web.frm.jsp;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.astrientfoundation.files.CachingFileStore;
import org.astrientfoundation.files.FileStore;
import org.astrientfoundation.files.FileUtil;
import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.Preferences;
import org.astrientfoundation.util.Dates;
import org.astrientfoundation.util.Strings;
import org.astrientfoundation.util.Text;
import org.astrientfoundation.util.Timer;
import org.astrientfoundation.web.frm.CommandException;
import org.astrientfoundation.web.frm.InvalidRequestException;
import org.astrientfoundation.web.frm.NoPrincipalException;
import org.astrientfoundation.web.frm.PrincipalNotAuthorizedException;
import org.astrientfoundation.web.frm.RequestPrincipal;
import org.astrientfoundation.web.frm.SmartRequest;


public abstract class JSPPage implements HttpJspPage
{
    protected static final Preferences jsp_Preferences = new Preferences("comp.web.frm.jsp");
    private static final FileStore helpFileStore = new CachingFileStore(jsp_Preferences.get("help.dir",System.getProperty("user.home","")+"/astrientlabs/comp.web.frm.jsp/help"));
    private static final Set<String> validHelpContexts = new HashSet<String>();
    
	protected boolean log = false;
    protected ServletConfig servletConfig;
    protected RequestPrincipal.AuthLevel authLevel;
    
    protected String helpId = null;
    protected long jsp_modified;
    protected long jsp_size;
    protected long jsp_requestTime = 0;
    protected int jsp_requestsHandled = 0;
    
    public static void saveHelpFile(String context,String helpText)
    {
        helpFileStore.add(context, "help.html", helpText.getBytes());
    }
    
    public static void resetHelpContexts()
    {
        try
        {
            validHelpContexts.clear();
            File dir = helpFileStore.getRoot();
            if ( dir.canRead() )
            {
                for ( String name : dir.list() )
                {
                    Log.info.log(JSPPage.class, "Adding help context " + name);
                    validHelpContexts.add(name);
                }
            }
        }
        catch (Exception e)
        {
            Log.errors.log(JSPPage.class,e);
        }
    }
    
    public boolean hasHelp()
    {
        return ( helpId != null && validHelpContexts.contains(helpId) );
    }
    
    public String getHelpId()
    {
    	return helpId;
    }
    
    public void jspInit()
    {
        helpId = this.getClass().getName();
        
        try
        {
            File file = new File(getServletConfig().getServletContext().getAttribute("javax.servlet.context.tempdir") + "/" + this.getClass().getName().replaceAll("\\.","/") + ".class");
 
            if ( file.exists() )
            {
                jsp_modified = file.lastModified();
                jsp_size = file.length();
            }   
        }
        catch (Exception e)
        {
            Log.errors.log(getClass(),e);
        }
    }
    
    public String getHelpText(Locale locale)
    {   
        return getHelpText(this.helpId,locale);
    }
    
    public String getHelpText(String helpId, Locale locale)
    {
        if ( helpId != null )
        {
            try
            {
                String file = "help_" + locale.getLanguage() + ".html";
                if ( helpFileStore.exists(helpId, file) )
                {
                    return new String(helpFileStore.get(helpId,file));   
                }
                else
                {
                    return new String(helpFileStore.get(helpId,"help.html"));    
                }
            }
            catch (Exception e)
            {
                Log.errors.log(getClass(),e);
            }
        }
        
        return null;
    }
    
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
	{
        Timer t = new Timer();
        jsp_requestsHandled++;
	        
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try
		{
            isRequestValid(request);           
            _jspService(request, response);			
		}
        catch (NoPrincipalException e)
        {
            handleNoPrincipal(request,response,e);
        }
        catch (InvalidRequestException e)
        {
            handleInvalidRequest(request,response,e);
        }
        catch (PrincipalNotAuthorizedException e)
        {
            handlePrincipalNotAuthorized(request,response,e);
        }
        catch ( CommandException ce )
        {
            throw new ServletException(ce);
        }
        finally
        {
            jsp_requestTime += t.elapsed();
        }
	}
    
    protected void handleNoPrincipal(HttpServletRequest request, HttpServletResponse response,NoPrincipalException e)
    {
        try
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        catch (IOException ioe)
        {
            
        }
    }

    protected void handleInvalidRequest(HttpServletRequest request, HttpServletResponse response,InvalidRequestException e)
    {
        try
        {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (IOException ioe)
        {
            
        }
    }
    
    protected void handlePrincipalNotAuthorized(HttpServletRequest request, HttpServletResponse response, PrincipalNotAuthorizedException e)
    {
        try
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        catch (IOException ioe)
        {
            
        }
    }
    
    public void isRequestValid(HttpServletRequest request) throws CommandException
    {
        if ( this.authLevel != null && this.authLevel != RequestPrincipal.AuthLevel.ANONYMOUS )
        {
            RequestPrincipal principal = new SmartRequest(request).getPrincipal();
            
            if ( principal == null )
            {
                throw new NoPrincipalException("text.noprincipal");
            }
            else
            {
                if ( !principal.authorized(authLevel) )
                {
                    throw new PrincipalNotAuthorizedException();
                } 
            }   
        }
    }
	
	public boolean isNull(String string)
	{
		return Strings.isNull(string);
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
	
    public static String fixQoutes(String in)
    {
    	return Strings.fixQoutes(in);
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
	
    public static String getDateString(Date date, String longPattern, String shortPattern, Locale locale)
    {
        return Dates.getDateString(date, longPattern, shortPattern, locale);
    }
    
    
    public static String format(long nullValue, long date, String format, Locale locale, TimeZone timeZone)
    {
        if ( date > nullValue ) 
        {
        	return Dates.format(new Date(date),format,null,locale,timeZone);
        }
        else
        {
            return "";
        }
    } 
    
    
    public static String format(Date date, String format, TimeZone timeZone)
    {
        return Dates.format(date,format,Locale.US,timeZone);
    }
    
    public static String format(Date date, String format, Locale locale, TimeZone timeZone)
    {
        return Dates.format(date,format,null,locale,timeZone);
    }   

    public static String format(Date date, String format, String nullmask, TimeZone timeZone)
    {
        return Dates.format(date, format, nullmask,timeZone);
    }
    
    public static String getDateString(Date date, String longPattern, String shortPattern, Locale locale, TimeZone timeZone)
    {
        return Dates.getDateString(date, longPattern, shortPattern, locale,timeZone);
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
    
	public abstract void _jspService(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException;
}
