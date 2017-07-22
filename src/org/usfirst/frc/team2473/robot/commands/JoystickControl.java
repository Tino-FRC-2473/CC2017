package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.robot.ControlsMap;
import org.usfirst.frc.team2473.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickControl extends Command {
	private double prevThrottleVal = 0;
	private double dvr = 0;
	private double dvl = 0;
    public JoystickControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("CMD INIT");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//(Robot.driveTrain.getRightPow() ) && (Robot.oi.throttle.getThrottle() <= 0.05 && Robot.oi.throttle.getThrottle() >= -0.05)
    		Robot.driveTrain.driveArcade(squareWithSign(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Y)),Database.getInstance().getNumeric(ControlsMap.STEERING_WHEEL_X));
			System.out.println(Database.getInstance().getNumeric(ControlsMap.THROTTLE_Y));
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
    private double squareWithSign(double d){
		if(d>=0.04){
			return -d*d;
		}
		else if(d<=-0.04){
			return d*d;
		}
		else{
			return 0;
		}
    }
}