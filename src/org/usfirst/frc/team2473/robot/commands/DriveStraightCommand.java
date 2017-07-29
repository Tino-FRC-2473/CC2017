package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Acceleration;
import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightCommand extends Command implements PIDOutput{
	
	private PIDController turnController;
	private double rotateToAngleRate;

	
	private static final double KP = 0.035;
	private static final double KI = 0.0005;
	private static final double KD = 0.04;
	private static final double KF = 0.00;

	private static final double K_TOLERANCE_DEGREES = 2.0f;
	
	private boolean finished = false;
	
	private double currentPower = Acceleration.START_POWER;
	

	public DriveStraightCommand(){
		requires(Robot.driveTrain);

		turnController = new PIDController(KP, KI, KD, KF, Robot.driveTrain.getGyro(), this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(K_TOLERANCE_DEGREES);
		turnController.setContinuous(true);
		turnController.disable();
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return finished;
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
		if(averageEncoderVal >= Acceleration.TOTAL_ENCODER_DISTANCE){
			finished = true;
			Robot.driveTrain.drive(0, rotateToAngleRate);
		}
		else{
			double motorPower = Acceleration.getPower(averageEncoderVal, currentPower);
			currentPower = motorPower;
			Robot.driveTrain.drive(motorPower, rotateToAngleRate);
			
			System.out.println("2 ------------ Current power of the robot = " + currentPower);
		}
	}
	
	//Comment
	//Comment

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {
		System.out.println("PID method called");
		rotateToAngleRate = output;
	}
	
	public void reset() {
		currentPower = Acceleration.START_POWER;
		finished = false;
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.resetGyro();
	}
}
