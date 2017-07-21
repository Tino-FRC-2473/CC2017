package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

/**
 * Tracks value for an existing AnalogInput sensor on the robot
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class AnalogTracker extends DeviceTracker {

	/**
	 * Constructor allowing for the assignment of a reference key and port number of existing analog input sensor.
	 * @param key <code>String</code> value representing the assigned reference key for the tracker
	 * @param port <code>int</code> value representing the channel port of the sensor
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/AnalogInput.html"><code>AnalogInput</code></a>
	 */
	public AnalogTracker(String key, int port) {
		super(key, Type.NUMERIC, port);
		setEvokeNumeric(() -> Devices.getInstance().getAnalogInput(port).getValue());
	}
	
}
