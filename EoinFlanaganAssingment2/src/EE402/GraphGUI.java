package EE402;

import java.awt.*;

import java.awt.event.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Timer;
import java.util.Arrays;
import java.util.Collections;



import javax.print.DocFlavor.STRING;

import java.util.*;
import java.util.Calendar;


public class GraphGUI extends  Canvas implements MouseListener, AdjustmentListener, ActionListener, Serializable, Runnable { 
	private static final long serialVersionUID = 1L;
//	private int x;
	private EE402Increment app;
	private EmptyClassInterface ECIapp;
	private  float [] y = new float[5];
	private  float [] x = new float [5];
	private int [] sampleNumber = new int [5];
	private int sampleRate;
	private static int sampleRateInt =1;
	private static volatile boolean running = true;
	private static volatile boolean on = false;
	private static volatile boolean objRecieved;
	private static int firstRound  =0;
	private static  boolean fistLoopDone;
	private Graphics g;
    private Thread thread;
    
	private boolean firstbit;

    GraphGUI GraphGUI;
    
    private long threadId;
    private long tempthreadId;
    
    private int init =0;
//    private int max1;  private int min1;
//    private int max2;  private int min2;
//    private int max3;  private int min3;
//    private int max4;  private int min4;
//    private int max5;  private int min5;
    
    
	private boolean paintLine1 = false;		private boolean paintLine2 = false; 
	private boolean paintLine3 = false; 	private  boolean paintLine4 = false; 
	private boolean paintLine5 = false;

	private Vector<Integer> vTemp1 = new Vector<Integer>(20); 		private Vector<Integer> vTime1 = new Vector<Integer>(20); 
	private Vector<Integer> vTemp2 = new Vector<Integer>(20);		private Vector<Integer> vTime2 = new Vector<Integer>(20);
	private Vector<Integer> vTemp3 = new Vector<Integer>(20);		private Vector<Integer> vTime3 = new Vector<Integer>(20);
	private Vector<Integer> vTemp4 = new Vector<Integer>(20);		private Vector<Integer> vTime4 = new Vector<Integer>(20);
	private Vector<Integer> vTemp5 = new Vector<Integer>(20);		private Vector<Integer> vTime5 = new Vector<Integer>(20);

	
	private double [] data;
	private int [] [] XData = new int [5][20];
	private int [] [] YData = new int [5][20];
	
	
	private int [] XDataOld = new int [20];
	private int [] [] YDataOld = new int [5][20];
	private int [] xAcc = new int [5];
	private int previousNumberInArray;
	
	
	

	private int xOriginOffset = 20;
	private int yOriginOffset = 280;
		
	
	private static int test1, test2, test3, test4 = 250;
	
	
	private double Xtotal= 0;
	private double [] Ytotal = new double [5];
	private double [] YAve = new double [5];
	
	private int previousXValue ;
	private int previousYValue ;
	
	private static int currentNumberInArray = 0;
	private static int currentNumberInArray1 = 0;

	
	private  int numberOfThreads = 0;
	private int z;

	
	private String temp = "no temp recieved yet";
	private String time = "no time recieved yet";
	private EmptyClass EmptyClass;
	private ThreadedConnectionHandler TCH;
	private int setConnectionID;

	private String connectionID;
	
	

	public GraphGUI(EE402Increment app) {
		this.setPreferredSize(new Dimension (310,310));
	//	this.addMouseListener(this);
		this.app = app;
		this.setVisible(true);
       

	} 
	
	public void setPaintLine1(boolean paintLine1) {this.paintLine1 = paintLine1; System.out.println("connectionID  ============ 1 in set  " + this.paintLine1);}
	public void setPaintLine2(boolean paintLine2) {this.paintLine2 = paintLine2; System.out.println("connectionID  ============ 2 in set  " + this.paintLine2);}
	public void setPaintLine3(boolean paintLine3) {this.paintLine3 = paintLine3; System.out.println("connectionID  ============ 3 in set  " + this.paintLine3);}
	public void setPaintLine4(boolean paintLine4) {this.paintLine4 = paintLine4; System.out.println("connectionID  ============ 4 in set  " + this.paintLine4);}
	public void setPaintLine5(boolean paintLine5) {this.paintLine5 = paintLine5; System.out.println("connectionID  ============ 5 in set  " + this.paintLine5);}


//	
	

	
	
	public void setGraphGUI(int z) {
		this.z = z;
		
		test1 = test1 +1;
		
		test2 =  test2 -1; 
		
		test3 = test3 +1 ;
		
		test4 = test4 -1;
		
		this.repaint();

	}
	
	public void setTempGraph(int y) {
	//	this.y = y;
		if (paintLine1) {  this.y[0] = y; }
		if (paintLine2) {  this.y[1] = y; }

		if (paintLine1) {  this.vTemp1.add(y); if (vTemp1.size() == 20) { vTemp1.remove(0);}}
		if (paintLine2) {  this.vTemp2.add(y); if (vTemp2.size() == 20) { vTemp2.remove(0);}}
		if (paintLine3) {  this.vTemp3.add(y); if (vTemp3.size() == 20) { vTemp3.remove(0);}}
		if (paintLine4) {  this.vTemp4.add(y); if (vTemp4.size() == 20) { vTemp4.remove(0);}}
		if (paintLine5) {  this.vTemp5.add(y); if (vTemp5.size() == 20) { vTemp5.remove(0);}}

		
		this.repaint();
		
	}
	
	public void sendConnectionID(String sid) {
		
		this.connectionID = sid;
		
	}
	
	public void setSampleNuber(int x) {
	//	if (connectionID.equals("run1"))
		do {this.sampleNumber[0] = x; break;} while((paintLine1) & connectionID.equals("run1"));
		do {this.sampleNumber[1] = x; break;} while((paintLine2) & connectionID.equals("run2"));
		do {this.sampleNumber[2] = x; break;} while((paintLine3) & connectionID.equals("run3"));
		do {this.sampleNumber[3] = x; break;} while((paintLine4) & connectionID.equals("run4"));
		do {this.sampleNumber[4] = x; break;} while((paintLine5) & connectionID.equals("run5"));

						

		this.repaint();
	}
	
	public void setTimeGraph(int x){
	//	this.x = x;
		if (paintLine1) {  this.x[0] = x; }
		if (paintLine2) {  this.x[1] = x; }		
		
		if (paintLine1) {  this.vTime1.add(x); if (vTime1.size() == 20) { vTime1.remove(0);}}
		if (paintLine2) {  this.vTime2.add(x); if (vTime2.size() == 20) { vTime2.remove(0);}}
		if (paintLine3) {  this.vTime3.add(x); if (vTime3.size() == 20) { vTime3.remove(0);}}
		if (paintLine4) {  this.vTime4.add(x); if (vTime4.size() == 20) { vTime4.remove(0);}}
		if (paintLine5) {  this.vTime5.add(x); if (vTime5.size() == 20) { vTime5.remove(0);}}


		this.repaint();
	}
	
	
	public void paint(Graphics g) {
		System.out.println("paint was called");
		this.g=g;
	    super.paint(this.g); 		

		System.out.println(temp + " from connection handler");


		Font f1 = new Font ("TimesRoman", Font.PLAIN, 12);
		this.g.setFont(f1);
	
		this.g.drawRect(0, 0, 300, 300);
		
		
		this.g.setColor(Color.WHITE);
		this.g.fillRect(0, 0, 300, 300);
		
		this.g.setColor(Color.BLACK);

		this.g.drawLine(20, 280, 270, 280);
		this.g.drawLine(20, 280, 20, 20);
		this.g.drawString("Seconds", 140, 295);
		this.g.drawString("Pi CPU °C", 15, 15);
		
		this.g.setColor(Color.RED);
		this.g.drawLine(20, 40, 280, 40);
		Font f2 = new Font ("TimesRoman", Font.PLAIN, 8);
		this.g.setFont(f2);
		this.g.drawString("---MAX TEMP---", 120	,50);
		this.g.setColor(Color.BLACK);


	

		
		if(objRecieved = true) {
			
			int max1 = 0; int min1 = 0;
			int max2 = 0; int min2 = 0;
			int max3 = 0; int min3 = 0;
			int max4 = 0; int min4 = 0;
			int max5 = 0; int min5 = 0;
			
			
						
			//Ploting S1
			if(paintLine1) { 		System.out.println("in paint g paintline 1");		
				g.setColor(Color.BLUE);
				
				int x2 = 0;int y2 = 0;
				
				for(int i = 1 ; i<vTemp1.size() ; i++) {
					int y= this.vTemp1.elementAt(i);
					int yPrevious = this.vTemp1.elementAt(i-1);
					
					int x1 =  vTime1.elementAt(i-1) + 5;
					int y1 = yPrevious;
					
					 x2 =  this.vTime1.elementAt(i) + 5;
					 y2 =  y ;
													
						g.drawLine(xOriginOffset +x1,yOriginOffset - y1,xOriginOffset + x2, yOriginOffset -y2);						
				
						int maxTemp1 = Collections.max(vTemp1);
						String SmaxTemp1 = String.valueOf(maxTemp1);
						
						g.drawString(SmaxTemp1, xOriginOffset +5, (yOriginOffset - maxTemp1 +5) );
//						g.drawLine(xOriginOffset ,yOriginOffset - maxTemp1, 300 - xOriginOffset , yOriginOffset - maxTemp1);						
						
						Ytotal[0] = Ytotal[0] + vTemp1.elementAt(i);
						YAve [0] = Ytotal[0] / vTemp1.size();
								
						max1 = Collections.max(vTemp1); min1 = Collections.min(vTemp1);

				} 
				int gs = vTime1.size();
				String gs2 = String.valueOf(gs);
				g.drawString(gs2,xOriginOffset + x2,yOriginOffset - y2 - 10);	
				
				
				// displaying max and min.. repeated for each of the 5
				 if(max1 > max2 & max1 > max3 & max1 > max4 & max1 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max1, 300 - xOriginOffset , yOriginOffset - max1);	}

				 if(min1 > min2 & min1 > min3 & min1 > min4 & min1 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min1, 300 - xOriginOffset , yOriginOffset - min1);	}


			}
			
			
			//Ploting S2
			if(paintLine2) { 		System.out.println("in paint g paintline 2");		
				g.setColor(Color.GREEN);
				
				int x2 = 0;int y2 = 0;
				
				for(int i = 1 ; i<vTemp2.size() ; i++) {
					int y= this.vTemp2.elementAt(i);
					int yPrevious = this.vTemp2.elementAt(i-1);
					
					int x1 =  vTime2.elementAt(i-1) + 5;
					int y1 = yPrevious;
					
					 x2 =  this.vTime2.elementAt(i) + 5;
					 y2 =  y ;
													
						g.drawLine(xOriginOffset +x1,yOriginOffset - y1,xOriginOffset + x2, yOriginOffset -y2);	
						

						int maxTemp1 = Collections.max(vTime2);
						String SmaxTemp1 = String.valueOf(maxTemp1);
						
						g.drawString(SmaxTemp1, xOriginOffset +5, (yOriginOffset - maxTemp1 +5) );
//						g.drawLine(xOriginOffset ,yOriginOffset - maxTemp1, 300 - xOriginOffset , yOriginOffset - maxTemp1);						

						Ytotal [1] = Ytotal[1] + vTemp2.elementAt(i);
						YAve [1] = Ytotal[1] / vTemp2.size();
						
						 max2 = Collections.max(vTemp2);  min2 = Collections.min(vTemp2);

				} 
				int gs = vTime2.size();
				String gs2 = String.valueOf(gs);
				g.drawString(gs2,xOriginOffset + x2,yOriginOffset - y2 - 10);	
				

				 if(max2 > max1 & max2 > max3 & max2 > max4 & max2 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max2, 300 - xOriginOffset , yOriginOffset - max2);}

				 if(min2 > min1 & min2 > min3 & min2 > min4 & min2 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min2, 300 - xOriginOffset , yOriginOffset - min2);}

			}
			
			
			//Ploting S3
			if(paintLine3) { 		System.out.println("in paint g paintline 3");		
				g.setColor(Color.RED);
				
				int x2 = 0;int y2 = 0;
				
				for(int i = 1 ; i<vTemp3.size() ; i++) {
					int y= this.vTemp3.elementAt(i);
					int yPrevious = this.vTemp3.elementAt(i-1);
					
					int x1 =  vTime3.elementAt(i-1) + 5;
					int y1 = yPrevious;
					
					 x2 =  this.vTime3.elementAt(i) + 5;
					 y2 =  y ;
													
						g.drawLine(xOriginOffset +x1,yOriginOffset - y1,xOriginOffset + x2, yOriginOffset -y2);	
						

						int maxTemp1 = Collections.max(vTemp3);
						String SmaxTemp1 = String.valueOf(maxTemp1);
						
						g.drawString(SmaxTemp1, xOriginOffset +5, (yOriginOffset - maxTemp1 +5) );
//						g.drawLine(xOriginOffset ,yOriginOffset - maxTemp1, 300 - xOriginOffset , yOriginOffset - maxTemp1);						

						
						Ytotal[2] = Ytotal[2] + vTemp3.elementAt(i);
						YAve [2] = Ytotal[2] / vTemp3.size();
						
						 max3 = Collections.max(vTemp3);  min3 = Collections.min(vTemp3);

				} 
				int gs = vTime3.size();
				String gs2 = String.valueOf(gs);
				g.drawString(gs2,xOriginOffset + x2,yOriginOffset - y2 - 10);
				
				
				 if(max3 > max1 & max3 > max2 & max3 > max4 & max3 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max3, 300 - xOriginOffset , yOriginOffset - max3);}
				 
				 if(min3 > min1 & min3 > min2 & min3 > min4 & min3 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min3, 300 - xOriginOffset , yOriginOffset - min3);}


				
			}
			
			
			//Ploting S4
			if(paintLine4) { 		System.out.println("in paint g paintline 4");		
			g.setColor(new Color(255-102-50));
				
				int x2 = 0;int y2 = 0;
				
				for(int i = 1 ; i<vTemp4.size() ; i++) {
					int y= this.vTemp4.elementAt(i);
					int yPrevious = this.vTemp4.elementAt(i-1);
					
					int x1 =  vTime4.elementAt(i-1) + 5;
					int y1 = yPrevious;
					
					 x2 =  this.vTime4.elementAt(i) + 5;
					 y2 =  y ;
													
						g.drawLine(xOriginOffset +x1,yOriginOffset - y1,xOriginOffset + x2, yOriginOffset -y2);		
						

						int maxTemp1 = Collections.max(vTemp4);
						String SmaxTemp1 = String.valueOf(maxTemp1);
//						
						g.drawString(SmaxTemp1, xOriginOffset +5, (yOriginOffset - maxTemp1 +5) );
//						g.drawLine(xOriginOffset ,yOriginOffset - maxTemp1, 300 - xOriginOffset , yOriginOffset - maxTemp1);	
//						
						
						Ytotal[3] = Ytotal[3] + vTemp4.elementAt(i);
						YAve[3] = Ytotal[3] / vTemp4.size();

						 max4 = Collections.max(vTemp4);  min4 = Collections.min(vTemp4);
					
				} 
				int gs = vTime4.size();
				String gs2 = String.valueOf(gs);
				g.drawString(gs2,xOriginOffset + x2,yOriginOffset - y2 - 10);
				
				 if(max4 > max1 & max4 > max2 & max4 > max3 & max4 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max4, 300 - xOriginOffset , yOriginOffset - max4);}

				 if(min4 > min1 & min4 > min2 & min4 > min3 & min4 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min4, 300 - xOriginOffset , yOriginOffset - min4);}


				
			}
			
			//Ploting S4
			if(paintLine5) { 		System.out.println("in paint g paintline 5");	
				
				g.setColor(new Color(153-153-153));
				
				int x2 = 0;int y2 = 0;
				
				for(int i = 1 ; i<vTemp5.size() ; i++) {
					int y= this.vTemp5.elementAt(i);
					int yPrevious = this.vTemp5.elementAt(i-1);
					
					int x1 =  vTime5.elementAt(i-1) + 5;
					int y1 = yPrevious;
					
					 x2 =  this.vTime5.elementAt(i) + 5;
					 y2 =  y ;
													
						g.drawLine(xOriginOffset +x1,yOriginOffset - y1,xOriginOffset + x2, yOriginOffset -y2);						
					
						int maxTemp1 = Collections.max(vTemp5);
						String SmaxTemp1 = String.valueOf(maxTemp1);
						
						g.drawString(SmaxTemp1, xOriginOffset +5, (yOriginOffset - maxTemp1 +5) );

					
						Ytotal[4] = Ytotal[4] + vTemp5.elementAt(i);
						YAve[4] = Ytotal[4] / vTemp5.size();

						max5 = Collections.max(vTemp5);  min5 = Collections.min(vTemp5);

						
				} 
				int gs = vTime5.size();
				String gs2 = String.valueOf(gs);
				g.drawString(gs2,xOriginOffset + x2,yOriginOffset - y2 - 10);
				
				 if(max5 > max1 & max5 > max2 & max5 > max3 & max5 > max4 ) {g.drawLine(xOriginOffset ,yOriginOffset - max5, 300 - xOriginOffset , yOriginOffset - max5);}
			
				 if(min5 > min1 & min5 > min2 & min5 > min3 & min5 > min4 ) {g.drawLine(xOriginOffset ,yOriginOffset - min5, 300 - xOriginOffset , yOriginOffset - min5);}


				
			}
			
			
			 
//				//max lines
//			 if(max1 > max2 & max1 > max3 & max1 > max4 & max1 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max1, 300 - xOriginOffset , yOriginOffset - max1);	}
//			 
//			 if(max2 > max1 & max2 > max3 & max2 > max4 & max2 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max2, 300 - xOriginOffset , yOriginOffset - max2);}
//			 	
//			 if(max3 > max1 & max3 > max2 & max3 > max4 & max3 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max3, 300 - xOriginOffset , yOriginOffset - max3);}
//
//			 if(max4 > max1 & max4 > max2 & max4 > max3 & max4 > max5 ) {g.drawLine(xOriginOffset ,yOriginOffset - max4, 300 - xOriginOffset , yOriginOffset - max4);}
//			 
//			 if(max5 > max1 & max5 > max2 & max5 > max3 & max5 > max4 ) {g.drawLine(xOriginOffset ,yOriginOffset - max5, 300 - xOriginOffset , yOriginOffset - max5);}
//		     
//			 
//				this.g.setColor(Color.LIGHT_GRAY); 
//			 // min lines
//			 if(min1 > min2 & min1 > min3 & min1 > min4 & min1 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min1, 300 - xOriginOffset , yOriginOffset - min1);	}
//
//			 if(min2 > min1 & min2 > min3 & min2 > min4 & min2 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min2, 300 - xOriginOffset , yOriginOffset - min2);}
//			 
//			 if(min3 > min1 & min3 > min2 & min3 > min4 & min3 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min3, 300 - xOriginOffset , yOriginOffset - min3);}
//
//			 if(min4 > min1 & min4 > min2 & min4 > min3 & min4 > min5 ) {g.drawLine(xOriginOffset ,yOriginOffset - min4, 300 - xOriginOffset , yOriginOffset - min4);}
//			 
//			 if(min5 > min1 & min5 > min2 & min5 > min3 & min5 > min4 ) {g.drawLine(xOriginOffset ,yOriginOffset - min5, 300 - xOriginOffset , yOriginOffset - min5);}
//		     
//			 
	        // etc
//			this.g.setColor(Color.BLACK); //only doing this for 2 of 5 supplies.......
//			if (max1 > max2) {this.g.drawString(String.valueOf(max1), 10, 280 - max1);} else {this.g.drawString(String.valueOf(max2), 10, 280 - max2);}
//			if (min1 > min2) {this.g.drawString(String.valueOf(min1), 10, 280 - min1);} else {this.g.drawString(String.valueOf(min1), 10, 280 - min1);}

	
			if ((max1 | max2 | max3 | max4 | max5) >  90 ) { // if any max is over 90 degrees display message
				app.maxTemp(true);
				
			}
	
			
		}
	    		

		app.update(); //??????
		System.out.println(" END OF PAINT");
		
	}
	
	
	public GraphGUI() {EmptyClass EmptyClass = new EmptyClass(); this.EmptyClass = EmptyClass; }

		

	
	
	public boolean setObjRecieved(boolean yn) {
		this.objRecieved = yn;
		System.out.println("Object recieved in graph  = " + objRecieved);		
		return this.objRecieved;}
	
	public boolean getObjRecieved() {
	//	System.out.println("GETOBJREC = " + objRecieved);		

		return objRecieved;} 
	
	public boolean getObjRecieved(boolean yn) { yn = objRecieved; return yn;} 

	

	
	public int setSampleRate(int sampleRateInts) {
		System.out.println("sample rate setting test in GraphGUI " + sampleRateInts);
		this.sampleRateInt = sampleRateInts;
		return this.sampleRateInt;
						
		}
	
	public int getSampleRate() { 
	//	String sampleRate = this.app.sampleRateTxt.getText();		
		return sampleRateInt;}
		

	
	
	public double displayAve1() {
		double temp0 = YAve[0];
		return temp0;	
	}
	public double displayAve2() {
		double temp1 = YAve[1];
		return temp1;	
	}
	public double displayAve3() {
		double temp2 = YAve[2];
		return temp2;	
	}
	public double displayAve4() {
		double temp3 = YAve[3];
		return temp3;	
	}
	public double displayAve5() {
		double temp4 = YAve[4];
		return temp4;	
	}
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("The mouse was clicked on the canvas");
		this.app.increment();
		this.app.update();
		
		// TODO Auto-generated method stub
		
	}
	
    public void update(Graphics g) {
        paint(g);
    }

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		
	}



	


	@Override
	public void run() { on = true; numberOfThreads++;

		
	
	}
	
	
	



	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
		

} 