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
package org.astrientfoundation.web.frm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.astrientfoundation.util.Strings;
import org.astrientfoundation.web.frm.servlet.CommandHandler;
import org.astrientfoundation.web.frm.servlet.CommandMessage;

public class SmartRequest implements HttpServletRequest
{
    private HttpServletRequest request;

    public SmartRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    /*
    public Object getAttribute(String arg0)
    {
        return this.request.getAttribute(arg0);
    }

    @SuppressWarnings("unchecked")
    public Enumeration getAttributeNames()
    {
        return this.request.getAttributeNames();
    }

    public String getAuthType()
    {
        return this.request.getAuthType();
    }

    public String getCharacterEncoding()
    {
        return this.request.getCharacterEncoding();
    }

    public int getContentLength()
    {
        return this.request.getContentLength();
    }

    public String getContentType()
    {
        return this.request.getContentType();
    }

    public String getContextPath()
    {
        return this.request.getContextPath();
    }

    public Cookie[] getCookies()
    {
        return this.request.getCookies();
    }

    public long getDateHeader(String arg0)
    {
        return this.request.getDateHeader(arg0);
    }

    public String getHeader(String arg0)
    {
        return this.request.getHeader(arg0);
    }

    @SuppressWarnings("unchecked")
    public Enumeration getHeaderNames()
    {
        return this.request.getHeaderNames();
    }

    @SuppressWarnings("unchecked")
    public Enumeration getHeaders(String arg0)
    {
        return this.request.getHeaders(arg0);
    }

    public ServletInputStream getInputStream() throws IOException
    {
        return this.request.getInputStream();
    }

    public int getIntHeader(String arg0)
    {
        return this.request.getIntHeader(arg0);
    }

    public String getLocalAddr()
    {
        return this.request.getLocalAddr();
    }

    public Locale getLocale()
    {
        return this.request.getLocale();
    }

    @SuppressWarnings("unchecked")
    public Enumeration getLocales()
    {
        return this.request.getLocales();
    }

    public String getLocalName()
    {
        return this.request.getLocalName();
    }

    public int getLocalPort()
    {
        return this.request.getLocalPort();
    }

    public String getMethod()
    {
        return this.request.getMethod();
    }

    public String getParameter(String arg0)
    {
        return this.request.getParameter(arg0);
    }

    @SuppressWarnings("unchecked")
    public Map getParameterMap()
    {
        return this.request.getParameterMap();
    }

    @SuppressWarnings("unchecked")
    public Enumeration getParameterNames()
    {
        return this.request.getParameterNames();
    }

    public String[] getParameterValues(String arg0)
    {
        return this.request.getParameterValues(arg0);
    }

    public String getPathInfo()
    {
        return this.request.getPathInfo();
    }

    public String getPathTranslated()
    {
        return this.request.getPathTranslated();
    }

    public String getProtocol()
    {
        return this.request.getProtocol();
    }

    public String getQueryString()
    {
        return this.request.getQueryString();
    }

    public BufferedReader getReader() throws IOException
    {
        return this.request.getReader();
    }

    public String getRealPath(String arg0)
    {
        return null;//this.request.getRealPath(arg0);
    }

    public String getRemoteAddr()
    {
        return this.request.getRemoteAddr();
    }

    public String getRemoteHost()
    {
        return this.request.getRemoteHost();
    }

    public int getRemotePort()
    {
        return this.request.getRemotePort();
    }

    public String getRemoteUser()
    {
        return this.request.getRemoteUser();
    }

    public RequestDispatcher getRequestDispatcher(String arg0)
    {
        return this.request.getRequestDispatcher(arg0);
    }

    public String getRequestedSessionId()
    {
        return this.request.getRequestedSessionId();
    }

    public String getRequestURI()
    {
        return this.request.getRequestURI();
    }

    public StringBuffer getRequestURL()
    {
        return this.request.getRequestURL();
    }

    public String getScheme()
    {
        return this.request.getScheme();
    }

    public String getServerName()
    {
        return this.request.getServerName();
    }

    public int getServerPort()
    {
        return this.request.getServerPort();
    }

    public String getServletPath()
    {
        return this.request.getServletPath();
    }

    public HttpSession getSession()
    {
        return this.request.getSession();
    }

    public HttpSession getSession(boolean arg0)
    {
        return this.request.getSession(arg0);
    }

    public Principal getUserPrincipal()
    {
        return this.request.getUserPrincipal();
    }

    public boolean isRequestedSessionIdFromCookie()
    {
        return this.request.isRequestedSessionIdFromCookie();
    }

    public boolean isRequestedSessionIdFromUrl()
    {
        return false;//this.request.isRequestedSessionIdFromUrl();
    }

    public boolean isRequestedSessionIdFromURL()
    {
        return this.request.isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdValid()
    {
        return this.request.isRequestedSessionIdValid();
    }

    public boolean isSecure()
    {
        return this.request.isSecure();
    }

    public boolean isUserInRole(String arg0)
    {
        return this.request.isUserInRole(arg0);
    }

    public void removeAttribute(String arg0)
    {
        this.request.removeAttribute(arg0);
    }

    public void setAttribute(String arg0, Object arg1)
    {
        this.request.setAttribute(arg0, arg1);
    }

    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
    {
        this.request.setCharacterEncoding(arg0);
    }*/

    public int getInt(String name, int def)
    {
        String value = request.getParameter(name);
        return ( Strings.isNull(value) || !value.matches("[-]{0,1}[0-9]+") ) ? def : Integer.parseInt(value);
    }

    public long getLong(String name, long def)
    {
        String value = request.getParameter(name);
        return ( Strings.isNull(value) || !value.matches("[-]{0,1}[0-9]+") ) ? def : Long.parseLong(value);
    }
    
    public double getDouble(String name, double def)
    {
        String value = request.getParameter(name);
        return ( Strings.isNull(value) || !value.matches("[-]{0,1}[0-9\\\\.]+") ) ? def : Double.parseDouble(value);
    }    
    
    public float getFloat(String name, float def)
    {
        String value = request.getParameter(name);
        return ( Strings.isNull(value) || !value.matches("[-]{0,1}[0-9\\\\.]+") ) ? def : Float.parseFloat(value);
    }
    
    public String getString(String name, String def)
    {
        String value = request.getParameter(name);
        return ( Strings.isNull(value) ) ? def : value;
    }
    
    public boolean getBoolean(String name, boolean def)
    {
        String value = request.getParameter(name);
        return ( Strings.isNull(value) ) ? def : Boolean.valueOf(value);
    }
    
    public String getString(String name, String def, String ifNull)
    {
        String value = request.getParameter(name);
        
        return ( Strings.isNull(value) ) ? Strings.ifNull(def,ifNull) : value;
    }
    
    public void forward(HttpServletResponse response, String url) throws ServletException, IOException
    {
        RequestDispatcher dis = this.getRequestDispatcher(url);
        dis.forward(this.request, response);
    }
    
    public RequestPrincipal getPrincipal()
    {
        HttpSession session = this.getSession();
        if ( session != null ) 
        {
            return (RequestPrincipal)session.getAttribute(RequestPrincipal.SESSION_KEY);
        }
        
        return null;
    }

    public void setErrors(List<CommandMessage> messages)
    {
        if ( !messages.isEmpty() )
        {
            request.setAttribute(CommandHandler.KEY_ERRORS, messages);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<CommandMessage> getErrors()
    {
        return (List<CommandMessage>)request.getAttribute(CommandHandler.KEY_ERRORS);
    }
    
    public void addError(CommandMessage error)
    {
        synchronized (CommandHandler.KEY_ERRORS )
        {
            List<CommandMessage> errors = getErrors();
            if ( errors == null )
            {
                errors = new ArrayList<CommandMessage>();
            }
            errors.add(error);
            setErrors(errors);
        }
    }
    
    public void setMessages(List<CommandMessage> messages)
    {
        if ( !messages.isEmpty() )
        {
            request.setAttribute(CommandHandler.KEY_MESSAGES, messages);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<CommandMessage> getMessages()
    {
        return (List<CommandMessage>)request.getAttribute(CommandHandler.KEY_MESSAGES);
    }
    
    public void addMessage(CommandMessage message)
    {
        synchronized (CommandHandler.KEY_MESSAGES )
        {
            List<CommandMessage> messages = getMessages();
            if ( messages == null )
            {
                messages = new ArrayList<CommandMessage>();
            }
            messages.add(message);
            setMessages(messages);
        }
    }
    
    public Cookie getCookie(String name)
    {
        Cookie[] cookies = this.request.getCookies();
        if ( cookies != null )
        {
            for ( Cookie cookie : cookies )
            {
                if ( cookie.getName().equalsIgnoreCase(name) )
                {
                    return cookie;
                }
            }   
        }
      
        return null;
    }
    
    public String getCookieValue(String name, String def)
    {
        Cookie[] cookies = this.request.getCookies();
        if ( cookies != null )
        {
            for ( Cookie cookie : cookies )
            {
                if ( cookie.getName().equalsIgnoreCase(name) )
                {
                    return cookie.getValue();
                }
            }   
        }
      
        return def;
    }

	public boolean authenticate(HttpServletResponse arg0) throws IOException,
			ServletException
	{
		return request.authenticate(arg0);
	}

	public AsyncContext getAsyncContext()
	{
		return request.getAsyncContext();
	}

	public Object getAttribute(String arg0)
	{
		return request.getAttribute(arg0);
	}

	public Enumeration<String> getAttributeNames()
	{
		return request.getAttributeNames();
	}

	public String getAuthType()
	{
		return request.getAuthType();
	}

	public String getCharacterEncoding()
	{
		return request.getCharacterEncoding();
	}

	public int getContentLength()
	{
		return request.getContentLength();
	}

	public String getContentType()
	{
		return request.getContentType();
	}

	public String getContextPath()
	{
		return request.getContextPath();
	}

	public Cookie[] getCookies()
	{
		return request.getCookies();
	}

	public long getDateHeader(String arg0)
	{
		return request.getDateHeader(arg0);
	}

	public DispatcherType getDispatcherType()
	{
		return request.getDispatcherType();
	}

	public String getHeader(String arg0)
	{
		return request.getHeader(arg0);
	}

	public Enumeration<String> getHeaderNames()
	{
		return request.getHeaderNames();
	}

	public Enumeration<String> getHeaders(String arg0)
	{
		return request.getHeaders(arg0);
	}

	public ServletInputStream getInputStream() throws IOException
	{
		return request.getInputStream();
	}

	public int getIntHeader(String arg0)
	{
		return request.getIntHeader(arg0);
	}

	public String getLocalAddr()
	{
		return request.getLocalAddr();
	}

	public String getLocalName()
	{
		return request.getLocalName();
	}

	public int getLocalPort()
	{
		return request.getLocalPort();
	}

	public Locale getLocale()
	{
		return request.getLocale();
	}

	public Enumeration<Locale> getLocales()
	{
		return request.getLocales();
	}

	public String getMethod()
	{
		return request.getMethod();
	}

	public String getParameter(String arg0)
	{
		return request.getParameter(arg0);
	}

	public Map<String, String[]> getParameterMap()
	{
		return request.getParameterMap();
	}

	public Enumeration<String> getParameterNames()
	{
		return request.getParameterNames();
	}

	public String[] getParameterValues(String arg0)
	{
		return request.getParameterValues(arg0);
	}

	public Part getPart(String arg0) throws IOException, IllegalStateException,
			ServletException
	{
		return request.getPart(arg0);
	}

	public Collection<Part> getParts() throws IOException,
			IllegalStateException, ServletException
	{
		return request.getParts();
	}

	public String getPathInfo()
	{
		return request.getPathInfo();
	}

	public String getPathTranslated()
	{
		return request.getPathTranslated();
	}

	public String getProtocol()
	{
		return request.getProtocol();
	}

	public String getQueryString()
	{
		return request.getQueryString();
	}

	public BufferedReader getReader() throws IOException
	{
		return request.getReader();
	}

	public String getRealPath(String arg0)
	{
		return request.getRealPath(arg0);
	}

	public String getRemoteAddr()
	{
		return request.getRemoteAddr();
	}

	public String getRemoteHost()
	{
		return request.getRemoteHost();
	}

	public int getRemotePort()
	{
		return request.getRemotePort();
	}

	public String getRemoteUser()
	{
		return request.getRemoteUser();
	}

	public RequestDispatcher getRequestDispatcher(String arg0)
	{
		return request.getRequestDispatcher(arg0);
	}

	public String getRequestURI()
	{
		return request.getRequestURI();
	}

	public StringBuffer getRequestURL()
	{
		return request.getRequestURL();
	}

	public String getRequestedSessionId()
	{
		return request.getRequestedSessionId();
	}

	public String getScheme()
	{
		return request.getScheme();
	}

	public String getServerName()
	{
		return request.getServerName();
	}

	public int getServerPort()
	{
		return request.getServerPort();
	}

	public ServletContext getServletContext()
	{
		return request.getServletContext();
	}

	public String getServletPath()
	{
		return request.getServletPath();
	}

	public HttpSession getSession()
	{
		return request.getSession();
	}

	public HttpSession getSession(boolean arg0)
	{
		return request.getSession(arg0);
	}

	public Principal getUserPrincipal()
	{
		return request.getUserPrincipal();
	}

	public boolean isAsyncStarted()
	{
		return request.isAsyncStarted();
	}

	public boolean isAsyncSupported()
	{
		return request.isAsyncSupported();
	}

	public boolean isRequestedSessionIdFromCookie()
	{
		return request.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL()
	{
		return request.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdFromUrl()
	{
		return request.isRequestedSessionIdFromUrl();
	}

	public boolean isRequestedSessionIdValid()
	{
		return request.isRequestedSessionIdValid();
	}

	public boolean isSecure()
	{
		return request.isSecure();
	}

	public boolean isUserInRole(String arg0)
	{
		return request.isUserInRole(arg0);
	}

	public void login(String arg0, String arg1) throws ServletException
	{
		request.login(arg0, arg1);
	}

	public void logout() throws ServletException
	{
		request.logout();
	}

	public void removeAttribute(String arg0)
	{
		request.removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1)
	{
		request.setAttribute(arg0, arg1);
	}

	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException
	{
		request.setCharacterEncoding(arg0);
	}

	public AsyncContext startAsync()
	{
		return request.startAsync();
	}

	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1)
	{
		return request.startAsync(arg0, arg1);
	}
    
    
    
}