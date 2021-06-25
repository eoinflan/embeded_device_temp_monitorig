package EE402;

import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class EmptyClass implements Serializable {


	
	private int sampleNumber = 0;
	private String temp = "default";
	private String tempString = "tempString";
	private String timeStamp = "Server time....";
	private int sampleRate = 1; // default of 1 per second
	private String connectionID;

	
	private static final long serialVersionUID = 1L;
	
	Client Clientapp;
	
	public EmptyClass() {this.sampleNumber = sampleNumber;}
	
	public EmptyClass(Client Clientapp) {this.Clientapp = Clientapp;}

	public EmptyClass (int sampleRate) { this.sampleRate = sampleRate;}

	public int getSampleNumber() {return sampleNumber++;}
	
	public int setSampleRate(int setSampleRate) {System.out.println("sample rate has ben set to " + setSampleRate); sampleRate = setSampleRate; return sampleRate;}
	
	public String setConnectionID (String s) { connectionID = s; return connectionID;}
	
	
	public EmptyClass (String temp) {
	this.temp = temp;
	System.out.println("Empty Class Pi String called");}
		

	public EmptyClass(String time, String temp) {
		this.temp = temp;
		this.timeStamp = time;
	}
	
	public EmptyClass (int sampleNumber, String time, String temp, int sampleRate, String connectionID) {
		this.sampleNumber = sampleNumber;
		this.temp = temp;
		this.timeStamp = time;	
		this.sampleRate = sampleRate;
		this.connectionID = connectionID;
	}
	
	//public int getSampleRate () {return sampleRate;}
	public int getSampleRate () {return sampleRate;}


	public String time(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        System.out.println("_______________________________ time stamp = " + sdf.format(cal.getTime()));
        
  //      timeStamp =			sdf.format(cal.getTime()).toString();
        timeStamp = ((sdf.format(cal.getTime()).toString()));
        return timeStamp;       
	}
	
	public String getTempString() { // get temp 
		
		BufferedReader in = null;
		try {
			//in = new BufferedReader(new FileReader("/Users/eoinmachd/Desktop/FakeTempRead.txt"));
			in = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone0/temp"));

		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		try {tempString = in.readLine();}
		catch (IOException e) {e.printStackTrace();}
		temp = tempString;
		        System.out.println("_______________________________ current temp in milli degress  = " + temp);

		return temp;}

}
