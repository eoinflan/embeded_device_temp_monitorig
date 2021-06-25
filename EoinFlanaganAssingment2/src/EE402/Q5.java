package EE402;





import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;



@SuppressWarnings("serial")
public class Q5 extends JFrame implements Serializable ,  AdjustmentListener, ActionListener, GGUIServerInterface, EE402Increment, EmptyClassInterface, Runnable
{


	private static int y;
	private static int x;
	private int sampleNumber = 0;
	private static int sampleRateInt =1;
	private static volatile boolean running = true;
	private static volatile boolean on = false;
	private static volatile boolean objRecieved;
	private int currentNumberInArray;
	private static int firstRound  =0;
	private static  boolean fistLoopDone;
	
	private volatile boolean paintLine1 = false; 	private volatile boolean paintLine2 = false; 
	private volatile boolean paintLine3 = false; 	private volatile boolean paintLine4 = false; 
	private volatile boolean paintLine5 = false;
 


	private boolean maxTemp;
	
	private Color offRed;

	
	private Scrollbar scrollbar;
	private IntTextField status;
	private Button resetButton;

  	private GraphGUI GGUI; 
  //	private GraphGUI GGUI2; 
  	
	private JButton s1State; 	private boolean temp1bit = true;
	private JButton s2State; 	private boolean temp2bit = true;	
	private JButton s3State; 	private boolean temp3bit = true;
	private JButton s4State; 	private boolean temp4bit = true;
	private JButton s5State; 	private boolean temp5bit = true;
	
	private Label sampleRateLbl;
	private IntTextField sampleRateTxt;
	private String tempReading;
	private Button sampleButton;
	private int sampleRateInt2 =1;

	
	private Label s1AverageLbl;
	private Label s2AverageLbl;
	private Label s3AverageLbl;
	private Label s4AverageLbl;
	private Label s5AverageLbl;
	
	private Label maxTemplbl;
	
	private String s1Average;
	private String s2Average;
	private String s3Average;
	private String s4Average;
	private String s5Average;

	
	private EmptyClassInterface ECIapp;
	private String temp;
	
	private EmptyClass EmptyClass;
	private String time;
	private String connectionID;
	
	//private int on;

	public Q5(){
		super("Assignment 2 Example");
		this.setLayout(new FlowLayout());
		Panel topPanel = new Panel();
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		
	//	if (setDefaultCloseOperation = true) {setOnOff
		
		
		this.status = new IntTextField(20);
		this.scrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 100, 10, 0, 110);
		this.resetButton = new Button("Reset");
		
		
	    offRed = new Color(128, 0, 0);
	    this.maxTemplbl = new Label("MAX TEMP REACHED");
	    maxTemplbl.setBackground(offRed);

		this.s1State = new JButton("Temp 1 On/Off");
		this.s1State.addActionListener(this);
		this.s2State = new JButton("Temp 2 On/Off");
		this.s2State.addActionListener(this);
		this.s3State = new JButton("Temp 3 On/Off");
		this.s3State.addActionListener(this);
		this.s4State = new JButton("Temp 4 On/Off");
		this.s4State.addActionListener(this);
		this.s5State = new JButton("Temp 5 On/Off");
		this.s5State.addActionListener(this);
		
		this.getContentPane().add(s1State);
		this.getContentPane().add(s2State);
		this.getContentPane().add(s3State);
		this.getContentPane().add(s4State);
		this.getContentPane().add(s5State); 		
		this.s1AverageLbl = new Label("S1 Average = " + "  " + s1Average );
		this.s2AverageLbl = new Label("S2 Average = " + "  " + s2Average );
		this.s3AverageLbl = new Label("S3 Average = " + "  " + s3Average);
		this.s4AverageLbl = new Label("S4 Average = " + "  " + s4Average );
		this.s5AverageLbl = new Label("S5 Average = " + "  " + s5Average );
		this.add(s1AverageLbl);
		this.add(s2AverageLbl);
		this.add(s3AverageLbl);
		this.add(s4AverageLbl);
		this.add(s5AverageLbl);

		
		
		this.GGUI = new GraphGUI(this);

	    this.add(GGUI, BorderLayout.CENTER);


		this.sampleRateTxt = new IntTextField(10);
		this.sampleRateLbl = new Label("Enter Sample Rate: ");
		this.sampleButton = new Button("Enter");
		this.sampleButton.addActionListener(this);
		


		this.resetButton.addActionListener(this);
		this.scrollbar.addAdjustmentListener(this);

		topPanel.add(status);
		topPanel.add(resetButton);

		this.add(topPanel);
		//this.add(scrollbar);
		
		this.add(sampleRateLbl);
		this.add(sampleRateTxt);
		this.add(sampleButton);

//		sampleRate =  Integer.valueOf(sampleRateTxt.getSelectedText());
//		System.out.println(sampleRate);
			
		
   		if (maxTemp) {
		this.add(maxTemplbl);
		}

		this.update();
		this.pack();
		this.setBounds(500, 500, 500, 510);
		this.setResizable(false);

		this.setVisible(true);
		new ThreadedServer(this);

	}
	
	
	public void increment() {
		this.scrollbar.setValue(scrollbar.getValue()+10);
		this.update();
	}

		
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("Reset")){
			this.scrollbar.setValue(0);
			this.update();				
		}
		
		
		if (e.getActionCommand().equals("Enter")){
			
		String sampleRate = this.sampleRateTxt.getText();
	//	@SuppressWarnings("deprecation")
		this.sampleRateInt = Integer.valueOf(sampleRate);
		this.sampleRateInt2 = sampleRateInt;
		

		this.setSampleRateQ5(sampleRateInt);
						
		}
		
		if (e.getActionCommand().equals("Temp 1 On/Off")){ 
			if(temp1bit == true) {temp1bit = false; GGUI.setPaintLine1(temp1bit); }			
			else if (temp1bit == false) {temp1bit = true; GGUI.setPaintLine1(temp1bit);}
			System.out.println("S1 has been turned - " + temp1bit);	
			this.update();
		}

		if (e.getActionCommand().equals("Temp 2 On/Off")){ 
			if(temp2bit == true) {temp2bit = false; GGUI.setPaintLine1(temp2bit); } 
			else if (temp2bit == false) {temp2bit = true; GGUI.setPaintLine2(temp2bit);}
			System.out.println("S2 has been turned - " + temp2bit);		
			this.update();
		}
		if (e.getActionCommand().equals("Temp 3 On/Off")){ 
			if(temp3bit == true) {temp3bit = false; GGUI.setPaintLine1(temp3bit); } 
			else if (temp3bit == false) {temp3bit = true; GGUI.setPaintLine3(temp3bit);}
			System.out.println("S3 has been turned - " + temp3bit);		
			this.update();
		}
		if (e.getActionCommand().equals("Temp 4 On/Off")){ 
			if(temp4bit == true) {temp4bit = false; GGUI.setPaintLine1(temp4bit); } 
			else if (temp4bit == false) {temp4bit = true; GGUI.setPaintLine4(temp4bit);}
			System.out.println("S4 has been turned - " + temp4bit);		
			this.update();
		}
		if (e.getActionCommand().equals("Temp 5 On/Off")){ 
			if(temp5bit = true) {temp5bit = false; GGUI.setPaintLine1(temp5bit); } 
			else if (temp5bit == false) {temp5bit = true; GGUI.setPaintLine4(temp5bit);}
			System.out.println("S4 has been turned - " + temp5bit);		
			this.update();
		}
		
	
		
	}
	
	public int getSampleRateQ5(int sampleRateInt) {return this.sampleRateInt;}
	
	
	public void setSampleRateQ5(int sampleRateInt) {
		this.sampleRateInt = sampleRateInt;
		GGUI.setSampleRate(sampleRateInt);
		System.out.println("Q5 set SampleRate is " + sampleRateInt);
	}
	
	@Override
	public  int getSampleRateQ5() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	public void adjustmentValueChanged(AdjustmentEvent e){
		if (e.getSource().equals(scrollbar)){
			this.GGUI.setGraphGUI(scrollbar.getValue());
			this.update();
			
		}	
	}
	


	public void update(){
		status.setText("Scroll Value = " + scrollbar.getValue());
		
		
   		if (maxTemp) {
		this.add(maxTemplbl);
		}
	
	}
	



	public String recieveTemp(String st) {
		tempReading = st;
		this.update();
		System.out.println(st);
		
		return tempReading;
	}

	@Override
	public String getTempString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doSomthing(String tempString) {
		// TODO Auto-generated method stub
		return null;
	}




    
	
//	public int setSampleRate(int sampleRateInts) {
//		System.out.println("sample rate setting test in Q5 " + sampleRateInts);
//		this.sampleRateInt = sampleRateInts;
//		return this.sampleRateInt;				
//		}
	

    
	public int getSampleRate() { 
	//	String sampleRate = this.app.sampleRateTxt.getText();		
		return sampleRateInt;}
    
    public boolean objRecieved(boolean b) {return objRecieved;}


    public boolean setState(boolean b) {return on;}
    
    
	@Override
	public void run() { 
    	System.out.println(" thread runing in Q5 " );
		while(on) {
		    
	
			
			
		}
		// TODO Auto-generated method stub
	
	}
	
	public boolean maxTemp(boolean maxT)  { this.maxTemp = maxT;
	
	return maxTemp; }

	
	
	public float setGraphX(float x) {return this.x;
	}
	
	public float setGraphY(float y) {return this.y;}
	
	public int setSampleNumber (int sampleNumber) {return this.sampleNumber;}
	
	public  int setSampleRate (int sampleRate) {return this.sampleRateInt;}
	
	//public void recieveObj3(EmptyClass EmptyClass) {
	
	//this.update();
	//}
	
	public void recieveObj2(EmptyClass EmptyClass) {
	}
	
	public void recieveObj(int sampleNumber, String time, String tempa, int sampleRate, String connectionID2) {
		
		this.sampleNumber = sampleNumber;
		this.temp = tempa;
		this.time = time;
		this.sampleRateInt = sampleRate;
		this.connectionID = connectionID2;
		objRecieved = true;
		
		
		System.out.println("Recieved time object " + time + " & and a temp of " + temp  + "displayed as strings");	
		System.out.println(" and a sample number " + this.sampleNumber);
		System.out.println(" and a sample rate of " + this.sampleRateInt + " samples per second");

		System.out.println("connectionID  ===================== in Q5 " + connectionID);
		if (connectionID == "run1") {paintLine1 = true; this.GGUI.setPaintLine1( paintLine1);}

		// temp values to change slightly, will be removing the math.rabdom when using pi temp	
		y = (Integer.valueOf(temp));
		float y1 = (float) ((y /1000));
		
		y = Math.round(y1);

		x = (Integer.valueOf(this.time));
		
		
		System.out.println("Recieved time object " + x + " & and a temp of " + y + " displayed as floats");	
		
		System.out.println(" CURRENT NUMBER IN ARRAY IN RECIEVE OBJ IS "  + currentNumberInArray);

		System.out.println("objRecieved  ===================== " + objRecieved);

		objRecieved();
		

		this.update();


	}

	
	public void objRecieved(){
		
		if (objRecieved = true){
			GGUI.setObjRecieved(objRecieved);
			System.out.println("connectionID  ===================== " + connectionID);
			
			
			if (connectionID.equals("run1") & temp1bit) { 
			this.GGUI.sendConnectionID(connectionID);
			System.out.println("S1 has been turned at objecrecied - " + temp1bit);			
			paintLine1 = true; this.GGUI.setPaintLine1( paintLine1);
			this.GGUI.setTempGraph(y);
			this.GGUI.setTimeGraph(x);
			this.GGUI.setSampleNuber(sampleNumber);
			s1Average = ("S1 Average = " + String.valueOf(GGUI.displayAve1()));
			s1AverageLbl.setText(s1Average);
			System.out.println("os1Averaged " + s1Average);
			this.update();
			if (maxTemp) {
	   			this.add(maxTemplbl);
	   			}
			}
			
			
			if (connectionID.equals("run2") & temp2bit) { 
			this.GGUI.sendConnectionID(connectionID);
			System.out.println("S2 has been turned at objecrecied - " + temp2bit);					
			paintLine2 = true; this.GGUI.setPaintLine2( paintLine2);
			this.GGUI.setTempGraph(y);
			this.GGUI.setTimeGraph(x);
			this.GGUI.setSampleNuber(sampleNumber);
			s2Average = ("S2 Average = " + String.valueOf(GGUI.displayAve2()));			
			s2AverageLbl.setText(s2Average);
			this.update();	
			if (maxTemp) {
	   			this.add(maxTemplbl);
	   			}
			
			//ect for 5 
			}
			
			if (connectionID.equals("run3") & temp3bit) { 
			this.GGUI.sendConnectionID(connectionID);
			System.out.println("S3 has been turned at objecrecied - " + temp3bit);					
			paintLine3 = true; this.GGUI.setPaintLine3( paintLine3);
			this.GGUI.setTempGraph(y);
			this.GGUI.setTimeGraph(x);
			this.GGUI.setSampleNuber(sampleNumber);
			s3Average = ("S3 Average = " + String.valueOf(GGUI.displayAve3()));			
			s3AverageLbl.setText(s3Average);
			if (maxTemp) {
	   			this.add(maxTemplbl);
	   			}

			this.update();
			
			}
			
			if (connectionID.equals("run4") & temp4bit) { 
			this.GGUI.sendConnectionID(connectionID);
			System.out.println("S4 has been turned at objecrecied - " + temp4bit);					
			paintLine4 = true; this.GGUI.setPaintLine4( paintLine4);
			this.GGUI.setTempGraph(y);
			this.GGUI.setTimeGraph(x);
			this.GGUI.setSampleNuber(sampleNumber);
			s4Average = ("S4 Average = " + String.valueOf(GGUI.displayAve4()));			
			s4AverageLbl.setText(s4Average);
			if (maxTemp) {
	   			this.add(maxTemplbl);
	   			}
			

			this.update();
			}
			
			if (connectionID.equals("run5") & temp5bit) { 
			this.GGUI.sendConnectionID(connectionID);
			System.out.println("S5 has been turned at objecrecied - " + temp5bit);					
			paintLine5 = true; this.GGUI.setPaintLine5( paintLine5);
			this.GGUI.setTempGraph(y);
			this.GGUI.setTimeGraph(x);
			this.GGUI.setSampleNuber(sampleNumber);
			s5Average = ("S5 Average = " + String.valueOf(GGUI.displayAve5()));			
			s5AverageLbl.setText(s5Average);
	   		if (maxTemp) {
	   			this.add(maxTemplbl);
	   			}

			this.update();
			}
			
			
			
		}

			
	}
	
	
	
	
	public static void main(String args[]) {
		
		new Q5();
	}


	public  String getConnectionID() {
		// TODO Auto-generated method stub
		return connectionID;
	}



}