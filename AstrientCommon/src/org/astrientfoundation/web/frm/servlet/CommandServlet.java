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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.prefs.ClassMap;
import org.astrientfoundation.prefs.ClassRegister;
import org.astrientfoundation.web.frm.SmartRequest;
import org.astrientfoundation.web.servlet.WebServlet;


public class CommandServlet extends WebServlet
{
    private static final long serialVersionUID = -3631044071027561753L;    
    private static ClassMap<CommandHandler> classMap;

    public void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String commandName = request.getServletPath();
            if ( commandName == null )  
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else
            {
                commandName = commandName.substring(1, commandName.lastIndexOf(".cmd"));
                ClassRegister<CommandHandler> register = classMap.getRegister(commandName);    
                
                Log.info.log(CommandServlet.class, commandName + " handled by " + register);
                if ( register == null )
                {
                    response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,commandName);
                }
                else
                {
                    CommandHandler handler = register.getClassObject().newInstance();
                    
                    handler.init(getServletContext(), new SmartRequest(request), response);
                    handler.process();
                }
            }
        }
        catch (Exception e)
        {
            Log.errors.log(CommandServlet.class, e);
            request.setAttribute("javax.servlet.error.exception", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
        }
    }

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        
        classMap = new ClassMap<CommandHandler>(servletConfig.getInitParameter("classMapFile"));
        for ( ClassRegister<CommandHandler> register : classMap.getRegisters() )
        {
            Log.info.log(getClass(),register.getName() + " > " + register.getClassObject().getName());   
        }
    }
}