package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

import com.ctre.CANTalon.TalonControlMode;

/**
 * Tracks encoder values for existing CANTalons on the robot.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class EncoderTracker extends DeviceTracker {

	/**
	 * Constructor allowing for key assignment and corresponding talon id assignment.
	 * @param key <code>String</code> value representing user-assigned reference key for this tracker
	 * @param port <code>int</code> value representing device id of corresponding talon for this tracker
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/CANTalon.html"><code>CANTalon</code></a>
	 * */
	public EncoderTracker(String key, int port) {
		super(key, Type.NUMERIC, port); //use parameters and assign type to numeric to construct tracker
		setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getPosition()); //set the numerical value to the corresponding talon's get position
	}
	
	/**
	 * Resets the encoder value for the corresponding talon for this tracker
	 * */
	public void resetEncoder() {
		Devices.getInstance().getTalon(getPort()).changeControlMode(TalonControlMode.Position); //set the talon to position edit mode
		Devices.getInstance().getTalon(getPort()).setPosition(0); //set the talon position to 0
		Devices.getInstance().getTalon(getPort()).changeControlMode(TalonControlMode.PercentVbus); //set the talon back to PercentVbus power edit mode
	}
}