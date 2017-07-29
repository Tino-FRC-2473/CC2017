package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.robot.Robot;

public class PIDTurning extends Command {

	public static final double SPEED = 0.5; //sets the speed

	public PIDTurning() {
		requires(Robot.driveTrain);
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.setTargetAngle(90.0f); //sets target angle to 90 degrees
		Robot.driveTrain.enable(); //enables the PID
	}

	@Override
	protected void execute() {
		Robot.driveTrain.drive(SPEED, Robot.driveTrain.getAngleRate()); //turns the robot with PID
		System.out.println(Robot.driveTrain.getGyro().getYaw()); //prints out gyro yaw
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrain.disable(); //disable the PID
	}

	@Override
	protected void interrupted() {
		end();
	}

}
