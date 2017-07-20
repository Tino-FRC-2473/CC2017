
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
	
	public static final int climberTalonOne = 1;
	public static final int climberTalonTwo = 2;
	public static final int shooterTalonOne = 3;
	public static final int shooterTalonTwo = 4;
	public static final int gearPickupMotor = 5;
	public static final int Gear_Stick = 6;
	public static final int Climber_Limit_Swith = 7;
	
	
	public static final int gearpickupEncVal = 69;
	public static final int gearpickupButtonVal = 70;
	public static final int shooterEncVal = 68;
	
	public static final String Gear_Pickup_Enc = "abc";
	public static final String Shooter_Enc = "def";
	public static final String climberTalonOneCurrent = "sdf";
	public static final String climberTalonTwoCurrent = "sdf";
	public static final String shooterTalonOneCurrent = "sdf";
	public static final String shooterTalonTwoCurrent = "sdf";
	public static final String gearPickupTalonCurrent = "sdf";
}
