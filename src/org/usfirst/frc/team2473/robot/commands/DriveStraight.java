package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

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
		if(Math.abs(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)) <= 0.04) {
			Robot.piDriveTrain.drive(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z), Robot.piDriveTrain.getAngleRate());
			System.out.println(Robot.piDriveTrain.getGyro().getYaw());
		}
		else {
			Robot.piDriveTrain.drive(squareWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z)),Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			System.out.println(Database.getInstance().getNums());
		}
	}

	private double squareWithSign(double d){
		if(d>=0.04){
			return -d*d;
		}
		else if(d<=-0.04){
			return d*d;
		}
		else{
			return 0;
		}
    }
	
	private void setRightPow(double pow){
		
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).set(pow);
		Devices.getInstance().getTalon(RobotMap.BACK_RIGHT).set(pow);

	}
	private void setLeftPow(double pow){
		
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