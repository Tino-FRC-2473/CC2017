	package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import com.ctre.CANTalon;
import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.diagnostic.DiagnosticThread;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.robot.DiagnosticMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Servo;

public class MotorDiagnoser extends Diagnoser{
	//constructor values
	private String speedKey; //speed
	private String currentKey; //current
	private String encoderKey; //encoder
	private String powerKey; //power
	private int deviceID; //device id
	private double range;
	private Type type; 
	
	//torque calculations
	private double rpm;
	private double torque;
	
	//speed multiplier
	private double SpeedMultiplier;
	
	//time
	//private double time;
	//motor constants
	//private final double MAX_TORQUE = 0.03;
//	private final double MAX_CURRENT = 10.0;
//	private final double EcnoderTicksPerRotation = 6000.0;
//	private final double GearRatio = 14;
	
	public MotorDiagnoser(int deviceID, double range, Type type){
		this.deviceID = deviceID;
		this.range = range;
		this.type = type;
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) {
			if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort()==deviceID) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					powerKey = tracker.getKey();
					break;
				case CURRENT:
					currentKey = tracker.getKey();
					break;
				case SPEED:
					speedKey = tracker.getKey();
					break;
				default:
						break;
				}
			} else if(tracker.getClass().getName().indexOf("EncoderTracker") != -1 && tracker.getPort()==deviceID) {
				encoderKey = ((EncoderTracker) tracker).getKey();
			}
		}
		//Diagnostics.addToQueue(this);
	}
	
	public enum Type{
		M775
	}
	
	@Override
	public void RunSimultaneousTest() {
		System.out.println("simul test running");
		double pastrpm;
		double current = (Database.getInstance().getNumeric(currentKey));
		if(DiagnosticThread.getInstance().getTime()%1000 == 0){
			if(type.equals(Type.M775)){
				pastrpm = rpm;
				rpm = (((Database.getInstance().getNumeric(speedKey)*600))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
				torque = (rpm - pastrpm);
			}
		}
		if(type.equals(Type.M775)){
			if((torque >= DiagnosticMap.MAX_TORQUE775) || (current >= DiagnosticMap.MAX_CURRENT775)){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
		}
	}

	@Override
	public void runOneTimeTest() {
		reset();
		while(Database.getInstance().getNumeric(encoderKey) <= range){
			if(Database.getInstance().getNumeric(powerKey) != 0.3){
				Devices.getInstance().getTalon(deviceID).set(0.3);
			}
		}
		if(Database.getInstance().getNumeric(encoderKey) <= range + 50 && Database.getInstance().getNumeric(encoderKey) >= range - 50){
			System.out.println("Motor: " + deviceID + "Dysfunctional");
		}
		reset();
		System.out.println("Turn motor-driven wheel manually in any direction");
		while(Math.abs(Database.getInstance().getNumeric(encoderKey)) <= range){
				System.out.println("Motor: " + deviceID + "Encoder Count: " + Database.getInstance().getNumeric(encoderKey));
		}
		System.out.println("STOP! If this is as far as the motor goes, everything is working.");
	}
	
	private void reset(){
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) if(tracker.getKey().equals(encoderKey)) ((EncoderTracker)tracker).resetEncoder();
	}
	
	public double getMultiplier(){
		return SpeedMultiplier;
	}

}