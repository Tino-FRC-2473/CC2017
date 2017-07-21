package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberRun extends Command {
	double voltage = 12;
    public ClimberRun() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.CLIMBER);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    public boolean testMotor(){
    	if((Robot.CLIMBER.getVoltage("Left motor")/voltage) == (Robot.CLIMBER.getPower("Left motor")) || 
    			Robot.CLIMBER.getVoltage("Left motor")/voltage - (Robot.CLIMBER.getPower("Left motor")) <= (Robot.CLIMBER.getPower("Left motor"))*0.05 
    			|| (Robot.CLIMBER.getPower("Left motor")) - Robot.CLIMBER.getVoltage("Left motor")/voltage <= (Robot.CLIMBER.getPower("Left motor"))*0.05){
    		return true;
    	}
    	return false;
    }
    
}
