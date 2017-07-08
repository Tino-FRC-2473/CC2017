package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickControl extends Command {

	double power = Robot.oi.throttle.getThrottle();
	
    public JoystickControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	//not sure what value getTwist() returns
    	if(-0.5 <= Robot.oi.wheel.getTwist() && Robot.oi.wheel.getTwist() <= 0.5){
    		//forwards and backwards
    		Robot.driveTrain.goForward(Robot.oi.throttle.getThrottle());
    	}
    	
    	//not sure what value getTwist() returns
    	else if(Robot.oi.wheel.getTwist() > 0.5){
    		//turns right
    		if(Robot.oi.throttle.getThrottle() > 0.05){
    			Robot.driveTrain.turnRight(Robot.oi.throttle.getThrottle());
    		}
    		else if(Robot.oi.wheel.getTwist() < -0.05){
    			Robot.driveTrain.turnLeft(Robot.oi.throttle.getThrottle());
    		}
    		else {
    			Robot.driveTrain.stop();
    		}
    	}
    	
    	//not sure what value getTwist() returns
    	else if(Robot.oi.wheel.getTwist() < -0.5){
    		//turns left
    		if(Robot.oi.throttle.getThrottle() > 0.05){
    			Robot.driveTrain.turnLeft(Robot.oi.throttle.getThrottle());
    		}
    		else if(Robot.oi.wheel.getTwist() < -0.05){
    			Robot.driveTrain.turnRight(Robot.oi.throttle.getThrottle());
    		}
    		else {
    			Robot.driveTrain.stop();
    		}
    	}
    	
    	else{
        	Robot.driveTrain.stop();
        }
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
}
