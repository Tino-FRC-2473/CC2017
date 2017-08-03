package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

public class DriveStraight extends Command {

	private double maxTurn = 0.8;
	private double deadzone = 0.04;

	public DriveStraight() {
		requires(Robot.piDriveTrain);
	}

	@Override
	protected void initialize() {
		Robot.piDriveTrain.enable();
	}

	@Override
	protected void execute() {
		if (Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)) <= 0.04) {
			Robot.piDriveTrain.drive(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z),
					Robot.piDriveTrain.getAngleRate());
			System.out.println(Robot.piDriveTrain.getGyro().getYaw());
		} else {
			Robot.piDriveTrain.drive(squareWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)),
					Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			System.out.println(Database.getInstance().getNums());
		}
	}

	private double squareWithSign(double d) {
		if (d >= 0.04) {
			return -d * d;
		} else if (d <= -0.04) {
			return d * d;
		} else {
			return 0;
		}
	}

	private void setRightPow(double pow) {
		if (pow > maxTurn)
			pow = maxTurn;
		else if (pow < -maxTurn)
			pow = -maxTurn;
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).set(pow);
		Devices.getInstance().getTalon(RobotMap.BACK_RIGHT).set(pow);

	}

	private void setLeftPow(double pow) {
		if (pow > maxTurn)
			pow = maxTurn;
		else if (pow < -maxTurn)
			pow = -maxTurn;
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).set(pow);
		Devices.getInstance().getTalon(RobotMap.BACK_LEFT).set(pow);

	}

	private void turn(double pow, double turn) {
		if (turn > maxTurn)
			turn = maxTurn;
		if (turn < -maxTurn)
			turn = -maxTurn;
		// right turn
		if (turn > deadzone) {
			if (pow > deadzone) {
				setLeftPow(0.4 / (maxTurn - deadzone) * (turn - deadzone) + 0.5);
				setRightPow(-0.4 / (maxTurn - deadzone) * (turn - deadzone) + 0.5);
				// setLeftPow(0.9*pow);
				// setRightPow(0.1*pow);
			} else if (pow < -deadzone) {
				setLeftPow(-(0.4 / (maxTurn - deadzone) * (turn - deadzone) + 0.5));
				setRightPow(-(-0.4 / (maxTurn - deadzone) * (turn - deadzone) + 0.5));
				// setLeftPow(0.1*pow);
				// setRightPow(0.9*pow);
			} else {
				setLeftPow(turn);
				setRightPow(-turn);
			}
		}
		// left turn
		else if (turn < deadzone) {
			if (pow > deadzone) {
				setLeftPow(0.4 / (maxTurn - deadzone) * (turn + deadzone) + 0.5);
				setRightPow(-0.4 / (maxTurn - deadzone) * (turn + deadzone) + 0.5);
				// setLeftPow(0.1*pow);
				// setRightPow(0.9*pow);
			} else if (pow < -deadzone) {
				setLeftPow(-(0.4 / (maxTurn - deadzone) * (turn + deadzone) + 0.5));
				setRightPow(-(-0.4 / (maxTurn - deadzone) * (turn + deadzone) + 0.5));
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