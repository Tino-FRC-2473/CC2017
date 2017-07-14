package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearTele extends Command {

    public GearTele() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gear.initDefaultCommand();
    	Robot.gear.pickupTalon.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.gearPickupStick.getRawButton(RobotMap.gearpickupButtonVal) == true) {
    		Robot.gear.pickupTalon.set(0.5);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Robot.gear.pickupTalon.getEncPosition() >= RobotMap.gearpickupEncVal) {
        	return true;
        }
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
