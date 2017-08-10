//package org.usfirst.frc.team2473.framework.diagnostic.commands;
//
//import org.usfirst.frc.team2473.framework.Database;
//import org.usfirst.frc.team2473.framework.components.Devices;
//import org.usfirst.frc.team2473.framework.components.Trackers;
//import org.usfirst.frc.team2473.robot.subsystems.BS;
//
//import com.ctre.CANTalon.TalonControlMode;
//
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Subsystem;
//
///**
// *
// */
//public class MotorDiagnoserCommand extends Command {
//	
//	private boolean done = false;
//	private Subsystem bs = new BS();
//	
//	private int deviceID;
//	private String keye;
//	private String keyp;
//	private double range;
//	private int direction;
//	private int testnum;
//    public MotorDiagnoserCommand(int deviceID, String keye, String keyp, double range, int direction) {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    	requires(bs);
//    	this.deviceID = deviceID;
//    	this.keye = keye;
//    	this.keyp = keyp;
//    	this.range = range;
//    	this.direction = direction;
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	if(range != 0){
//    		Trackers.getInstance().resetEncoders();
//    		System.out.println("encoder start(motor : " + deviceID + "): " + Math.abs(Database.getInstance().getNumeric(keye)));
//			while(Math.abs(Database.getInstance().getNumeric(keye)) <= Math.abs(range)){
//				if(range < 0){
//					if(Database.getInstance().getNumeric(keyp) != -0.1){
//						Devices.getInstance().getTalon(deviceID).set(-0.1);
//					}
//				}else{
//					if(Database.getInstance().getNumeric(keyp) != 0.1){
//						Devices.getInstance().getTalon(deviceID).set(0.1);
//					}
//				}
//			}
//			Devices.getInstance().getTalon(deviceID).set(0.0);
//			System.out.println("encoder end(motor : " + deviceID + "): " + Database.getInstance().getNumeric(keye));
//			if(Math.abs(Database.getInstance().getNumeric(keye)) >= Math.abs(range) + 50 || Math.abs(Database.getInstance().getNumeric(keye)) <= Math.abs(range) - 50){
//				System.out.println("Motor: " + deviceID + " Dysfunctional");
//			}else{
//				System.out.println("Motor: " + deviceID + " Functional");
//				testnum++;
//			}
//			Trackers.getInstance().resetEncoders();
//			done = true;
//    	}else{
//    		//System.out.println("motor running");
//	    	if(direction > 0){
//	    		while(!Devices.getInstance().getTalon(deviceID).isFwdLimitSwitchClosed()){
//		    		if(Database.getInstance().getNumeric(keyp) != 0.1){
//		    			Devices.getInstance().getTalon(deviceID).set(0.1);
//		    		}
//	    		}
//	    	}else{
//	    		while(!Devices.getInstance().getTalon(deviceID).isRevLimitSwitchClosed()){
//	    			if(Database.getInstance().getNumeric(keyp) != -0.1){
//		    			Devices.getInstance().getTalon(deviceID).set(-0.1);
//		    		}
//	    		}
//	    	}
//    		Devices.getInstance().getTalon(deviceID).set(0.0);
//    		System.out.println("Motor:" + deviceID + " triggered its limit switch and is functional.");
//    		testnum++;
//    		done = true;
//    	}
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return done;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    	System.out.println("One time diagnostic test completed for motor: " + deviceID + " completed.");
//    	System.out.println(testnum + "/1 tests passed.");
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
