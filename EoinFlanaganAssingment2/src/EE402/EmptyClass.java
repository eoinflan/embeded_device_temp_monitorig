package EE402;

import java.io.*;
import java.text.SimpleDateFormat;


import java.net.*;

public class EmptyClass implements Serializable {


	private int sampleNumber;
	private String temp;
	private String timeStamp = "Server timeStamp";
	private int sampleRate = 0;
	private String connectionID = "0";



	
	private static final long serialVersionUID = 1L;

//	public EmptyClass () {System.out.println("Empty Class Server called");}
	public EmptyClass () {}
	public EmptyClass (int SampleNumber, String time, String temp, int sampleRate, int connectionID) {}
	
	public String setConnectionID (String s) { connectionID = s; return connectionID;}
	public String getConnectionID () {return connectionID;}

	

	public int getSampleRate () {return sampleRate;}
	
	public int setSampleRate(int setSampleRate) {System.out.println("sample rate has ben set to " + setSampleRate); sampleRate = setSampleRate; return sampleRate;}

	public Integer getSampleNumber() {return sampleNumber;}

	public String getTempString() {return temp;}

	public String doSomthing (String tempa) {return this.temp;}
	
	public String time() { System.out.println("timestamp called - shoould return time " +  timeStamp);
		return timeStamp;}

		
		
	
	
	
	
}
