package org.usfirst.frc.team2473.framework.diagnostic.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
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
	private String limitswitchkey;
	private double range;
	
    public MotorDiagnoserCommand(int deviceID, String keye, String keyp, double range, String limitswitchkey) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
    	this.deviceID = deviceID;
    	this.keye = keye;
    	this.keyp = keyp;
    	this.range = range;
    	this.limitswitchkey = limitswitchkey;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(range != (Double)null){
	    	Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.Position);
			Devices.getInstance().getTalon(deviceID).setPosition(0);
			Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.PercentVbus);
			while(Database.getInstance().getNumeric(keye) <= range){
				if(Database.getInstance().getNumeric(keyp) != 0.3){
					Devices.getInstance().getTalon(deviceID).set(0.3);
				}
			}
			Devices.getInstance().getTalon(deviceID).set(0.0);
			if(Database.getInstance().getNumeric(keye) >= range + 50 && Database.getInstance().getNumeric(keye) <= range - 50){
				System.out.println("Motor: " + deviceID + "Disfunctional");
			}
			Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.Position);
			Devices.getInstance().getTalon(deviceID).setPosition(0);
			Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.PercentVbus);
    	}else{
    		while(!Database.getInstance().getConditional(limitswitchkey)){
    			if(Database.getInstance().getNumeric(keyp) != 0.5){
    				Devices.getInstance().getTalon(deviceID).set(0.5);
    			}
    		}
    		Devices.getInstance().getTalon(deviceID).set(0.0);
    		System.out.println("If motor-" + deviceID + "-hit its maximum range, it is functional.");
    	}
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
