package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

/**
 * Tracks position value of an existing Servo on the robot.
 * @author Deep Sethi
 * @author Harmony He 
 * @version 1.0
 * */
public class ServoTracker extends DeviceTracker{

	/**
	 * Constructor allowing for the assignment of a reference key and port number of existing servo.
	 * @param key <code>String</code> value representing the assigned reference key for the tracker
	 * @param port <code>int</code> value representing the port number of the servo
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/Servo.html"><code>Servo</code></a>
	 */
	public ServoTracker(String key, int port) {
		super(key, Type.NUMERIC, port); //super constructor called with given key and port, classified as numeric value tracker
		setEvokeNumeric(() -> Devices.getInstance().getServo(getPort()).getPosition()); //get the Servo with given port number from Devices and get its position as the numeric value call
	}

}