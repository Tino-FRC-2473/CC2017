package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MakeshiftTurn extends Command {

	private final double diffCap = 0.3;
	private final double skiddingScale = 0.1;
	private final double throttleScale = 0.15;
	
	public MakeshiftTurn() {
		requires(Robot.piDriveTrain);
	}
	
	private final double turnDeadZone = 3.6;
	private final double throttleDeadZone = 0.04;
	
	@Override
	protected void execute() {
		System.out.println(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z) + " " + Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X)*90);
		double turn = Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X)*90;
		double throttle = -Database.getInstance().getNumeric(ControlsMap.THROTTLE_Z);
		if (Math.abs(turn) < turnDeadZone) 
			driveStraight(throttle);
		else if (Math.abs(throttle) < throttleDeadZone)
			this.turnOnADime(turn);
		else
			this.turn(throttle, turn);
	}
	
	private void turnOnADime(double turn) {
		System.out.println("TURN ON A DIME");
		double diff = Math.signum(turn) * Math.sqrt(Math.abs(Math.sin(turn * Math.PI / 180)));
		setLeftPow(diff);
		setRightPow(-diff);
	}
	
	private void turn(double pow, double turn) {
		System.out.println("TURN");
		double diff = (skiddingScale + throttleScale * pow) * Math.sin(turn * Math.PI / 180);
		if (Math.abs(diff) > diffCap)
			diff = Math.signum(diff) * diffCap;
		if (pow > 0) {
			setLeftPow(pow + diff); 
			setRightPow(pow - diff); 
		} else if (pow < 0) {
			setLeftPow(pow - diff);
			setRightPow(pow + diff);
		}
	}

	private void driveStraight(double pow) {
		System.out.println("DRIVE STRAIGHT: " + pow);
		setLeftPow(pow);
		setRightPow(pow);
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
