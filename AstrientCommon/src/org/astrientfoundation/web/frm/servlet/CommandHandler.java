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
package org.astrientfoundation.web.frm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.astrientfoundation.prefs.Preferences;
import org.astrientfoundation.web.frm.CommandException;
import org.astrientfoundation.web.frm.InvalidRequestException;
import org.astrientfoundation.web.frm.NoPrincipalException;
import org.astrientfoundation.web.frm.PrincipalNotAuthorizedException;
import org.astrientfoundation.web.frm.RequestPrincipal;
import org.astrientfoundation.web.frm.SmartRequest;

public abstract class CommandHandler
{
    public static final String KEY_ERRORS = "astrientlabs.x_cmd_errors";
    public static final String KEY_MESSAGES = "astrientlabs.x_cmd_messages";
    public static final Preferences PREFS = new Preferences("comp.web.frm.commandhandler");
    
    protected static final Preferences PAGES = new Preferences("comp.web.frm.handlerpages");
    
    protected RequestPrincipal.AuthLevel authLevel;
    protected ServletContext servletContext;
    protected SmartRequest request;
    protected HttpServletResponse response;
    protected boolean useRedirects = false;
    
    protected List<CommandMessage> messages = new ArrayList<CommandMessage>();
    protected List<CommandMessage> errors = new ArrayList<CommandMessage>();

    protected void init(ServletContext servletContext, SmartRequest request, HttpServletResponse response)
    {
        this.servletContext = servletContext;
        this.request = request;
        this.response = response;
    }
    
    protected void finish(String errorPage, String successPage) throws ServletException, IOException
    {
        String next = (errors.isEmpty()) ? successPage : errorPage;
        
        request.setErrors(errors);
        request.setMessages(messages);
        
        if ( useRedirects && errors.isEmpty() )
        {
            response.sendRedirect(next);
        }
        else
        {
            request.forward(response,next);
        }
    }
    
    public final void process() throws CommandException, ServletException, IOException
    {
        try
        {
            isRequestValid();
            _process();
        }
        catch (NoPrincipalException e)
        {
            handleNoPrincipal(e);
        }
        catch (InvalidRequestException e)
        {
            handleInvalidRequest(e);
        }
        catch (PrincipalNotAuthorizedException e)
        {
            handlePrincipalNotAuthorized(e);
        }
        finally
        {
            
        }
    }
    
    protected void handleNoPrincipal(NoPrincipalException e)
    {
        try
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        catch (IOException ioe)
        {
            
        }
    }

    protected void handleInvalidRequest(InvalidRequestException e)
    {
        try
        {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (IOException ioe)
        {
            
        }
    }
    
    protected void handlePrincipalNotAuthorized(PrincipalNotAuthorizedException e)
    {
        try
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        catch (IOException ioe)
        {
            
        }
    }
    
    public void isRequestValid() throws CommandException
    {
        if ( this.authLevel != null && this.authLevel != RequestPrincipal.AuthLevel.ANONYMOUS )
        {
            RequestPrincipal principal = request.getPrincipal();
            
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
    
    public abstract void _process()  throws CommandException, ServletException, IOException;
}
