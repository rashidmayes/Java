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
/******************************************************************************
 *  Not orginal author; needs cite
 */
package org.astrientfoundation.web.frm.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharFilter implements Filter
{
	private String encoding;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		request.setCharacterEncoding(encoding);
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig)
	{
		  encoding = filterConfig.getInitParameter("requestEncoding");
		  if( encoding == null )
		  {
		      encoding = "UTF-8";
		  }
	}

	public void destroy()
	{
	}
}
