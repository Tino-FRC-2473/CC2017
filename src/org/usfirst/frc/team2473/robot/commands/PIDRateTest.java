package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class PIDRateTest extends Command {

	public PIDRateTest() {

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.setPIDMode(RobotMap.GYRO_RATE);
		Robot.driveTrain.setTargetAngle(0.0f);
		Robot.driveTrain.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_KEY)) < 0.2) {
			Robot.driveTrain.disable();
			Robot.driveTrain.stop();
		} else {
			if (!Robot.driveTrain.getPIDController().isEnabled()) {
				Robot.driveTrain.enable();
			}
			Robot.driveTrain.drive(Database.getInstance().getNumeric(ControlsMap.THROTTLE_KEY),
					Robot.driveTrain.getAngleRate());
			System.out.println(Robot.driveTrain.getAngleRate());
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.driveTrain.disable();
	}
}
