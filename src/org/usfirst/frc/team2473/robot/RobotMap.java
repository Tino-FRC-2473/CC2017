package org.usfirst.frc.team2473.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int FRONT_LEFT = 2;
	public static final int BACK_LEFT = 3;
	public static final int FRONT_RIGHT = 4;
	public static final int BACK_RIGHT = 5;
	
	public static final String GYRO_YAW = "gyro yaw";
	public static final String GYRO_RATE = "gyro rate";
	
	public static final String PEG_DISTANCE = "peg distance";
	public static final String PEG_ANGLE = "peg angle";
	public static final String FUNCTION_TRIGGER = "function trigger";
	public static final String STOP_TRIGGER = "stop trigger";
}
