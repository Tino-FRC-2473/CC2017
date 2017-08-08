package org.usfirst.frc.team2473.framework.diagnostic.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.robot.subsystems.BS;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MotorDiagnoserCommand extends Command {
	
	private boolean done = false;
	private Subsystem bs = new BS();
	
	private int deviceID;
	private String keye;
	private String keyp;
	private double range;
	private int direction;
    public MotorDiagnoserCommand(int deviceID, String keye, String keyp, double range, int direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
    	this.deviceID = deviceID;
    	this.keye = keye;
    	this.keyp = keyp;
    	this.range = range;
    	this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(range != 0){
    		Trackers.getInstance().resetEncoders();
    		System.out.println(Math.abs(Database.getInstance().getNumeric(keye)));
			while(Math.abs(Database.getInstance().getNumeric(keye)) <= Math.abs(range)){
				System.out.println("encoder: " + Database.getInstance().getNumeric(keye));
				if(range < 0){
					if(Database.getInstance().getNumeric(keyp) != -0.3){
						Devices.getInstance().getTalon(deviceID).set(-0.3);
					}
				}else{
					if(Database.getInstance().getNumeric(keyp) != 0.3){
						Devices.getInstance().getTalon(deviceID).set(0.3);
					}
				}
			}
			Devices.getInstance().getTalon(deviceID).set(0.0);
			if(Database.getInstance().getNumeric(keye) >= range + 50 || Database.getInstance().getNumeric(keye) <= range - 50){
				System.out.println("Motor: " + deviceID + " Disfunctional");
			}else{
				System.out.println("Motor: " + deviceID + " Functional");
			}
			Trackers.getInstance().resetEncoders();
			done = true;
    	}else{
    		//System.out.println("motor running");
	    	if(direction > 0){
	    		while(!Devices.getInstance().getTalon(deviceID).isFwdLimitSwitchClosed()){
		    		if(Database.getInstance().getNumeric(keyp) != 0.3){
		    			Devices.getInstance().getTalon(deviceID).set(0.3);
		    		}
	    		}
	    	}else{
	    		while(!Devices.getInstance().getTalon(deviceID).isRevLimitSwitchClosed()){
	    			if(Database.getInstance().getNumeric(keyp) != -0.3){
		    			Devices.getInstance().getTalon(deviceID).set(-0.3);
		    		}
	    		}
	    	}
    		Devices.getInstance().getTalon(deviceID).set(0.0);
    		System.out.println("Motor:" + deviceID + " hit its max range and is functional.");
    		done = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Motor diagnostic test functional...");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
