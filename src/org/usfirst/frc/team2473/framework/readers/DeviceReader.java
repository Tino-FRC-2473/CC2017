package org.usfirst.frc.team2473.framework.readers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.threading.StringSupplier;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;

/* The purpose of the following class is
 * to read all the values of Devices
 * */
public class DeviceReader extends Thread {
	private volatile boolean running;
	private int period;
	
	Map<String, DoubleSupplier> numericalCalls;
	Map<String, BooleanSupplier> conditionalCalls;
	Map<String, StringSupplier> messageCalls;
	
	Map<String, Double> numericalSnapshot;
	Map<String, Boolean> conditionalSnapshot;
	Map<String, String> messageSnapshot;

	public DeviceReader() {
		init(5);
	}
	
	public DeviceReader(int milis) {
		init(milis);
	}
	
	public void init(int milis) {
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
	
	@Override
	public void run() {
		while(running) {
			//task execution
			trackValues();

			try {
				Thread.sleep(period);				
			} catch(InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void kill() {
		running = false;		
	}
	
	public boolean isDead() {
		return !running;
	}
	
	public void assignCalls() {
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
			switch(tracker.getType()) {
			case NUMERIC:
				numericalCalls.put(tracker.getKey(), tracker.getterNumeric());
				break;
			case CONDITIONAL:
				conditionalCalls.put(tracker.getKey(), tracker.getterConditional());
				break;
			case MESSAGE:
				messageCalls.put(tracker.getKey(), tracker.getterMessage());
				break;
			default:
				break;
			}		
	}
	
	public void trackValues() {
		for(String key : numericalCalls.keySet()) numericalSnapshot.put(key, numericalCalls.get(key).getAsDouble());
		for(String key : conditionalCalls.keySet()) conditionalSnapshot.put(key, conditionalCalls.get(key).getAsBoolean());
		for(String key : messageCalls.keySet()) messageSnapshot.put(key, messageCalls.get(key).getAsString());

		for(String key : numericalSnapshot.keySet()) Database.getInstance().setNumeric(key, numericalSnapshot.get(key));
		for(String key : conditionalSnapshot.keySet()) Database.getInstance().setConditional(key, conditionalSnapshot.get(key));
		for(String key : messageSnapshot.keySet()) Database.getInstance().setMessage(key, messageSnapshot.get(key));
	}
}