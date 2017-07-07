package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSystem extends Subsystem{
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void runShooter(double pow) {
		Devices.getInstance().getTalon(RobotMap.SHOOTER).set(pow);;
	}
}