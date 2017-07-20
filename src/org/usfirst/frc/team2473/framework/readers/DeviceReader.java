package org.usfirst.frc.team2473.framework.readers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.threading.StringSupplier;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;

/**
 * This class scans all values from existing <code>Trackers</code> of devices and maps them to a <code>Database</code>.
 * The process occurs periodically.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class DeviceReader extends Thread {
	private volatile boolean running; //stores whether the process is running or not
	private int period; //duration of 1 iteration of the looping process
	
	Map<String, DoubleSupplier> numericalCalls; //stores all getter functions returning numerical values being tracked
	Map<String, BooleanSupplier> conditionalCalls; //stores all getter functions returning boolean values being tracked
	Map<String, StringSupplier> messageCalls; //stores all getter functions returning String values being tracked
	
	Map<String, Double> numericalSnapshot; //instantaneously stores all numerical values returned from supplier functions stored in numericalCalls
	Map<String, Boolean> conditionalSnapshot; //instantaneously stores all boolean values returned from supplier functions stored in conditionalCalls
	Map<String, String> messageSnapshot; //instantaneously stores all String values returned from supplier functions stored in messageCalls

	/**
	 * Default constructor, loop iteration is 5 milliseconds.
	 */
	public DeviceReader() {
		init(5);
	}
	
	/**
	 * Constructor allowing for setting of loop iteration for the scanning and mapping process
	 * @param milis <code>int</code> value representing the number of milliseconds between iterations of the process
	 */
	public DeviceReader(int milis) {
		init(milis);
	}

	/*The following method is called in all constructors; the only difference is how many milliseconds are passed.
	 * Some of the constructor code was written in a lazy manner, and this is one such example.
	 * */
	private void init(int milis) {
		period = milis;
		running = true;

		numericalCalls = new HashMap<>();
		conditionalCalls = new HashMap<>();
		messageCalls = new HashMap<>();
		
		assignCalls();
		
		numericalSnapshot = new HashMap<>();
		conditionalSnapshot = new HashMap<>();
		messageSnapshot = new HashMap<>();		
		
		Trackers.getInstance().resetEncoders();
		Trackers.getInstance().resetGyro();
	}
	
	/**
	 * The method of execution for this Thread, just like all Threads
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#run--"><code>Thread</code></a>
	 */
	@Override
	public void run() {
		while(running) { //what runs in the thread is run as a repeated loop
			//the actual stuff that happens in this thread is in trackValues(), which runs repeatedly due to the while loop
			trackValues();

			//make the thread go to sleep for the given number of millseconds period, an instance variable of DeviceReader
			try {
				Thread.sleep(period);				
			} catch(InterruptedException e) { //throw an InterruptedException should there be an error in the sleep process
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * Kills the DeviceReader process
	 */
	public void kill() {
		running = false;	 //setting running to false will stop the loop in run()	
	}
	
	/**
	 * Returns the termination status of the DeviceReader process.
	 * @return a <code>boolean</code> value representing the whether or not the DeviceReader process is terminated or not.
	 */
	public boolean isDead() {
		return !running; //returning the opposite of running will return whether or not it is dead or "not running"
	}
	
	/* fills the "calls" maps with the getter suppliers set in the trackers
	 * is called only once in the constructor through the init method
	 * */
	private void assignCalls() {
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) //loop through all trackers added to Trackers by the programmer
			switch(tracker.getType()) {
			case NUMERIC: //if it is NUMERIC in type, add the tracker's supplier to numericalCalls with the same key as the tracker
				numericalCalls.put(tracker.getKey(), tracker.getterNumeric());
				break;
			case CONDITIONAL: //if it is CONDITIONAL in type, add the tracker's supplier to conditionalCalls with the same key as the tracker
				conditionalCalls.put(tracker.getKey(), tracker.getterConditional());
				break;
			case MESSAGE: //if it is MESSAGE in type, add the tracker's supplier to messageCalls with the same key as the tracker
				messageCalls.put(tracker.getKey(), tracker.getterMessage());
				break;
			default: //by default do nothing
				break;
			}		
	}
	
	/* takes the value suppliers from the "call" maps and stores a snapshot of the values in the "snapshot" maps
	 * the snapshot maps are then mapped to Database for storage, with the same keys as assigned
	 * in short, trackValues uses the programmer assigned suppliers to update Database with the correct values with the correct keys 
	 */
	private void trackValues() {
		/* Process is same for the next 3 lines of code:
		 * for every existing key in the "call" supplier map, take the supplier it refers to, 
		 * get its value and store it under the same key onto the value holding "snapshot" map
		 * */
		for(String key : numericalCalls.keySet()) numericalSnapshot.put(key, numericalCalls.get(key).getAsDouble());
		for(String key : conditionalCalls.keySet()) conditionalSnapshot.put(key, conditionalCalls.get(key).getAsBoolean());
		for(String key : messageCalls.keySet()) messageSnapshot.put(key, messageCalls.get(key).getAsString());

		/* Process is same for next 3 lines of code:
		 * for every existing key in the "snapshot" value holding map, take the value it refers to,
		 * and store it under the same key onto  
		 * Database based on its data type(numerical, conditional, message) 
		 */
		for(String key : numericalSnapshot.keySet()) Database.getInstance().setNumeric(key, numericalSnapshot.get(key));
		for(String key : conditionalSnapshot.keySet()) Database.getInstance().setConditional(key, conditionalSnapshot.get(key));
		for(String key : messageSnapshot.keySet()) Database.getInstance().setMessage(key, messageSnapshot.get(key));
	}
}