package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.diagnostic.Diagnostics;
import org.usfirst.frc.team2473.framework.diagnostic.Diagnostics.TestType;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class OneTime extends Command{

	@Override
	public void execute() {
		Diagnostics.getInstance().startTests(TestType.ONETIME);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
