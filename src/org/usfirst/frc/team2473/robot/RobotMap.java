
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
	public static final int shooterServo = 5;
	public static final int gearPickupMotor = 6;
	public static final int Gear_Stick = 7;
	public static final int Climber_Limit_Swith = 8;
	
	
	public static final int gearpickupEncVal = 69;
	public static final int gearpickupButtonVal = 5;
	
}
