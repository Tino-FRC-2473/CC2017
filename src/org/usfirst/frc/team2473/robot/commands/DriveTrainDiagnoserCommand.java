package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.DiagnosticMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrainDiagnoserCommand extends Command {
	
	private Subsystem bs;
	private boolean done = false;
	
	private String keyre;
	private String keyfrp;
	private double encoders;
	private String keyle;
	private String gyroangle;
	private int fr;
	private int fl;
	private int br;
	private int bl;
	private int gyro;

    public DriveTrainDiagnoserCommand(int fr, int fl, int br, int bl, int gyro, String keyre, String keyle, String keyfrp, double encoders, String gyroangle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(bs);
    	this.fr = fr;
    	this.fl = fl;
    	this.br = br;
    	this.bl = bl;
    	this.gyro = gyro;
    	this.keyfrp = keyfrp;
    	this.keyre = keyre;
    	this.keyle = keyle;
    	this.encoders = encoders;
    	this.gyroangle = gyroangle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	reset();
		while(Database.getInstance().getNumeric(keyre) <= encoders || Database.getInstance().getNumeric(keyre) <= encoders){
			if(Database.getInstance().getNumeric(keyfrp) != 0.5){
				setPowerToALl(0.5);
			}
		}
		setPowerToALl(0.0);
		if((Database.getInstance().getNumeric(keyre) + Database.getInstance().getNumeric(keyle))/2 > encoders + 50 &&
				(Database.getInstance().getNumeric(keyre) + Database.getInstance().getNumeric(keyle))/2 < encoders - 50){
					System.out.println("Overall Drivetrain Status: Positive");
		}else{
			if(encoders - Database.getInstance().getNumeric(keyre) < -50 || encoders - Database.getInstance().getNumeric(keyre) > 50){
				System.out.println("Right Encoder: Functional");
			}else{
				System.out.println("Right Encoder: Disfunctional");
			}
			if(encoders - Database.getInstance().getNumeric(keyle) < -50 || encoders - Database.getInstance().getNumeric(keyle) > 50){
				System.out.println("Left Encoder: Functional");
			}else{
				System.out.println("Left Encoder: Disfunctional");
			}
		}
		reset();
		System.out.println("Turn both the left and the right wheel forward.");
		while(Database.getInstance().getNumeric(keyre) <= DiagnosticMap.ENCODER_PER_ROTATION775 && Database.getInstance().getNumeric(keyle) <= DiagnosticMap.ENCODER_PER_ROTATION775){
			System.out.println("Right Encoder Count: " + Database.getInstance().getNumeric(keyre));
			System.out.println("Right Encoder Count: " + Database.getInstance().getNumeric(keyle));
		}
		System.out.println("If both wheels completed one rotation, the encoders are in good condition.");
		reset();
		System.out.println("Now, turn the robot  90 degrees");
		while(Database.getInstance().getNumeric(gyroangle) <= 90){
			System.out.println("Gyro Angle: " + Database.getInstance().getNumeric(gyroangle));
		}
		System.out.println("If this looks like 90 degrees, the gyro is in good condition.");
		done = true;
    }
    
    private void reset(){
		Devices.getInstance().getTalon(fr).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(fl).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(bl).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(br).changeControlMode(TalonControlMode.Position);
	
		Devices.getInstance().getTalon(fr).setPosition(0);
		Devices.getInstance().getTalon(fl).setPosition(0);
		Devices.getInstance().getTalon(br).setPosition(0);
		Devices.getInstance().getTalon(bl).setPosition(0);
		
		Devices.getInstance().getTalon(fr).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(fl).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(bl).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(br).changeControlMode(TalonControlMode.PercentVbus);
		
		Devices.getInstance().getGyro(gyro).reset();
	}
    
    private void setPowerToALl(double pow){
		Devices.getInstance().getTalon(fr).set(pow);
		Devices.getInstance().getTalon(fl).set(pow);
		Devices.getInstance().getTalon(br).set(pow);
		Devices.getInstance().getTalon(bl).set(pow);
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
