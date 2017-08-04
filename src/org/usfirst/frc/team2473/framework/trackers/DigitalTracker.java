package org.usfirst.frc.team2473.framework.trackers;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team2473.framework.components.Devices;

/**
 * Tracks value for an existing DigitalInput sensor on the robot
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class DigitalTracker extends DeviceTracker {

	/**
	 * Constructor allowing for the assignment of a reference key and port number of existing digital input sensor.
	 * @param key <code>String</code> value representing the assigned reference key for the tracker
	 * @param port <code>int</code> value representing the channel port of the sensor
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/DigitalInput.html"><code>DigitalInput</code></a>
	 */
	public DigitalTracker(String key, int port) {
		super(key, Type.CONDITIONAL, port);
		setEvokeConditional(() -> Devices.getInstance().getDigitalInput(port).get());
	}
	
}