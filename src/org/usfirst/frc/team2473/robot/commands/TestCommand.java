package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommand extends Command{
	
	private boolean firstTime = true;
	private boolean finished = false;
	
	public TestCommand(){
		requires(Robot.driveTrain);
		
	}
	
	@Override
	public void execute(){
		if(firstTime){
			Robot.driveTrain.resetEncoders();
			firstTime = false;
		}
		
		Robot.driveTrain.setPower(0.5);
		if(Robot.driveTrain.getLeftEnc() > 500 || Robot.driveTrain.getRightEnc() > 500){
			Robot.driveTrain.setPower(0);
			finished = true;
		}
	}

	@Override
	protected boolean isFinished() {
		
		return finished;
	}
	
	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {
		end();
	}

}
