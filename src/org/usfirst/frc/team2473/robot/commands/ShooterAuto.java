package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterAuto extends Command {
	

    public ShooterAuto() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.shooter.initDefaultCommand();
    	Robot.shooter.resetEncoders();
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_ONE).set(0.5);
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_TWO).set(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if (Database.getInstance().getNumeric(RobotMap.climberTalonOneCurrent) >= RobotMap.shooterEncVal){
//    		Robot.shooter.setPowerIntake(0.5);
//    		Robot.shooter.setPowerShoot(0.5);
//    	}
//Commented because power is different from current. Fix this.
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.shooter.getTalonOneEncoder() >= 6000.0) {
        	return true;
        }
    	return false;
    	
//    	return Database.getInstance().getNumeric(RobotMap.Shooter_Enc) >= RobotMap.shooterEncVal;
    	
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.setPowerIntake(0);
    	Robot.shooter.setPowerShoot(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
