package org.usfirst.frc.team2473.framework.diagnostic.commands;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.ServoDiagnoser.Type;
import org.usfirst.frc.team2473.robot.subsystems.BS;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ServoDiagnoserCommand extends Command {
	int limitswitch;
	int id;
	double range;
	BS bs = new BS();
	boolean done = false;
	Type type;
    public ServoDiagnoserCommand(int id, double range, Type type) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.range = range;
    	this.id = id;
    	this.type = type;
    	requires(bs);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
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
