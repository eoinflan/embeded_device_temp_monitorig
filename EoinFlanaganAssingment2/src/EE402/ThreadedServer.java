package EE402;



import java.net.*;
import java.util.Vector;
import java.io.*;

public class ThreadedServer 
{
	private static int portNumber = 5050;
	private GraphGUI myGraphGUI;
	Thread newThread;
	private Q5 Q5;
	

	
	public ThreadedServer(EE402.Q5 Q5) {
       // new Q5( );
		this.Q5 = Q5;

		boolean listening = true;
        ServerSocket serverSocket = null;
        try 
        {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("New Server has started listening on port: " + portNumber );
        } 
        catch (IOException e) 
        {
            System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
            System.exit(1);
        }
        
        // Server is now listening for connections or would not get to this point
        while (listening) // almost infinite loop - loop once for each client request
        {
            Socket clientSocket = null;
            try{
            	System.out.println("**. Listening for a connection...");
                clientSocket = serverSocket.accept();
                System.out.println("00. <- Accepted socket connection from a client: ");
                System.out.println("    <- with address: " + clientSocket.getInetAddress().toString());
                System.out.println("    <- and port number: " + clientSocket.getPort());
            } 
            catch (IOException e){
                System.out.println("XX. Accept failed: " + portNumber + e);
                listening = false;   // end the loop - stop listening for further client requests
            }	
           // this.Q5 = new Q5();
            ThreadedConnectionHandler con = new ThreadedConnectionHandler(clientSocket,  this.Q5);
            con.start(); 
            System.out.println("02. -- Finished communicating with client:" + clientSocket.getInetAddress().toString());
        }
        // Server is no longer listening for client connections - time to shut down.
        try 
        {
            System.out.println("04. -- Closing down the server socket gracefully.");
            serverSocket.close();
        } 
        catch (IOException e) 
        {
            System.err.println("XX. Could not close server socket. " + e.getMessage());
        }
    
	}


}