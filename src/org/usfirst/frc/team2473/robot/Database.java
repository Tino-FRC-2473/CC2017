package org.usfirst.frc.team2473.robot;

import java.util.HashMap;
import java.util.Map;


public class Database {
	private Map<String, ThreadSafeDouble> numerical_values;
	private Map<String, ThreadSafeBoolean> conditional_values;
	private Map<String, ThreadSafeString> message_values;

	private static Database theInstance;

	static {
		theInstance = new Database();
	}
	
	private Database() {
		numerical_values = new HashMap<>();
		conditional_values = new HashMap<>();
		message_values = new HashMap<>();
		fillMaps();
	}
	
	public static Database getInstance() {
		return theInstance;
	}
	
	public void fillMaps() {
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) {
			switch(tracker.getType()) {
				case NUMERIC:
					numerical_values.put(tracker.getKey(), new ThreadSafeDouble());
					break;
				case CONDITIONAL:
					conditional_values.put(tracker.getKey(), new ThreadSafeBoolean());
					break;
				case MESSAGE:
					message_values.put(tracker.getKey(), new ThreadSafeString());
					break;
				default:
					break;
			}
		}
	}
	
	public void setNumeric(String key, double value) {
		numerical_values.get(key).setValue(value);
	}
	
	public double getNumeric(String key) {
		return numerical_values.get(key).getValue();
	}
	
	public void setConditional(String key, boolean conditional) {
		conditional_values.get(key).setValue(conditional);
	}
	
	public boolean getConditional(String key) {
		return conditional_values.get(key).getValue();
	}
	
	public void setMessage(String key, String message) {
		message_values.get(key).setValue(message);
	}
	
	public String getMessage(String key) {
		return message_values.get(key).getValue();
	}
}