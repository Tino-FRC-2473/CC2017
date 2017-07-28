package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.robot.Robot;

public class DriveStraight extends Command {

	public DriveStraight() {
		requires(Robot.driveTrain);
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.enable();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.drive(Robot.oi.getThrottle().getZ(), Robot.driveTrain.getAngleRate());
		System.out.println(Robot.driveTrain.getGyro().getYaw());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrain.disable();
	}

	@Override
	protected void interrupted() {
		Robot.driveTrain.disable();
	}

}
