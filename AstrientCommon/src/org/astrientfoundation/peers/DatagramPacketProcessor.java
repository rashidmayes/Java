/*
 * Copyright (C) 2006 Astrient Foundation, Incorporated 
 * 
 * Astrient Foundation, Inc.
 * www.astrientfoundation.org
 * info@astrientfoundation.org
 */
package org.astrientfoundation.peers;

import java.net.DatagramPacket;

public interface DatagramPacketProcessor
{
    public void process(Multicaster multicaster, DatagramPacket packet);
}
