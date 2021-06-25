package EE402;


public interface EE402Increment {
	
	public void setSampleRateQ5(int sampleRateInt);

	public void increment();
	//public void update();
//	public String sendSampleRate();

	public void update();

	public int getSampleRateQ5();

	public String getConnectionID();

	public boolean maxTemp(boolean b);
}