package org.usfirst.frc.team2473.robot;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.MotorDiagnoser.Type;

public class DiagnosticMap {
	//conversion constants
	public static final double MAX_TORQUE775 = 0.3;
	public static final double MAX_CURRENT775 = 10.0;
	public static final double ENCODER_PER_ROTATION775 = 6000;
	public static double DRIVETRAIN_GEARRATIO = 14;
	public static final Type MOTOR_TYPE = Type.M775;

	//ranges
	public static final double MOTOR_RANGE = 300;	
}