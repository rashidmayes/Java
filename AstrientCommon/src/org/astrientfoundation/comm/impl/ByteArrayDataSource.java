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
package org.astrientfoundation.comm.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;


public class ByteArrayDataSource implements DataSource
{
    private String contentType;
    private String name;
    
    private ByteArrayOutputStream outputStream;
    
    public ByteArrayDataSource(String name, String contentType) throws IOException
    {
        this.name = name;
        this.contentType = contentType;
        
        outputStream = new ByteArrayOutputStream();
    }    
    
    public String getContentType()
    {
        return contentType;
    }
    
    
    public InputStream getInputStream() throws IOException
    {
        
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    
    
    public String getName()
    {
        return name;
    }
    
    
    public OutputStream getOutputStream() throws IOException
    {
        return outputStream;
    }
}
