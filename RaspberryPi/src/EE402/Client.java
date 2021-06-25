package EE402;
//import ee402.*;

/* The Client Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 * 
 * 
 */

import java.net.*;
import java.io.*;

public class Client extends Thread implements TempClientInterface, Serializable, EmptyClassServerInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int portNumber = 5050;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private String temp;
	static Client Clientapp;
    private boolean running = true, paused = false;
    private Thread thread;
    private EmptyClass EmptyClass1;
    
	private static String connectionID;

    private int y;



	// the constructor expects the IP address of the server - the port is fixed
    public Client(String serverIP, String Temp1of5) {
    	if (!connectToServer(serverIP)) {
    		System.out.println("XX. Failed to open socket connection to: " + serverIP);            
    	}

		//this.Clientapp =  (this);
        this.thread = new Thread(this);
       
//				
    }
    

    
    

    private boolean connectToServer(String serverIP) {
    	try { // open a new socket to the server 
    		this.socket = new Socket(serverIP,portNumber);

    		this.os = new ObjectOutputStream(this.socket.getOutputStream());
    		this.is = new ObjectInputStream(this.socket.getInputStream());
    		System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
    				+ " on port: " + this.socket.getPort());
    		System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
    				+ " and port: " + this.socket.getLocalPort());
    	} 
        catch (Exception e) {
        	System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
        	System.out.println("    Exception: " + e.toString());	
        	return false;
        }
		return true;
    }
    

    

    private void RunApp() {
       	
       	int sn = 0;
       	
       	EmptyClass EmptyClass1 = new EmptyClass();       	
       	this.EmptyClass1 = new EmptyClass(); 
					    
    	while(running) {
  	
    	int orginalSampleRate = EmptyClass1.getSampleRate();
  		y =  EmptyClass1.getSampleRate();
	    
  		EmptyClass1.getSampleNumber(); EmptyClass1.time(); EmptyClass1.getTempString(); EmptyClass1.getSampleRate();EmptyClass1.setConnectionID(connectionID);
		System.out.println("EmptyClass1.getSampleNumber()");

	    send(EmptyClass1);    	
	    System.out.println("01. -> Sending Command (" + EmptyClass1 + ") to the server...");
	    
		try {
			Thread.sleep(y*2 * 1000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    try{
	    	EmptyClass1 = receive();
    		System.out.println("05. <- The Server responded with: " + EmptyClass1.getSampleRate());
    		System.out.println("    <- " );
    		EmptyClass1.setSampleRate(EmptyClass1.getSampleRate());
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}
    	System.out.println("06. -- Disconnected from Server.");
    	
    	}
    	
		try {
			Thread.sleep(y* 2 * 1000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
    }
    
    public void stopCount() { this.running = false; }

	
    // method to send a generic object.
    private void send(Object o) {
    //	o = ( "" + o);
			try {
				System.out.println(o);
			    System.out.println("02. -> Sending an object... " + o);
			    os.writeObject(o);
			    os.flush();
			    
			} 
		    catch (Exception e) {
			    System.out.println("XX. Exception Occurred on Sending:" + o + " -- " + e.toString());
			    this.stopCount();
			   // System.out.println(o);
			}

    	
    }

    // method to receive a generic object.
    private EmptyClass receive() 
    {
    	EmptyClass o = null;
        EmptyClass EmptyClass = new EmptyClass();
		try {
			System.out.println("03. -- About to receive an object...");
		 //   o = (EmptyClass) is.readObject();
            EmptyClass =  (EE402.EmptyClass) is.readObject();
		    System.out.println("04. <- Object received..." );
		    System.out.println("04. <- Object received..." + EmptyClass.getSampleRate());
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		return EmptyClass;
    }

    public static void main(String args[]) 
    {
    	System.out.println("**. Java Client Application - EE402 OOP Module, DCU");

    	if(args.length==2){
    		Client theApp = new Client(args[0], args[1]);
    		
    		connectionID=args[1];
		
   		    theApp.RunApp();
    	
    	}

	
    	else
    	{
    		System.out.println("Error: you must provide the address of the server");
    		System.out.println("Usage is:  java Client x.x.x.x  (e.g. java Client 192.168.7.2)");
    		System.out.println("      or:  java Client hostname (e.g. java Client localhost)");

    	}    
    	System.out.println("**. End of Application.");
    }

	@Override
	public String getTempString() {
		// TODO Auto-generated method stub
		return temp;
	}


}
