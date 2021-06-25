package EE402;
import java.net.*;
import java.util.*;
import java.io.*;

public class ThreadedConnectionHandler extends Thread implements Serializable, Runnable
{
    /**
	 */
	private static final long serialVersionUID = 1L;
	private static final Vector<EE402.Q5> Vector = null;
	
	private Socket clientSocket = null;				// Client socket object
    private ObjectInputStream is = null;			// Input stream
    private ObjectOutputStream os = null;			// Output stream
    private DateTimeService theDateService;
    private GraphGUI GraphGUI;
    private Q5 Q5;
    private EmptyClass EmptyClass = new EmptyClass();
	private Thread ThreadGUI;
	private Thread threadGraph; 
	
	private long connectionID;

	private Q5toCH app;
	
	private int numbI=0;

    
    
    private boolean running = true, paused = false;
    private Thread thread;
    private volatile boolean  objectRecieved;

        
	// The constructor for the connection handler
    public ThreadedConnectionHandler(Socket clientSocket, EE402.Q5 Q5) {
        this.clientSocket = clientSocket;
        //Set up a service object to get the current date and time
        theDateService = new DateTimeService();
          this.Q5 = Q5;
          threadGraph = new Thread (this.GraphGUI);
          threadGraph.start();
  
    }
    
    

    // Will eventually be the thread execution method - can't pass the exception back
    public void run() {   
    //	for(;numbI <= 5;) {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
         //   this.GraphGUI =  new GraphGUI();
        //    this.Q5 = super Q5();
           // this.Q5 = new Q5();


            while (this.readCommand()) {}
         } 
         catch (IOException e) 
         {
        	System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }  
    //	}
    }

    // Receive and process incoming string commands from client socket 
    private boolean readCommand() {
    	
    		this.objectRecieved = false;
    		
    		Q5.setState(false);
            Q5.objRecieved(false);

        try {
        	
            EmptyClass = (EmptyClass)  is.readObject();
            
            System.out.println(" recieveing object " + EmptyClass.getSampleRate() + ".");
            System.out.println("Temp is " + EmptyClass.getTempString());

        } 
        catch (Exception e){  
                this.closeSocket();  
                System.out.println("About to close sockets");
            return false;
        }

        
        
        Q5.recieveObj(EmptyClass.getSampleNumber(), EmptyClass.time(), EmptyClass.getTempString(), EmptyClass.getSampleRate(), EmptyClass.getConnectionID());
      //  Q5.recieveObj3(EmptyClass);
        Q5.objRecieved(false);


        
        int a = Q5.getSampleRate();
        int b = EmptyClass.getSampleRate();
        
        if (a != b) 	
        {
        	EmptyClass.setSampleRate(a);
        	System.out.println("Sample rate has been set to " + EmptyClass.getSampleRate());
        }
        
        Q5.objRecieved(false); 
    	send(EmptyClass);


        System.out.println("01. <- Received an object from the client");// (" + EmptyClass.toString() + " - " + EmptyClass.getTempString() + ").");
     
        
        
        try {
        		int x =  Q5.getSampleRate();
     			Thread.sleep((0));
     		} catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
            	
    	
        return true; 
        
        
    }
    
    public void sendObj(int SampleNumber, String Time2, String temp, int sampleRate) {    	
    }
    


	// Use our custom DateTimeService Class to get the date and time
    private void getDate() {	// use the date service to get the date
        String currentDateTimeText = theDateService.getDateAndTime();
        this.send(currentDateTimeText);
    }

    // Send a generic object back to the client 
    private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }
    
    // Send a pre-formatted error message to the client 
    public void sendError(String message) { 
        this.send("Error:" + message);	//remember a String IS-A Object!
    }
    
    // Close the client socket 
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }



}