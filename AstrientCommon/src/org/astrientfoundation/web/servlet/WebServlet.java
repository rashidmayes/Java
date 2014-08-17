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
package org.astrientfoundation.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.astrientfoundation.logging.Log;

public abstract class WebServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6879177874384632867L;
	public static boolean debug = false;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            _doGet(request, response);
        }
        catch (ServletException e)
        {
            Log.errors.log(getClass(), e);
            throw e;
        }
        catch (IOException e)
        {
            Log.errors.log(getClass(), e);
            throw e;
        }
    }

    protected void forward(HttpServletRequest req, HttpServletResponse res, String url) throws ServletException, IOException
    {
        RequestDispatcher dis = getServletContext().getRequestDispatcher(url);
        dis.forward(req, res);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

    public abstract void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}