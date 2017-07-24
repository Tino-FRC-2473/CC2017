package org.usfirst.frc.team2473.robot;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;

public class GyroDiagnoser extends Diagnoser{
	
	int deviceID;
	String trackedDeviceValue;
	String angleKey;
	Double maxDeviceValue = null;
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
			maxDeviceValue = Database.getInstance().getNumeric(trackedDeviceValue);
		}
		if(maxDeviceValue != null){
			if(Database.getInstance().getNumeric(trackedDeviceValue)/maxDeviceValue <= (Database.getInstance().getNumeric(angleKey)/range - 0.05)
			|| Database.getInstance().getNumeric(trackedDeviceValue)/maxDeviceValue >= (Database.getInstance().getNumeric(angleKey)/range + 0.05)){
				System.out.println("gyro: " + deviceID + "-not callibrated");
			}
		}
	}

	@Override
	public void RunOneTimeTest() {
		Devices.getInstance().getGyro(deviceID).reset();
		System.out.println("Turn the gyro 90 degrees");
		while(Database.getInstance().getNumeric(angleKey) <= range){
			System.out.println("Gyro: " + deviceID + " Angle: " + Database.getInstance().getNumeric(angleKey));
		}
		System.out.println("STOP! If this looks like " + range + " degrees, the gyro is working.");
	}
	@Override
	public double getMultiplier(){
		return 1.0;
	}
}
