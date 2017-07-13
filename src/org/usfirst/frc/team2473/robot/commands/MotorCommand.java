package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MotorCommand extends Command {

	public MotorCommand() {
		requires(Robot.motorSystem);
	}

	@Override
	public void execute() {
		Robot.motorSystem.runMotor(Database.getInstance().getNumeric(ControlsMap.JOYSTICK_TWO));
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
