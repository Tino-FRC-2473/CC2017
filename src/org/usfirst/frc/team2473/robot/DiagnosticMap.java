package org.usfirst.frc.team2473.robot;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.MotorDiagnoser.Type;

public class DiagnosticMap {
	//conversion constants
	public static final double MAX_TORQUE775 = 0.71;
	public static final double MAX_CURRENT775 = 70;
	
	public static final double MAX_TORQUE550 = 0.49;
	public static final double MAX_CURRENT550 = 45;
	
//	public static final double MAX_TORQUE3102 = 0.3;
//	public static final double MAX_CURRENT3102 = 10.0;
	
	public static final double MAX_TORQUECIM = 2.41;
	public static final double MAX_CURRENTCIM = 70;
	
	public static final double DRIVETRAIN_GEAR_RATIO = 14;
	public static final double SHOOTER_GEAR_RATIO = 1;
	public static final double DISTANCE_PER_PULSE_DIGITALENC = 5; 
	
	public static final double ENCODER_PER_ROTATION = 1024;
	public static final double DRIVE_TRAIN_ENCODER_PER_ROTATION = 7600;
	//ranges
	public static final double MOTOR_RANGE = 7000;
	
	
	public static final double M775_SLOPE = 0.0046; //efficiency by rpm
	public static final double M550_SLOPE = 0.004;
	public static final double CIM_SLOPE = 0.015;
	
	public static final double M775_RANGE = 17300; //rpm
	public static final double M550_RANGE = 17800;
	public static final double CIM_RANGE = 4500;
	
	
	//updates needed: torque values need tweeking and different encoder counts per rotation should be added.
}