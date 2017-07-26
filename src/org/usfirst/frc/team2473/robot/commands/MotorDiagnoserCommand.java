package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MotorDiagnoserCommand extends Command {
	
	private boolean done = false;
	private Subsystem bs;
	
	private int deviceID;
	private String keye;
	private String keyp;
	private double range;
	
    public MotorDiagnoserCommand(int deviceID, String keye, String keyp, double range) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
    	this.deviceID = deviceID;
    	this.keye = keye;
    	this.keyp = keyp;
    	this.range = range;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(deviceID).setPosition(0);
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.PercentVbus);
		
		while(Database.getInstance().getNumeric(keye) <= range){
			if(Database.getInstance().getNumeric(keyp) != 0.3){
				Devices.getInstance().getTalon(deviceID).set(0.3);
			}
		}
		if(Database.getInstance().getNumeric(keye) <= range + 50 && Database.getInstance().getNumeric(keye) >= range - 50){
			System.out.println("Motor: " + deviceID + "Disfunctional");
		}
		
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(deviceID).setPosition(0);
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.PercentVbus);
		
		System.out.println("Start Turning Motor: " + deviceID + ", mannually");
		while(Math.abs(Database.getInstance().getNumeric(keye)) <= range){
				System.out.println("Motor: " + deviceID + "Encoder Count: " + Database.getInstance().getNumeric(keye));
		}
		System.out.println("STOP! If this is as far as the motor goes, everything is working.");
		done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
