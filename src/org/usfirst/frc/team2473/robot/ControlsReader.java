package org.usfirst.frc.team2473.robot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ControlsReader {
	private Map<String, DoubleSupplier> joystickCalls;
	private Map<String, Double> joystickSnapshot;
	
	private Map<String, BooleanSupplier> buttonCalls;
	private Map<String, Boolean> buttonSnapshot;
	
	private static ControlsReader theInstance;

	static {
		theInstance = new ControlsReader();
	}
	
	public static ControlsReader getInstance() {
		return theInstance;
	}
	
	private ControlsReader() {
		joystickCalls = new HashMap<>();
		joystickSnapshot = new HashMap<>();
		buttonCalls = new HashMap<>();
		buttonSnapshot = new HashMap<>();
	}
	
	public void init() {
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) if(tracker.getClass().getName().equals("JoystickTracker")) joystickCalls.put(tracker.getKey(), tracker.getterNumeric());		
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) if(tracker.getClass().getName().equals("ButtonTracker")) buttonCalls.put(tracker.getKey(), tracker.getterConditional());
	}

	public void updateAll() {		
		for(String key : joystickCalls.keySet()) joystickSnapshot.put(key, joystickCalls.get(key).getAsDouble());
		for(String key : joystickSnapshot.keySet()) Database.getInstance().setNumeric(key, joystickSnapshot.get(key));

		for(String key : buttonCalls.keySet()) buttonSnapshot.put(key, buttonCalls.get(key).getAsBoolean());
		for(String key : buttonSnapshot.keySet()) Controls.getInstance().setButtonValue(key, buttonSnapshot.get(key));
	}
}