package org.usfirst.frc.team2473.robot;

public class Acceleration {
	public static final double START_POWER = 0.3;
	public static final double END_POWER = 0.3;
	
	public static final double MAX_POWER = 0.7;
	
	public static final int TOTAL_ENCODER_DISTANCE = 2000;
	
	public static final int START_ACCELERATION_ENCODER = 50;
	public static final int END_ACCELERATION_ENCODER = 750;
	public static final int START_DECELERATION_ENCODER = 1250;
	public static final int END_DECELERATION_ENCODER = 1950;
	
	public static final int ACCELERATION_INTERVAL = 175;
	public static final double ACCELERATION_RATE = (MAX_POWER - START_POWER) / ((END_ACCELERATION_ENCODER - START_ACCELERATION_ENCODER) / ACCELERATION_INTERVAL);
	
	public static double getPower(int encoderCount, double currentPower){
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
