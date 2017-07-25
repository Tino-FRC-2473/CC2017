package org.usfirst.frc.team2473.framework.readers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team2473.framework.components.Controls;
import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;

/**
 * This class scans all values from existing <code>Trackers</code> and maps them to a <code>Database</code>.
 * The process occurs periodically.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class ControlsReader {
	private Map<String, DoubleSupplier> joystickCalls; //stores all getter functions returning joystick values being tracked
	private Map<String, Double> joystickSnapshot; //instantaneously stores all joystick values returned from supplier functions stored in joystickCalls
	
	private Map<String, BooleanSupplier> buttonCalls; //stores all getter functions returning button values being tracked
	private Map<String, Boolean> buttonSnapshot; //instantaneously stores all button values returned from supplier functions stored in buttonCalls
	
	private static ControlsReader theInstance; //instance of the class to use as a static reference

	static {
		theInstance = new ControlsReader(); //call the constructor as a static process
	}
	
	/**
	 * Returns the static instance of this class to use during the program.
	 * @return a <code>ControlsReader</code> object to use as the static reference
	 */
	public static ControlsReader getInstance() {
		return theInstance;
	}
	
	private ControlsReader() { //private constructor prevents an instance of this object to be created or use other than the static theInstance, getInstance() reference
		//simply constructing the maps
		joystickCalls = new HashMap<>();
		joystickSnapshot = new HashMap<>();
		buttonCalls = new HashMap<>();
		buttonSnapshot = new HashMap<>();
	}
	
	/**
	 * Initial first step for the joystick and button value tracking proces..
	 */
	public void init() {
		/*Loop through the trackers. Based on the tracker type(JoystickTracker/ButtonTracker), place it under the same key in the correct map*/
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) if(tracker.getClass().getName().equals("JoystickTracker")) joystickCalls.put(tracker.getKey(), tracker.getterNumeric());		
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) if(tracker.getClass().getName().equals("ButtonTracker")) buttonCalls.put(tracker.getKey(), tracker.getterConditional());
	}

	/**
	 * Updates the <code>Database</code> and <code>Controls</code> classes based on the 
	 * @see org.usfirst.frc.team2473.framework.components.Controls
	 * @see org.usfirst.frc.team2473.framework.Database
	 */
	public void updateAll() {		
		//loop through the map of getter functions by key, receive the values, and store them under the same key in the corresponding snapshot map
		for(String key : joystickCalls.keySet()) joystickSnapshot.put(key, joystickCalls.get(key).getAsDouble());
		//loop through the map of snapshot values by key and store them under the same key in the Database
		for(String key : joystickSnapshot.keySet()) Database.getInstance().setNumeric(key, joystickSnapshot.get(key));

		//loop through the map of getter functions by key, receive the values, and store them under the same key in the corresponding snapshot map
		for(String key : buttonCalls.keySet()) buttonSnapshot.put(key, buttonCalls.get(key).getAsBoolean());
		//loop through the map of snapshot values by key and store them under the same key in Controls
		for(String key : buttonSnapshot.keySet()) Controls.getInstance().setButtonValue(key, buttonSnapshot.get(key));
	}
}