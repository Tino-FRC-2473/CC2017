package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.subsystems.BS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GyroDiagnoserCommand extends Command {
	
	private Subsystem bs = new BS();
	private int deviceID;
	private String angleKey;
	private double range;
	
	private boolean done = false;

    public GyroDiagnoserCommand(int deviceID, String angleKey, double range) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
    	this.deviceID = deviceID;
    	this.angleKey = angleKey;
    	this.range = range;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Devices.getInstance().getGyro(deviceID).reset();
		System.out.println("Turn the gyro to its range.");
		while(Database.getInstance().getNumeric(angleKey) <= range){
			System.out.println("Gyro: " + deviceID + " Angle: " + Database.getInstance().getNumeric(angleKey));
		}
		System.out.println("STOP! If this looks like " + range + " degrees, the gyro is working.");
		done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
