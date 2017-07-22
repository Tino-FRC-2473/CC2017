package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MotorTestCommand extends Command {

	@Override
	public void execute() {
		Devices.getInstance().getTalon(RobotMap.MOTOR).set(0.5);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
