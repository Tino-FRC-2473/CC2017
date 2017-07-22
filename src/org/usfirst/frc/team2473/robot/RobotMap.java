package org.usfirst.frc.team2473.robot;


/**
 * Class stores constants for devices installed on the robot and respective access keys. These constants can be value limits, port numbers, device ids, and more.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class RobotMap {
	//device ids
	public static final int MOTOR = 10;
	public static final int SERVO = 0;
	public static final int GYRO = 0;

	//keys
	public static final String MOTOR_ENCODER_KEY = "encoder key";
	public static final String MOTOR_VOLTAGE_KEY = "voltage key";
	public static final String MOTOR_CURRENT_KEY = "current key";
	public static final String MOTOR_POWER_KEY = "power key";
	public static final String MOTOR_SPEED_KEY = "speed key";
	public static final String SERVO_POSITION_KEY = "servo position key";
	public static final String SERVO_POWER_KEY = "servo power key";
	public static final String GYRO_HEADING_KEY = "gyro key";
	
	public static final String PEG_DISTANCE = "peg distance";
	public static final String PEG_ANGLE = "peg angle";
	public static final String FUNCTION_TRIGGER = "function trigger";
	public static final String STOP_TRIGGER = "stop trigger";
}