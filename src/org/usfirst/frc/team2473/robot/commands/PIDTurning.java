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
		
	}

	@Override
	protected void execute() {
		Robot.driveTrain.turn(90.0f, SPEED);
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
		end(); //disable the PID
	}

}
