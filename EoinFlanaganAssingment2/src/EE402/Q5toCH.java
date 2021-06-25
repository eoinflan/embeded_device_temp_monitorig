package EE402;

import java.awt.event.WindowEvent;

public interface Q5toCH {
	
	//public int setSampleRate(int x);
	 public boolean windowClosing();
	 
	 public void recieveObj(boolean f);

	 public void recieveObj3();
	 
	 public boolean setState(boolean s);

	public boolean objRecieved(boolean b);

	public void recieveObj(Integer sampleNumber, String time, String tempString, int sampleRate);

	public void recieveObj3(EmptyClass emptyClass);
	 
}
