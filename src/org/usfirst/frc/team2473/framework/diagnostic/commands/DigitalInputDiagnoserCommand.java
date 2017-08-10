package org.usfirst.frc.team2473.framework.diagnostic.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type;
import org.usfirst.frc.team2473.robot.subsystems.BS;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DigitalInputDiagnoserCommand extends Command {
	
	private Subsystem bs = new BS();
	private boolean done = false;
	private org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type type;
	private String digitalinputkey;

public DigitalInputDiagnoserCommand(org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type type, String digitalinputkey) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
		this.digitalinputkey = digitalinputkey;
		this.type = type;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(type){
		case LIMIT_SWITCH:
			System.out.println("Press limitswitch: " + digitalinputkey);
			while(Database.getInstance().getConditional(digitalinputkey)){
				
			}
			System.out.println("The limitswitch responded accordingly.");
			done = true;
			break;
		case BREAKBEAM:
			System.out.println("Wave your hand infront of breakbeam: " + digitalinputkey);
			while(!Database.getInstance().getConditional(digitalinputkey)){
				
			}
			System.out.println("The breakbeam responded accordingly.");
			done = true;
			break;
		case ROTARY_SWITCH:
			done = true;
			break;
		}
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
