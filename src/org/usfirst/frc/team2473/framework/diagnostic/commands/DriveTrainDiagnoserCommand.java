//package org.usfirst.frc.team2473.framework.diagnostic.commands;
//
//
//import org.usfirst.frc.team2473.framework.Database;
//import org.usfirst.frc.team2473.framework.components.Devices;
//import org.usfirst.frc.team2473.framework.components.Trackers;
//import org.usfirst.frc.team2473.robot.DiagnosticMap;
//import org.usfirst.frc.team2473.robot.subsystems.BS;
//
//import com.ctre.CANTalon.TalonControlMode;
//
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Subsystem;
//
///**
// *
// */
//public class DriveTrainDiagnoserCommand extends Command {
//	
//	private Subsystem bs = new BS();
//	private boolean done = false;
//	
//	private String keyre;
//	private String keyfrp;
//	private double encoders;
//	private String keyle;
//	private String gyroangle;
//	private int fr;
//	private int fl;
//	private int br;
//	private int bl;
//	private int gyro;
//	
//	private int testnum;
//
//    public DriveTrainDiagnoserCommand(int fr, int fl, int br, int bl, int gyro, String keyre, String keyle, String keyfrp, double encoders, String gyroangle) {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    	requires(bs);
//    	this.fr = fr;
//    	this.fl = fl;
//    	this.br = br;
//    	this.bl = bl;
//    	this.gyro = gyro;
//    	this.keyfrp = keyfrp;
//    	this.keyre = keyre;
//    	this.keyle = keyle;
//    	this.encoders = encoders;
//    	this.gyroangle = gyroangle;
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	reset();
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	reset();
//		System.out.println("Turn both the left and the right wheel forward, until one rotation is completed.");
//		while(Math.abs(Database.getInstance().getNumeric(keyre)) <= DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION || Math.abs(Database.getInstance().getNumeric(keyle)) <= DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION){
//
//		}
//		System.out.println("FINAL Right Encoder Count: " + Database.getInstance().getNumeric(keyre) + "(should be around " + DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION + ")");
//		System.out.println("FINAL Left Encoder Count: " + Database.getInstance().getNumeric(keyle) + "(should be around " + DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION + ")");
//		System.out.println("If both wheels completed one rotation, the encoders are in good condition.");
//		if((Math.abs(Database.getInstance().getNumeric(keyre)) <= DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION + 100 
//		   && Math.abs(Database.getInstance().getNumeric(keyre)) >= DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION - 100) 
//		   && (Math.abs(Database.getInstance().getNumeric(keyle)) <= DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION + 100 
//		   && Math.abs(Database.getInstance().getNumeric(keyle)) >= DiagnosticMap.DRIVE_TRAIN_ENCODER_PER_ROTATION - 100)){
//			testnum++;
//		}
//		reset();
//		double time = System.currentTimeMillis();
//		while(System.currentTimeMillis() - time <= 5000) {
//		}
////		while(true){
////			if(Timer.Interface.hasPeriodPassed(5.0)){
////				
////			}
////		}
//		reset();
//		System.out.println("Enc left start: " + Database.getInstance().getNumeric(keyle));
//		System.out.println("Enc right start: " + Database.getInstance().getNumeric(keyre));
//		while(Math.abs(Database.getInstance().getNumeric(keyre)) <= encoders || Math.abs(Database.getInstance().getNumeric(keyle)) <= encoders){
//			if(Database.getInstance().getNumeric(keyfrp) != 0.1){
//				setPowerToALl(0.1);
//			}
//		}
//		setPowerToALl(0.0);
//		System.out.println("Right Enc FINAL" + Database.getInstance().getNumeric(keyre));
//    	System.out.println("Left Enc FINAL" + Database.getInstance().getNumeric(keyle));
//		if(Math.abs(Database.getInstance().getNumeric(keyre) + Database.getInstance().getNumeric(keyle))/2 <= encoders + 50 &&
//				Math.abs(Database.getInstance().getNumeric(keyre) + Database.getInstance().getNumeric(keyle))/2 >= encoders - 50){
//					System.out.println("Overall Drivetrain Status: Positive");
//		}else{
//			if(encoders - Math.abs(Database.getInstance().getNumeric(keyre)) < -50 || encoders - Math.abs(Database.getInstance().getNumeric(keyre)) > 50){
//				System.out.println("Right Encoder of Drive Train: Functional");
//				testnum++;
//			}else{
//				System.out.println("Right Encoder of Drive Train: Dysfunctional");
//			}
//			if(encoders - Math.abs(Database.getInstance().getNumeric(keyle)) < -50 || encoders - Math.abs(Database.getInstance().getNumeric(keyle)) > 50){
//				System.out.println("Left Encoder of Drive Train: Functional");
//				testnum++;
//			}else{
//				System.out.println("Left Encoder of Drive Train: Dysfunctional");
//			}
//		}
//		reset();
//		if(gyroangle != null){
//			System.out.println("Now, turn the robot  90 degrees");
//			while(Database.getInstance().getNumeric(gyroangle) <= 90){
//				
//			}
//			System.out.println("Gyro Angle: " + Database.getInstance().getNumeric(gyroangle));
//			System.out.println("If this looks like 90 degrees or more, the gyro is in good condition.");
//			if(Database.getInstance().getNumeric(gyroangle) >= 90){
//				testnum++;
//			}
//		}
//		done = true;
//    }
//    
//    private void reset(){
//		Devices.getInstance().getTalon(fr).changeControlMode(TalonControlMode.Position);
//		Devices.getInstance().getTalon(fl).changeControlMode(TalonControlMode.Position);
//		Devices.getInstance().getTalon(bl).changeControlMode(TalonControlMode.Position);
//		Devices.getInstance().getTalon(br).changeControlMode(TalonControlMode.Position);
//	
//		Devices.getInstance().getTalon(fr).setPosition(0);
//		Devices.getInstance().getTalon(fl).setPosition(0);
//		Devices.getInstance().getTalon(br).setPosition(0);
//		Devices.getInstance().getTalon(bl).setPosition(0);
//		
//		Devices.getInstance().getTalon(fr).changeControlMode(TalonControlMode.PercentVbus);
//		Devices.getInstance().getTalon(fl).changeControlMode(TalonControlMode.PercentVbus);
//		Devices.getInstance().getTalon(bl).changeControlMode(TalonControlMode.PercentVbus);
//		Devices.getInstance().getTalon(br).changeControlMode(TalonControlMode.PercentVbus);
//
//		if(gyroangle != null){
//			Trackers.getInstance().resetGyro();
//		}
//	}
//    
//    private void setPowerToALl(double pow){
//		Devices.getInstance().getTalon(fr).set(pow);
//		Devices.getInstance().getTalon(fl).set(pow);
//		Devices.getInstance().getTalon(br).set(pow);
//		Devices.getInstance().getTalon(bl).set(pow);
//	}
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return done;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    	System.out.println("One time diagnostic test for drive train completed.");
//    	if(gyroangle != null){
//    		System.out.println(testnum + "/4 tests passed.");
//    	}else{
//    		System.out.println(testnum + "/3 tests passed.");
//    	}
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
