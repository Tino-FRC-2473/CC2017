package org.usfirst.frc.team2473.robot;

public class Acceleration {
	
	// Center start - 6ft 8 inches
	// 1/3 of the path is accel, 1/3 is drive, 1/3 is decel
	// one motor is at normal power, the other is multiplied by a constant r
	
	
	// User set values
	public static final int TOTAL_ENCODER_DISTANCE = 2000;
	public static final int ACCELERATION_INTERVAL = 100;
	
	
	
	public static final double START_POWER = 0.3;
	public static final double END_POWER = 0.3;
	public static final double MAX_POWER = 0.7;
	
	public static final double POWER_RATIO = 1.5;
	
	public static final int START_ACCELERATION_ENCODER = TOTAL_ENCODER_DISTANCE / 18;
	public static final int END_ACCELERATION_ENCODER = TOTAL_ENCODER_DISTANCE / 3;
	public static final int START_DECELERATION_ENCODER = 2*TOTAL_ENCODER_DISTANCE/3;
	public static final int END_DECELERATION_ENCODER = TOTAL_ENCODER_DISTANCE - TOTAL_ENCODER_DISTANCE/18;
	
	
	public static final double ACCELERATION_RATE = (MAX_POWER - START_POWER) / ((END_ACCELERATION_ENCODER - START_ACCELERATION_ENCODER) / ACCELERATION_INTERVAL);
	
	public static double getPower(int encoderCount, double currentPower){
		System.out.println("Encoder" + encoderCount);
		
		if(encoderCount >= START_ACCELERATION_ENCODER && encoderCount <= END_ACCELERATION_ENCODER){
			int speedFactor = (encoderCount - START_ACCELERATION_ENCODER) / ACCELERATION_INTERVAL;
			return START_POWER + speedFactor * ACCELERATION_RATE;
		}
		
		else if(encoderCount >= START_DECELERATION_ENCODER && encoderCount <= END_DECELERATION_ENCODER){
			int speedReductionFactor = (encoderCount - START_DECELERATION_ENCODER) / ACCELERATION_INTERVAL;
			return MAX_POWER - speedReductionFactor * ACCELERATION_RATE;
		}
		
		return currentPower;
	}
	
	
	
}
