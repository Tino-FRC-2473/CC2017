package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

public class DriveStraight extends Command {

	private double maxPow = 0.8;
	private double deadzone = 0.04;
	private double maxWheelX = 0;
	
	public DriveStraight() {
		requires(Robot.piDriveTrain);
	}

	@Override
	protected void initialize() {
		Robot.piDriveTrain.enable();
	}

	@Override
	protected void execute() {
//		if (Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)) <= 0.04) {
//			Robot.piDriveTrain.drive(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z),
//					Robot.piDriveTrain.getAngleRate());
//			System.out.println(Robot.piDriveTrain.getGyro().getYaw());
		if(Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)) > 0.04){
			turn(squareRtWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)), Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
		}
		else {
			Robot.piDriveTrain.drive(squareRtWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)),
					Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			System.out.println(Database.getInstance().getNums());
		}
	}

	private double squareRtWithSign(double d) {
		if (d >= 0.04) {
			return -Math.sqrt(d);
		} else if (d <= -0.04) {
			return Math.sqrt(d);
		} else {
			return 0;
		}
	}

	private void setRightPow(double pow) {
		if (pow > maxPow)
			pow = maxPow;
		else if (pow < -maxPow)
			pow = -maxPow;
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).set(pow);
		Devices.getInstance().getTalon(RobotMap.BACK_RIGHT).set(pow);

	}

	private void setLeftPow(double pow) {
		if (pow > maxPow)
			pow = maxPow;
		else if (pow < -maxPow)
			pow = -maxPow;
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).set(pow);
		Devices.getInstance().getTalon(RobotMap.BACK_LEFT).set(pow);

	}

	private void turn(double pow, double turn) {
		if (pow > maxPow)
			pow = maxPow;
		if (pow < -maxPow)
			pow = -maxPow;
		// right turn
		if (turn > deadzone) {
			if (pow > deadzone) {
				setLeftPow(0.4 / (maxWheelX - deadzone) * (turn - deadzone) + 0.5);
				setRightPow(-0.4 / (maxWheelX - deadzone) * (turn - deadzone) + 0.5);
				// setLeftPow(0.9*pow);
				// setRightPow(0.1*pow);
			} else if (pow < -deadzone) {
				setLeftPow(-(0.4 / (maxWheelX - deadzone) * (turn - deadzone) + 0.5));
				setRightPow(-(-0.4 / (maxWheelX - deadzone) * (turn - deadzone) + 0.5));
				// setLeftPow(0.1*pow);
				// setRightPow(0.9*pow);
			} else {
				setLeftPow(pow);
				setRightPow(-pow);
			}
		}
		// left turn
		else if (turn < -deadzone) {
			if (pow > deadzone) {
				setLeftPow(0.4 / (maxWheelX - deadzone) * (turn + deadzone) + 0.5);
				setRightPow(-0.4 / (maxWheelX - deadzone) * (turn + deadzone) + 0.5);
				// setLeftPow(0.1*pow);
				// setRightPow(0.9*pow);
			} else if (pow < -deadzone) {
				setLeftPow(-(0.4 / (maxWheelX - deadzone) * (turn + deadzone) + 0.5));
				setRightPow(-(-0.4 / (maxWheelX - deadzone) * (turn + deadzone) + 0.5));
				// setLeftPow(0.9*pow);
				// setRightPow(0.1*pow);
			} else {
				setLeftPow(-turn);
				setRightPow(turn);
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.piDriveTrain.disable();
		System.out.println("DriveStraight ended. :)");
	}

	@Override
	protected void interrupted() {
		Robot.piDriveTrain.disable();
	}

}