package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

/**
 * Tracks one of several values available for access from a CANTalon regardless of attached encoders.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class TalonTracker extends DeviceTracker {
	
	/**
	 * Type of data being received from the talon
	 * */
	public enum Target {
		/**
		 * Talon power
		 */
		POWER, 
		/**
		 * Talon output voltage
		 */
		VOLTAGE, 
		/**
		 * Talon output current
		 */
		CURRENT
	}

	/**
	 * Constructor allowing for assignment of reference key, corresponding talon device id, and type of data being accessed.
	 * @param key <code>String</code> value representing the user-assigned reference key for this tracker
	 * @param port <code>int</code> value representing the device id for the corresponding talon to this tracker
	 * @param target <code>Target</code> value representing the type of data being targeted by this tracker
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/CANTalon.html"><code>CANTalon</code></a>
	 * @see Target
	 */
	public TalonTracker(String key, int port, Target target) {
		super(key, Type.NUMERIC, port); //super constructor with given key and port, classified as numerical value tracker 
		/*Based on the given target, assign the proper evoke method*/
		if(target == Target.POWER) setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).get()); 
		else if(target == Target.VOLTAGE) setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getOutputVoltage());
		else if(target == Target.CURRENT) setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getOutputCurrent());
	}
}