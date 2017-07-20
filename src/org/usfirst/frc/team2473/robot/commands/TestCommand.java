package org.usfirst.frc.team2473.robot.commands;

import javax.swing.SortingFocusTraversalPolicy;

import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommand extends Command {

	private boolean finished = false;

	public TestCommand() {
		requires(Robot.driveTrain);
	}

	@Override
	public void initialize() {
		Robot.driveTrain.resetEncoders();
		System.out.println("Test command initialized");
	}

	@Override
	public void execute() {
		System.out.println("Robot set to 0.5 power");
		Robot.driveTrain.setPower(0.5);
		if (Robot.driveTrain.getLeftEnc() > 5000 || Robot.driveTrain.getRightEnc() > 5000) {
			System.out.println("Encoder max value reached");
			Robot.driveTrain.setPower(0);
			finished = true;
		}
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}

	@Override
	protected void end() {
		System.out.println("Done");
	}

	@Override
	protected void interrupted() {
		end();
	}

}
