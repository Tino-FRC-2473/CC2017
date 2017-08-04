package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
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
    	Robot.gear.resetEncoders();
    	Devices.getInstance().getTalon(RobotMap.GEAR_PICKUP_MOTOR).set(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Devices.getInstance().getTalon(RobotMap.GEAR_PICKUP_MOTOR).getEncPosition() >= RobotMap.gearpickupEncVal) {
        	return true;
        }
        return false;
        //System.out.println(Database.getInstance().getNumeric(RobotMap.gearPickupTalonCurrent));
        //return Database.getInstance().getNumeric(RobotMap.Gear_Pickup_Enc) >= RobotMap.gearpickupEncVal;
//    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Devices.getInstance().getTalon(RobotMap.GEAR_PICKUP_MOTOR).set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
