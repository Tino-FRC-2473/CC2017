package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Controls;

/**
 * Tracks an axial value on a joystick.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class JoystickTracker extends DeviceTracker {

	/**
	 * All possible joystick value types to track
	 * */
	public enum Type {
		/**
		 * Value along x-axis
		 * */
		X,
		/**
		 * Value along y-axis
		 * */
		Y, 
		/**
		 * Value along z-axis
		 * */
		Z, 
		/**
		 * Joystick 'throttle' value
		 * */
		THROTTLE, 
		/**
		 * Joystick 'twist' value
		 * */
		TWIST
	}
	
	/**
	 * Constructor allowing for the assignment of a reference key,
	 * joystick port number, and axis-value type
	 * @param key <code>String</code> value representing the user-assigned reference key for this tracker
	 * @param port <code>int</code> value representing the port of the joystick
	 * @param type <code>Type</code> value representing the axis value to track for the joystick
	 * @throws IllegalArgumentException if the assigned <code>Type</code> is invalid
	 * @see Type 
	 * */
	public JoystickTracker(String key,int port, Type type) {
		super(key, DeviceTracker.Type.NUMERIC, port); //call the super constructor with the given key and port, classified as numeric value tracker
		switch(type) { //set evoke based on type passed
			case X:
				setEvokeNumeric(() -> Controls.getInstance().getJoystick(getPort()).getX()); 
				break;
			case Y:
				setEvokeNumeric(() -> Controls.getInstance().getJoystick(getPort()).getY());
				break;
			case Z:
				setEvokeNumeric(() -> Controls.getInstance().getJoystick(getPort()).getZ());
				break;
			case THROTTLE:
				setEvokeNumeric(() -> Controls.getInstance().getJoystick(getPort()).getThrottle());
				break;
			case TWIST:
				setEvokeNumeric(() -> Controls.getInstance().getJoystick(getPort()).getTwist());
				break;
			default:
				throw new IllegalArgumentException("Insufficient getter argument(joystick)"); //if neither of the 5 are assigned, throw the following error
		}
	}	
}