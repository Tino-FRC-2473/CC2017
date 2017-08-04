package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Controls;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.robot.ControlsMap;
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
//    	Robot.climber.initDefaultCommand();
//    	Robot.climber.resetEncoders();
    	
    	Robot.climber.resetEncoders();
		Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_ONE).set(0.5);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_TWO).set(0.5);
		//Moved this from execute to here.
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	return (Robot.climber.switchVal() ||
    			!Controls.getInstance().getButton(ControlsMap.Joy_ZERO_Climber_EncVal).get());
//    		return true;
    		//Should this be in isFinished or End instead of here.
    		//Moved here from execute.
    	
    
//		return false;
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_ONE).set(0);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_TWO).set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
