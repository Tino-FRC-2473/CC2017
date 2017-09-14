package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

public class DriveStraight extends Command {

	public DriveStraight() {
		requires(Robot.driveTrain);
	}

	@Override
	protected void initialize() {
		Robot.driveTrain.setPIDMode(RobotMap.GYRO_YAW);
		Robot.driveTrain.setTargetAngle(Robot.driveTrain.getGyro().getYaw());
		Robot.driveTrain.enable();
		Robot.driveTrain.setPin(true);
	}

	@Override
	protected void execute() {
//		if (Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_KEY)) < 0.2) {
//			Robot.driveTrain.disable();
//			Robot.driveTrain.stop();
//		} else {
//			if (!Robot.driveTrain.getPIDController().isEnabled()) {
//				Robot.driveTrain.enable();
//			}
//			Robot.driveTrain.drive(Database.getInstance().getNumeric(ControlsMap.THROTTLE_KEY),
//					Robot.driveTrain.getAngleRate());
//			System.out.println(Robot.driveTrain.getAngleRate());
//		}
		System.out.println("Yaw: " + Database.getInstance().getNumeric(RobotMap.GYRO_YAW));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.driveTrain.disable();
		Robot.driveTrain.setPin(false);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
