package org.usfirst.frc.team2473.robot.commands;


import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

public class AccelStraight extends Command implements PIDOutput{

	protected PIDController turnController;
	protected double rotateToAngleRate;


	protected static final double KP = 0.035;
	protected static final double KI = 0.0005;
	protected static final double KD = 0.04;
	protected static final double KF = 0.00;

	protected static final double K_TOLERANCE_DEGREES = 2.0f;

	protected boolean completelyFinished = false;
	protected boolean finished = false;
	

	protected double currentPower;

	private int totalEncoderDistance;
	private int accelerationInterval;
	private Acceleration accel;


	public AccelStraight(int totalEncoderDistance, int accelerationInterval){
		requires(Robot.driveTrain);

		turnController = new PIDController(KP, KI, KD, KF, Robot.driveTrain.getGyro(), this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(K_TOLERANCE_DEGREES);
		turnController.setContinuous(true);
		turnController.disable();

		this.totalEncoderDistance = totalEncoderDistance;
		this.accelerationInterval = accelerationInterval;
		accel = new Acceleration(totalEncoderDistance, accelerationInterval);
		currentPower = accel.START_POWER;
	}


	@Override
	protected boolean isFinished() {
		return completelyFinished;
	}

	@Override
	public void execute(){

		if (!turnController.isEnabled()) {
			turnController.setSetpoint(Robot.driveTrain.getGyro().getYaw());
			rotateToAngleRate = 0;
			turnController.enable();
		} 

		System.out.println("1 ----------- Current power of the robot = " + currentPower);

		// Change below based on testing
		int averageEncoderVal = ((Robot.driveTrain.getLeftEnc()) + (Robot.driveTrain.getRightEnc())) /2;
		if(averageEncoderVal >= totalEncoderDistance){
			finished = true;
			Robot.driveTrain.drive(0, rotateToAngleRate);
		}
		else{
			double motorPower = accel.getPower(averageEncoderVal, currentPower);
			currentPower = motorPower;
			Robot.driveTrain.drive(currentPower, rotateToAngleRate);

			System.out.println("2 ------------ Current power of the robot = " + currentPower);
		}
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}

	public void reset() {
		currentPower = accel.START_POWER;
		finished = false;
		completelyFinished = false;
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.resetGyro();
	}

	class Acceleration{
		// Center start - 6ft 8 inches
		// 1/3 of the path is accel, 1/3 is drive, 1/3 is decel
		// one motor is at normal power, the other is multiplied by a constant r


		public final double START_POWER = 0.4;
		public final double END_POWER = 0.4;
		public final double MAX_POWER = 0.8;
		
		public final int ACCELERATION_INTERVAL;
		public final int TOTAL_ENCODER_DISTANCE;
		
		public final int START_ACCELERATION_ENCODER;
		public final int END_ACCELERATION_ENCODER;
		public final int START_DECELERATION_ENCODER;
		public final int END_DECELERATION_ENCODER;
		
		public final double ACCELERATION_RATE;
		
		public Acceleration(int totalEncoder, int accelerationInterval) {
			TOTAL_ENCODER_DISTANCE = totalEncoder;
			ACCELERATION_INTERVAL = accelerationInterval;
			
			START_ACCELERATION_ENCODER = TOTAL_ENCODER_DISTANCE / 18;
			END_ACCELERATION_ENCODER = TOTAL_ENCODER_DISTANCE / 3;
			START_DECELERATION_ENCODER = 2*TOTAL_ENCODER_DISTANCE/3;
			END_DECELERATION_ENCODER = TOTAL_ENCODER_DISTANCE - TOTAL_ENCODER_DISTANCE/18;
			
			ACCELERATION_RATE = (MAX_POWER - START_POWER) / ((END_ACCELERATION_ENCODER - START_ACCELERATION_ENCODER) / ACCELERATION_INTERVAL);
		}

		public double getPower(int encoderCount, double currentPower){
			System.out.println("Encoder" + encoderCount);

			if(encoderCount >= START_ACCELERATION_ENCODER && encoderCount <= END_ACCELERATION_ENCODER){
				System.out.println("If statement 1");
				int speedFactor = (encoderCount - START_ACCELERATION_ENCODER) / ACCELERATION_INTERVAL;
				return START_POWER + speedFactor * ACCELERATION_RATE;
			}

			else if(encoderCount >= START_DECELERATION_ENCODER && encoderCount <= END_DECELERATION_ENCODER){
				System.out.println("If statement 2");
				int speedReductionFactor = (encoderCount - START_DECELERATION_ENCODER) / ACCELERATION_INTERVAL;
				return MAX_POWER - speedReductionFactor * ACCELERATION_RATE;
			}

			return currentPower;
		}

	}
}
