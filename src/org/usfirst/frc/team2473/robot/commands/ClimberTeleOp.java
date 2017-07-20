package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberTeleOp extends Command {

    public ClimberTeleOp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.climber.initDefaultCommand();
    	Robot.climber.ClimberMotorOne.reset(); 
    	Robot.climber.ClimberMotorTwo.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
		Robot.climber.ClimberMotorOne.set(0.5);
		Robot.climber.ClimberMotorTwo.set(0.5);
    	
    	if (Robot.climber.switchVal()){
    		Robot.climber.ClimberMotorOne.set(0);
    		Robot.climber.ClimberMotorTwo.set(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	//if (Robot.climber.ClimberMotorOne.getEncPosition() >= 69 && Robot.climber.ClimberMotorTwo.getEncPosition() >= 69) {
//    		//return true;
		return false;
        
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
