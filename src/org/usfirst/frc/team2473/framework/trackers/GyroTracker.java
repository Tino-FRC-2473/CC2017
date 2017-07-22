package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

/**
 * Tracks angle value for an existing AnalogGyro on the robot
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class GyroTracker extends DeviceTracker {
	/**
	 * Constructor allowing for key assignment and corresponding analog gyro port assignment.
	 * @param key <code>String</code> value representing reference key for tracker
	 * @param port <code>int</code> value representing the port number of the AnalogGyro
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/AnalogGyro.html"><code>AnalogGyro</code></a>
	 * */
	public GyroTracker(String key, int port) {
		super(key, Type.NUMERIC, port); //call the super constructor with the given key and port, classified as numeric value tracker
		setEvokeNumeric(() -> Devices.getInstance().getGyro(getPort()).getAngle()); //get the gyro device of given port from Devices and get its angle
	}

	/**
	 * Calibrates and resets the gyro associated with the tracker. 
	 * */
	public void resetGyro() {
		Devices.getInstance().getGyro(getPort()).calibrate(); //get the gyro device of given port from Devices and calibrate it
	}
}