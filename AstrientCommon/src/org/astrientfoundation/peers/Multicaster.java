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
package org.astrientfoundation.peers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.astrientfoundation.logging.Log;
import org.astrientfoundation.services.Cron;
import org.astrientfoundation.services.ServiceManager;
import org.astrientfoundation.threads.AbstractScheduledTask;
import org.astrientfoundation.threads.Scheduler;
import org.astrientfoundation.threads.Task;
import org.astrientfoundation.threads.TaskRunner;

public class Multicaster extends AbstractScheduledTask implements Runnable
{
    public static enum MESSAGE_TYPE {
        HELLO {
            String getKey() { return "HELLO"; }
        },
        INFO {
            String getKey() { return "INFO"; }
        },
        DATA {
            String getKey() { return "DATA"; }
        },
        QUERY {
            String getKey() { return "QUERY"; }
        },
        ECHO {
            String getKey() { return "ECHO"; }
        },
        PING {
            String getKey() { return "PING"; }
        };
        
        abstract String getKey();
    }
    public static final int MAX_TTL = (1000*60*5*3);
        
    private TaskRunner taskRunner = new TaskRunner();
    private InetAddress inetAddress;
    private int serverSocketPort;
    private int multicastPort;
    private Thread thread;
    private Thread receiveThread;
    private ServerSocket serverSocket;
    private boolean shouldRun = true;
    private MulticastSocket multicastSocket;
    private DatagramPacketProcessor packetProcessor;
    private Map<String,Peer> peers = new HashMap<String,Peer>();
    
    public Multicaster(String friendlyName, int serverSocketPort, String host, int port) throws UnknownHostException
    {
        this(friendlyName,serverSocketPort,InetAddress.getByName(host),port);
    }
    
    public Multicaster(String name, int serverSocketPort,InetAddress inetAddress,int multicastPort)
    {
        super(name,new String[0]);
        
        this.inetAddress = inetAddress;
        this.serverSocketPort = serverSocketPort;
        this.multicastPort = multicastPort;
        
        this.setMinutes(Scheduler.toIntArray("@2",60));
    }

    public DatagramPacketProcessor getPacketProcessor()
    {
        return this.packetProcessor;
    }

    public void setPacketProcessor(DatagramPacketProcessor packetProcessor)
    {
        this.packetProcessor = packetProcessor;
    }

    public InetAddress getInetAddress()
    {
        return this.inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress)
    {
        this.inetAddress = inetAddress;
    }

    public int getServerSocketPort()
    {
        return this.serverSocketPort;
    }
    
    public int getActualServerSocketPort()
    {
        return ( serverSocket != null && serverSocket.isBound() ) ? serverSocket.getLocalPort() : serverSocketPort;
    }

    public void setServerSocketPort(int serverSocketPort)
    {
        this.serverSocketPort = serverSocketPort;
    }


    public synchronized void start()
    {
        if ( thread == null || !thread.isAlive() )
        {
            thread = new Thread(this);
            thread.start();
            
            Cron service = ServiceManager.d.getService(Cron.class);
            Scheduler schecduler = service.getComponent();
            schecduler.load(this);
        }
    }
    
    public void send(MESSAGE_TYPE messageType, byte[] message) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(messageType.getKey());
        dos.write(message);
        dos.flush();
        
        byte[] m = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(m, m.length,inetAddress,multicastPort);
        multicastSocket.send(packet);
    }
    
    protected void receive(DatagramPacket packet)
    {   
        try
        {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.getData()));
         
            Multicaster.MESSAGE_TYPE messageType = Multicaster.MESSAGE_TYPE.valueOf(dis.readUTF());
            Log.network.log(getClass(), packet.getAddress() + " " + messageType +  " " + packet.getLength());
            
            if ( messageType == Multicaster.MESSAGE_TYPE.HELLO )
            {
                savePeer(packet);
            }
            else if ( messageType == Multicaster.MESSAGE_TYPE.PING )
            {
                Peer peer = peers.get(packet.getSocketAddress().toString());
                if ( peer != null )
                {
                    peer.setLastPing(System.currentTimeMillis());
                }
            }
            else if ( packetProcessor != null )
            {
                packetProcessor.process(this,packet);
            }
        }
        catch ( Exception e )
        {
            Log.errors.log(getClass(), e);
        }
    }
    
    private void savePeer(DatagramPacket packet) throws IOException
    {
        if ( !packet.getAddress().getHostAddress().equals(InetAddress.getLocalHost().getHostAddress())  )
        {
            Peer peer = new Peer();
            
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.getData()));
            dis.readUTF(); //ignore commandname
            
            peer.setPort(dis.readInt());
            peer.setName(dis.readUTF());
            peer.setInetAddress(packet.getAddress());
            peer.setLastPing(System.currentTimeMillis());
            peer.refresh();
            
            peers.put(peer.getAddress().toString(),peer);
        }
    }
    
     
    protected void sendHello() throws IOException
    {
        ByteArrayOutputStream helloData = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(helloData);
        dos.writeInt(serverSocketPort);
        dos.writeUTF(getName());
        dos.flush();
   
        send(MESSAGE_TYPE.HELLO,helloData.toByteArray());
    }
    
    public void _execute()
    {
        try
        {
            sendHello();
            
            for (Peer peer : peers.values().toArray(new Peer[0]) )
            {
                if ( (System.currentTimeMillis() - peer.getLastPing()) < MAX_TTL )
                {
                    peers.remove(peer.getAddress());
                }
            }
        }
        catch (IOException e)
        {
            Log.errors.log(getClass(), e);
        }
    }

    public void run()
    {
        try
        {
            if ( multicastSocket == null )
            {
                multicastSocket = new MulticastSocket(multicastPort);
                multicastSocket.joinGroup(inetAddress);
            }
            
            Receiver receiver = new Receiver();
            receiver.multicastPeer = this;
            receiveThread = new Thread(receiver);
            receiveThread.start();
            
            //TODO: only reset if port has changed
            if ( serverSocket != null  )
            {
                if ( serverSocket.isBound() )
                {
                    serverSocket.close();              
                }
            }
            
            sendHello();
            
            serverSocket = new ServerSocket(serverSocketPort);
            shouldRun = true;
            
            Socket socket;
            while (  shouldRun && (socket = serverSocket.accept()) != null )
            {
                taskRunner.execute(new ProcessSocketJob(this,socket));    
            }
            
            serverSocket.close();
            multicastSocket.close();
        }
        catch (IOException e)
        {
            Log.errors.log(getClass(), e);
        }
    }
    
    public synchronized void stop()
    {
        shouldRun = false;
        Cron service = ServiceManager.d.getService(Cron.class);
        Scheduler schecduler = service.getComponent();        
        schecduler.unload(this);
    }
    
    public void kill() throws IOException
    {
        stop();
        
        if ( thread != null )
        {
            //thread.interrupt();
        }
        
        try
        {
            serverSocket.close();
        }
        finally
        {
            multicastSocket.close();
        }
    }
    
    protected void notifyComplete(ProcessSocketJob job)
    {
        Log.network.log(getClass(),job.toString());
    }
    
    class Receiver implements Runnable 
    {
        Multicaster multicastPeer;
        
        public void run()
        {
            byte[] buffer = new byte[4096];
            DatagramPacket packet;
            while ( multicastPeer.shouldRun )
            {
                try
                {
                    packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);
                    multicastPeer.taskRunner.execute(new ProcessPacketJob(this,packet));
                }
                catch (Exception e)
                {
                    Log.errors.log(getClass(),e);
                }
            }
        }
    }
    
    class ProcessPacketJob extends Task
    {
        DatagramPacket packet;
        Receiver receiver;
        
        ProcessPacketJob(Receiver receiver, DatagramPacket packet)
        {
            this.packet = packet;
            this.receiver = receiver;
        }
        
        public void _execute()
        {
            receiver.multicastPeer.receive(packet);  
        }
    }
    
    /*
    public static void main (String[] args)
    {
        Multicaster peer;
        try
        {
            peer = new Multicaster(InetAddress.getLocalHost().getHostName(),999,"228.5.6.7",6789);
            peer.start();
        }
        catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //System.exit(0);
    }*/
}