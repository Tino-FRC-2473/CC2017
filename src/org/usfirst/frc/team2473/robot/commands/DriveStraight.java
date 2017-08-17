
package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

public class DriveStraight extends Command {

	private double maxPow = 0.8;
	private double wheelDeadzone = 0.04;
	private double throttleDeadzone = 0.05;
	// WARNING!!!!!!!!!!!!!!!!!!
	// Cannot run code without initializing maxWheelX value correctly
	private double maxWheelX = 1;

	public DriveStraight() {
		requires(Robot.piDriveTrain);
	}

	@Override
	protected void initialize() {
		Robot.piDriveTrain.enable();
	}

	@Override
	protected void execute() {
		Robot.piDriveTrain.setTargetAngle(Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X)*90);
		
		Robot.piDriveTrain.drive(squareWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)),Robot.piDriveTrain.getAngleRate());

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

	private void turn(double pow, double turn) {
		if (Math.abs(pow) > maxPow) {
			pow = maxPow * Math.signum(pow);
		}
		if (pow > throttleDeadzone) {
			setLeftPow(pow * (0.4 / (maxWheelX - wheelDeadzone) * (turn - wheelDeadzone) + 0.5));
			setRightPow(pow * (-0.4 / (maxWheelX - wheelDeadzone) * (turn - wheelDeadzone) + 0.5));
		} else if (pow < -throttleDeadzone) {
			setLeftPow(pow * (0.4 / (maxWheelX - wheelDeadzone) * (turn + wheelDeadzone) + 0.5));
			setRightPow(pow * (-0.4 / (maxWheelX - wheelDeadzone) * (turn + wheelDeadzone) + 0.5));
		} else {
			System.out.println("right");
			if (Math.abs(turn) > wheelDeadzone) {
				setLeftPow(-squareWithSign(turn));
				setRightPow(squareWithSign(turn));
			}
		}
	}

	private void setRightPow(double pow) {
		double temp = pow;
		if (temp > maxPow)
			temp = maxPow;
		else if (temp < -maxPow)
			temp = -maxPow;
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).set(-temp);
		Devices.getInstance().getTalon(RobotMap.BACK_RIGHT).set(-temp);

	}

	private void setLeftPow(double pow) {
		double temp = pow;
		if (temp > maxPow)
			temp = maxPow;
		else if (temp < -maxPow)
			temp = -maxPow;
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).set(temp);
		Devices.getInstance().getTalon(RobotMap.BACK_LEFT).set(temp);

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