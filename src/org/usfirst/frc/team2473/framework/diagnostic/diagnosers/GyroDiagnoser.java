package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.diagnostic.commands.*;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

public class GyroDiagnoser extends Diagnoser{
	
	int deviceID;
	String trackedDeviceValue;
	String angleKey;
	Double maxDeviceValue = null;
	//Type type;
	double range;
	
	private Command command;
	
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
		if(Database.getInstance().getNumeric(angleKey) <= range + 5 && Database.getInstance().getNumeric(angleKey) >= range - 5){
			maxDeviceValue = Database.getInstance().getNumeric(trackedDeviceValue);
		}
		if(maxDeviceValue != null){
			if(Database.getInstance().getNumeric(trackedDeviceValue)/maxDeviceValue <= (Database.getInstance().getNumeric(angleKey)/range - 0.05)
			|| Database.getInstance().getNumeric(trackedDeviceValue)/maxDeviceValue >= (Database.getInstance().getNumeric(angleKey)/range + 0.05)){
				System.out.println("gyro: " + deviceID + " - Disfunctional");
			}
		}
	}

	@Override
	public Command RunOneTimeTest() {
		return new GyroDiagnoserCommand(deviceID,angleKey,range);
	}
	@Override
	public double getMultiplier(){
		return 1.0;
	}
}
