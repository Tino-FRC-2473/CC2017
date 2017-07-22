package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;

public class GyroDiagnoser extends Diagnoser{
	
	int deviceID;
	String trackedDeviceValue;
	String angleKey;
	//Type type;
	double range;
	
	public GyroDiagnoser(int deviceID, String angleKey, String trackedDeviceValue, double gyroRange){
		this.deviceID = deviceID;
		this.angleKey = angleKey;
		this.range = gyroRange;
		this.trackedDeviceValue = trackedDeviceValue;
		//this.type = type;
		//Diagnostics.addToQueue(this);
	}
	
//	public enum Type{
//		MOTOR,
//		
//		SERVO
//	}

	@Override
	public void RunSimultaneousTest() {
//		if(type.equals(Type.SERVO)){
//
//		}else if(type.equals(Type.MOTOR)){
//			
//		}
		if(Database.getInstance().getNumeric(angleKey) <= range + 5 || Database.getInstance().getNumeric(angleKey) >= range - 5){
			
		}
	}

	@Override
	public void runOneTimeTest() {
		Devices.getInstance().getGyro(deviceID).reset();
		System.out.println("Turn the gyro 90 degrees");
		while(Database.getInstance().getNumeric(angleKey) <= 90){
			System.out.println("Gyro: " + deviceID + " Angle: " + Database.getInstance().getNumeric(angleKey));
		}
		System.out.println("STOP! If this looks like 90 degrees, the gyro is working.");
	}

}