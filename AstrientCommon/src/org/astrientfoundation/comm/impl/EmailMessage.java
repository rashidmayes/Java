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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.astrientfoundation.comm.Message;
import org.astrientfoundation.files.FileUtil;


public class EmailMessage extends Message
{
    private String subject;
    private String contentType;
    private List<DataSource> attachments = new ArrayList<DataSource>();
    
    private List<String> to = new ArrayList<String>();
    private List<String> bcc = new ArrayList<String>();
    private List<String> cc = new ArrayList<String>();
    
    public void setTo(String address)
    {
        to.add(address);
    }
    
    public void addToAddress(String address)
    {
        if ( !to.contains(address) )
        to.add(address);
    }
    
    public void addBCCAddress(String address)
    {
        if ( !bcc.contains(address) )
        bcc.add(address);
    }
    
    public void addCCAddress(String address)
    {
        if ( !cc.contains(address) )
        cc.add(address);
    }
    
    public List<String> getCCAddresses()
    {
        return cc;
    }
    
    public List<String> getBCCAddresses()
    {
        return bcc;
    }
    
    public List<String> getToAddresses()
    {
        return to;
    }
    
    
    /**
     * @return Returns the subject.
     */
    public String getSubject()
    {
        return subject;
    }
    
    
    /**
     * @param subject The subject to set.
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    
    
    public String getContentType()
    {
        return this.contentType;
    }
    
    
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    
    public void addAttachment(File f)
    {
        attachments.add(new FileDataSource(f.getPath()));
    }
    
    public void addAttachment(String name, byte data[]) throws IOException
    {
        String contentType = FileUtil.typeOf(data);
        ByteArrayDataSource bads = new ByteArrayDataSource(name,contentType);
        bads.getOutputStream().write(data);
        bads.getOutputStream().write(-1);
        attachments.add(bads);
    }
    
    public void addAttachment(String name, String contentType, byte data[]) throws IOException
    {
        ByteArrayDataSource bads = new ByteArrayDataSource(name,contentType);
        bads.getOutputStream().write(data);
        bads.getOutputStream().write(-1);
        attachments.add(bads);
    }
    
    public void addAttachment(DataSource dataSource)
    {
        attachments.add(dataSource);
    }
    
    
    public DataSource[] getAttachments()
    {
        return (DataSource[])attachments.toArray(new DataSource[0]);
    }
}
