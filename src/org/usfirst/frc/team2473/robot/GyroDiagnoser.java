package org.usfirst.frc.team2473.robot;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;

public class GyroDiagnoser extends Diagnoser{
	
	int deviceID;
	String rightEncoder;
	String leftEncoder;
	String angleKey;
	Type type;
	
	public GyroDiagnoser(int deviceID, String angleKey){
		this.deviceID = deviceID;
		this.angleKey = angleKey;
		this.type = Type.ARM;
		Diagnostics.addToQueue(this);
	}
	
	public GyroDiagnoser(int deviceID, String angleKey, String rightEncoderKey, String leftEncoderKey){
		this.leftEncoder = leftEncoderKey;
		this.rightEncoder = rightEncoderKey;
		this.deviceID = deviceID;
		this.angleKey = angleKey;
		this.type = Type.DRIVE_TRAIN;
		Diagnostics.addToQueue(this);
	}
	
	public enum Type{
		ARM,
		
		DRIVE_TRAIN
	}

	@Override
	public void RunSimultaneousTest() {
		if(type.equals(Type.ARM)){
//			if((Database.getInstance().getNumeric(leftEncoder)-Database.getInstance().getNumeric(rightEncoder))){
//			
//			}
		}else if(type.equals(Type.DRIVE_TRAIN)){
			
		}
	}

	@Override
	public void RunOneTimeTest() {
		Devices.getInstance().getGyro(deviceID).reset();
		System.out.println("Turn the gyro 90 degrees");
		while(Database.getInstance().getNumeric(angleKey) <= 90){
			System.out.println("Gyro: " + deviceID + " Angle: " + Database.getInstance().getNumeric(angleKey));
		}
		System.out.println("STOP! If this looks like 90 degrees, the gyro is working.");
	}

}
