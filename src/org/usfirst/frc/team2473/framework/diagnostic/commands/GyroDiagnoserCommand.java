//package org.usfirst.frc.team2473.framework.diagnostic.commands;
//
//import org.usfirst.frc.team2473.framework.Database;
//import org.usfirst.frc.team2473.framework.components.Devices;
//import org.usfirst.frc.team2473.robot.subsystems.BS;
//
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Subsystem;
//
///**
// *
// */
//public class GyroDiagnoserCommand extends Command {
//	
//	private Subsystem bs = new BS();
//	private int deviceID;
//	private String angleKey;
//	private double range;
//	private String device;
//	
//	private boolean done = false;
//
//    public GyroDiagnoserCommand(int deviceID, String angleKey, double range, String device) {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    	requires(bs);
//    	this.deviceID = deviceID;
//    	this.angleKey = angleKey;
//    	this.range = range;
//    	this.device = device;
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	if(device.equals("Motor")){
//    		while(Math.abs(Database.getInstance().getNumeric(angleKey)) <= Math.abs(range)){
//    			if(range < 0){
//    				Devices.getInstance().getTalon(deviceID).set(-0.3);
//    			}else{
//    				Devices.getInstance().getTalon(deviceID).set(0.3);
//    			}
//    			System.out.println("Gyro Angle: " + Database.getInstance().getNumeric(angleKey));
//    		}
//    		System.out.println("Gyro - Functional");
//    	}
//    	if(device.equals("Motor")){
//    		
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
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
