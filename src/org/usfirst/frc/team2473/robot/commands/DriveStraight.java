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
	//WARNING!!!!!!!!!!!!!!!!!!
	//Cannot run code without initializing maxWheelX value correctly
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
		if(Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)) > 0.04){
			turn(squareWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)), Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
		}
		else {
			Robot.piDriveTrain.drive(squareWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)),
					Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			//System.out.println(Database.getInstance().getNums());
		}
		System.out.println("Wheel: " + Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
	}

	private double squareWithSign(double d) {
		if (d >= 0.04) {
			System.out.println(-d*d);
			return -d*d;
		} else if (d <= -0.04) {
			System.out.println(d*d);
			return d*d;
		} else {
			return 0;
		}
	}

	private void turn(double pow, double turn) {
		if(Math.abs(pow) < maxPow)
		{
			pow = maxPow * Math.signum(pow);
		}
		// if right
		if (turn > wheelDeadzone) {
			//if throttle forward
			if (pow > throttleDeadzone) {
				setLeftPow(0.4 / (maxWheelX - wheelDeadzone) * (turn - wheelDeadzone) + 0.5);
				setRightPow(-0.4 / (maxWheelX - wheelDeadzone) * (turn - wheelDeadzone) + 0.5);
			} else if (pow < -throttleDeadzone) {
				setLeftPow(-(0.4 / (maxWheelX - wheelDeadzone) * (turn - wheelDeadzone) + 0.5));
				setRightPow(-(-0.4 / (maxWheelX - wheelDeadzone) * (turn - wheelDeadzone) + 0.5));
			} else {
//				setLeftPow(pow);
//				setRightPow(-pow);
				System.out.println(Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			}
		}
		// if left
		else if (turn < -wheelDeadzone) {
			if (pow > throttleDeadzone) {
				setLeftPow(0.4 / (maxWheelX - wheelDeadzone) * (turn + wheelDeadzone) + 0.5);
				setRightPow(-0.4 / (maxWheelX - wheelDeadzone) * (turn + wheelDeadzone) + 0.5);
			} else if (pow < -throttleDeadzone) {
				setLeftPow(-(0.4 / (maxWheelX - wheelDeadzone) * (turn + wheelDeadzone) + 0.5));
				setRightPow(-(-0.4 / (maxWheelX - wheelDeadzone) * (turn + wheelDeadzone) + 0.5));
			} else {
//				setLeftPow(-pow);
//				setRightPow(pow);
				System.out.println((double)Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			}
		}
		else {
			setLeftPow(pow);
			setRightPow(pow);
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