package org.usfirst.frc.team2473.framework;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.threading.ThreadSafeBoolean;
import org.usfirst.frc.team2473.framework.threading.ThreadSafeDouble;
import org.usfirst.frc.team2473.framework.threading.ThreadSafeString;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;

/**
 * This class is responsible for storage of thread-safe numerical, conditional, and String message values.
 * @author Deep Sethi
 * @author Harmony He
 * @version 2.0
 */
public class Database {
	private Map<String, ThreadSafeDouble> numerical_values; //stores numerical values under a String key in a ThreadSafeDouble
	private Map<String, ThreadSafeBoolean> conditional_values; //stores boolean values under a String key in a ThreadSafeBoolean
	private Map<String, ThreadSafeString> message_values; //stores String values under a String key in a ThreadSafeString

	private static Database theInstance; //serves as the static instance to use at all times

	static { //construct theInstance as a static function
		theInstance = new Database();
	}
	
	private Database() { //private constructor prevents the creation of such an object elsewhere, forcing the use of the public static getInstance()
		numerical_values = new HashMap<>();
		conditional_values = new HashMap<>();
		message_values = new HashMap<>();
		fillMaps();
	}
	
	/**
	 * Returns a usable instance of this class for program use.
	 * @return a <code>static Database</code> object
	 */
	public static Database getInstance() {
		return theInstance;
	}
	
	private void fillMaps() { //fills the hashmaps constructed in the beginning
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) { //loop through all trackers
			//based on the type: NUMERIC, CONDITIONAL, MESSAGE add a value holding spot in the correct map
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
				default: //by default do nothing
					break;
			}
		}
	}
	
	/**
	 * Sets a new numeric value under the existing given key
	 * @param key a <code>String</code> value representing the reference key in <code>Database</code>
	 * @param value a <code>double</code> value representing the new value to set under the key
	 */
	public void setNumeric(String key, double value) {
		/* gets the ThreadSafeDouble from numerical_values under the given key, and calls the ThreadSafeDouble method setValue,
		 * assigning the correct value
		 */
		numerical_values.get(key).setValue(value);
	}
	
	/**
	 * Returns a numeric value from the <code>Database</code> stored under a given key
	 * @param key a <code>String</code> value representing the reference key
	 * @return the <code>double</code> value stored under the given key in this class 
	 */
	public double getNumeric(String key) {
		/* gets the ThreadSafeDouble from numerical_values under the given key, and calls the ThreadSafeDouble method getValue
		 * in order to return the stored value
		 */
		return numerical_values.get(key).getValue();
	}
	
	/**
	 * Sets a new <code>boolean</code> value under the existing given key
	 * @param key a <code>String</code> value representing the reference key in <code>Database</code>
	 * @param conditional a <code>boolean</code> value representing the new value to set under the key
	 */
	public void setConditional(String key, boolean conditional) {
		/* gets the ThreadSafeConditional from conditional_values under the given key, 
		 * and calls the ThreadSafeBoolean method setValue,
		 * assigning the correct value
		 */
		conditional_values.get(key).setValue(conditional);
	}
	
	/**
	 * Returns a <code>boolean</code> value from the <code>Database</code> stored under a given key
	 * @param key a <code>String</code> value representing the reference key
	 * @return the <code>boolean</code> value stored under the given key in this class 
	 */
	public boolean getConditional(String key) {
		/* gets the ThreadSafeBoolean from conditional_values under the given key, 
		 * and calls the ThreadSafeBoolean method getValue
		 * in order to return the stored value
		 */
		return conditional_values.get(key).getValue();
	}
	
	/**
	 * Sets a new <code>String</code> value under the existing given key
	 * @param key a <code>String</code> value representing the reference key in <code>Database</code>
	 * @param message a <code>String</code> value representing the new value to set under the key
	 */
	public void setMessage(String key, String message) {
		/* gets the ThreadSafeString from message_values under the given key, 
		 * and calls the ThreadSafeString method setValue,
		 * assigning the correct value
		 */
		message_values.get(key).setValue(message);
	}
	
	/**
	 * Returns a <code>String</code> value from the <code>Database</code> stored under a given key
	 * @param key a <code>String</code> value representing the reference key
	 * @return the <code>String</code> value stored under the given key in this class 
	 */
	public String getMessage(String key) {
		/* gets the ThreadSafeString from message_values under the given key, 
		 * and calls the ThreadSafeString method getValue
		 * in order to return the stored value
		 */
		return message_values.get(key).getValue();
	}
}