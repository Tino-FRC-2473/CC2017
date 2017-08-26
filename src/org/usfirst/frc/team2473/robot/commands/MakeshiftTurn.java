package org.usfirst.frc.team2473.robot.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MakeshiftTurn extends Command {

	private final double powCap = 0.3;
	private final double a = 0.5;
	
	public MakeshiftTurn() {
		requires(Robot.piDriveTrain);
	}
	
	@Override
	protected void execute() {
		System.out.println(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z) + " " + Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X)*90);
				this.turn(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z), Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X)*90);

	}
	
	private void turn(double pow, double turn) {
		double diff = (a + pow) * Math.sin(turn * Math.PI / 180);
		if (Math.abs(diff) > powCap)
			diff = Math.signum(diff) * powCap;
		if (pow > 0) {
			setLeftPow(pow + diff); 
			setRightPow(pow - diff); 
		} else if (pow < 0) {
			setLeftPow(pow - diff);
			setRightPow(pow + diff);
		}
	}
	
	private void setRightPow(double pow) {
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).set(-pow);
		Devices.getInstance().getTalon(RobotMap.BACK_RIGHT).set(-pow);

	}

	private void setLeftPow(double pow) {
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).set(pow);
		Devices.getInstance().getTalon(RobotMap.BACK_LEFT).set(pow);

	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	public void end() {
		setRightPow(0);
		setLeftPow(0);
	}

}
