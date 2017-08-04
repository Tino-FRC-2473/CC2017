
package org.usfirst.frc.team2473.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int CLIMBER_TALON_ONE = 1;
	public static final int CLIMBER_TALON_TWO = 9;
	public static final int SHOOTER_TALON_ONE = 2;
	public static final int SHOOTER_TALON_TWO = 11;
	public static final int GEAR_PICKUP_MOTOR = 3;
	public static final int GEAR_STICK = 0;
	public static final int Climber_Limit_Switch = 7;
	
	
	public static final int gearpickupEncVal = 69;
	public static final int gearpickupButtonVal = 70;
	public static final int shooterEncVal = 68;
	public static final int gearPickupServoAngle = 100;
	
	public static final String Gear_Pickup_Enc = "gear pickup encoder";
	public static final String Shooter_Enc = "shooter encoder";
	public static final String climberTalonOneCurrent = "climber talone one";
	public static final String climberTalonTwoCurrent = "climber talon two";
	public static final String shooterTalonOneCurrent = "shooter talon one";
	public static final String shooterTalonTwoCurrent = "shooter talon two";
	public static final String shooterTalonOnePower = "shooter talon one";
	public static final String shooterTalonTwoPower = "shooter talon two";
	public static final String climberTalonOnePower = "shooter talon one";
	public static final String SHOOTER_TALON1_ENC = "shooter talon 1 position";
	public static final String SHOOTER_TALON2_ENC = "shooter talon 2 position";
	public static final String climberTalonTwoPower = "shooter talon two";
	public static final String gearPickupTalonCurrent = "gear pickup talon";
	public static final String CLIMBER_LIMIT_SWITCH = "climber limit switch";
	public static final String GEAR_PICKUP_POWER = "gear pickup power";
}
