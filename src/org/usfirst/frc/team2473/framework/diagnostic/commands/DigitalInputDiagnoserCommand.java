package org.usfirst.frc.team2473.framework.diagnostic.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type;
import org.usfirst.frc.team2473.robot.subsystems.BS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DigitalInputDiagnoserCommand extends Command {
	
	private Subsystem bs = new BS();
	private boolean done = false;
	private org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type type;
	private int trackedDeviceID;
	private String digitalinputkey;
	private String trackedDeviceEncoder;
	private double range;

    public DigitalInputDiagnoserCommand(org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type type, int trackedDeviceID, String digitalinputkey, String trackedDeviceEncoder, double range) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
		this.digitalinputkey = digitalinputkey;
		this.range = range;
		this.trackedDeviceEncoder = trackedDeviceEncoder;
		this.trackedDeviceID = trackedDeviceID;
		this.type = type;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(type){
		case LIMIT_SWITCH_SERVO:
			Devices.getInstance().getServo(trackedDeviceID).setPosition(range);
			if(!Database.getInstance().getConditional(digitalinputkey)){
				System.out.println("Limit Switch(SERVO): " + digitalinputkey + " - not functional");
			}
			break;
		case LIMIT_SWITCH_MOTOR:
			while(Database.getInstance().getNumeric(trackedDeviceEncoder) < range){
				Devices.getInstance().getTalon(trackedDeviceID).set(0.2);
			}
			if(!Database.getInstance().getConditional(digitalinputkey)){
				System.out.println("Limit Switch(MOTOR): " + digitalinputkey + " - not functional");
			}
			break;
		case BREAKBEAM:
			
			break;
		case ROTARY_SWITCH:
			
			break;
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
