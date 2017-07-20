package org.usfirst.frc.team2473.robot.commands;

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
    		Robot.driveTrain.driveArcade(Robot.oi.squareWithSign(Robot.oi.throttle.getZ()),Robot.oi.wheel.getTwist());
			System.out.println(Robot.oi.throttle.getZ());
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