package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MotorStopCommand extends Command {

	public MotorStopCommand() {
		requires(Robot.motorSystem);
	}
	
	@Override
	public void execute() {
		Robot.motorSystem.runMotor(0);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Database.getInstance().getNumeric(RobotMap.MOTOR_POWER_KEY)==0;
	}

}
