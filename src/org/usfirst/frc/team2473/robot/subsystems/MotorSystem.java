package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class MotorSystem extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	public void runMotor(double pow) {
		Devices.getInstance().getTalon(RobotMap.MOTOR).set(pow);
	}
}
