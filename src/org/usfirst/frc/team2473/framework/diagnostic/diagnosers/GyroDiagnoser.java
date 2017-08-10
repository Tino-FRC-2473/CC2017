//package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;
//
//import org.usfirst.frc.team2473.framework.Database;
//import org.usfirst.frc.team2473.framework.components.Devices;
//import org.usfirst.frc.team2473.framework.components.Trackers;
//import org.usfirst.frc.team2473.framework.diagnostic.commands.*;
//import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
//import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
//import org.usfirst.frc.team2473.framework.trackers.ServoTracker;
//
//import edu.wpi.first.wpilibj.AnalogGyro;
//import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.command.Command;
//
//public class GyroDiagnoser extends Diagnoser{
//	
//	int trackedDeviceID;
//	String trackedDeviceValue;
//	String angleKey;
//	double maxDeviceValue;
//	String device;
//	//Type type;
//	double range;
//	
//	private Command command;
//	
//	public GyroDiagnoser(String angleKey, int trackedDeviceID, double gyroRange, Type type){
//		this.trackedDeviceID = trackedDeviceID;
//		this.angleKey = angleKey;
//		this.range = gyroRange;
//		if(type.equals(Type.MOTOR)){
//			for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
//				if(tracker.getClass().getName().indexOf("EncoderTracker") != -1 && tracker.getPort() == trackedDeviceID) {
//					trackedDeviceValue = ((EncoderTracker) tracker).getKey();
//				}
//			device = "Motor";
//		}
//		if(type.equals(Type.SERVO)){
//			for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
//				if(tracker.getClass().getName().indexOf("ServoTracker") != -1 && tracker.getPort() == trackedDeviceID) {
//					trackedDeviceValue = ((ServoTracker) tracker).getKey();
//				}
//			device = "Servo";
//		}
//		//this.type = type;
//		//Diagnostics.addToQueue(this);
//	}
//	
//	public enum Type{
//		MOTOR,
//		
//		SERVO
//	}
//
//	@Override
//	public void RunSimultaneousTest() {
////		if(type.equals(Type.SERVO)){
////
////		}else if(type.equals(Type.MOTOR)){
////			
////		}
//		if(Database.getInstance().getNumeric(angleKey) <= range + 2 && Database.getInstance().getNumeric(angleKey) >= range - 2){
//			maxDeviceValue = Database.getInstance().getNumeric(trackedDeviceValue);
//		}
//		if(maxDeviceValue != 0){
//			if(Database.getInstance().getNumeric(trackedDeviceValue)/maxDeviceValue <= (Database.getInstance().getNumeric(angleKey)/range - 0.05)
//			|| Database.getInstance().getNumeric(trackedDeviceValue)/maxDeviceValue >= (Database.getInstance().getNumeric(angleKey)/range + 0.05)){
//				System.out.println("gyro: " + trackedDeviceID + " - Disfunctional");
//			}
//		}
//	}
//
//	@Override
//	public Command RunOneTimeTest() {
//		return new GyroDiagnoserCommand(trackedDeviceID,angleKey,range,device);
//	}
//	@Override
//	public double getMultiplier(){
//		return 1.0;
//	}
//}
