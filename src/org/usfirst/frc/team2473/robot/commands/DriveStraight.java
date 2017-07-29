package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;

public class DriveStraight extends Command {

	public DriveStraight() {
		requires(Robot.piDriveTrain);
	}

	@Override
	protected void initialize() {
		Robot.piDriveTrain.enable();
	}

	@Override
	protected void execute() {
		Robot.piDriveTrain.drive(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z), Robot.piDriveTrain.getAngleRate());
		System.out.println(Robot.piDriveTrain.getGyro().getYaw());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.piDriveTrain.disable();
	}

	@Override
	protected void interrupted() {
		Robot.piDriveTrain.disable();
	}

}