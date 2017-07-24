package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.DigitalInput;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;

public class DigitalInputDiagnoser extends Diagnoser {
	private String digitalinputkey;
	private int trackedDeviceID;
	private String trackedDeviceEncoder;
	private double range;
	private type type;
	
	public DigitalInputDiagnoser(String digitalinputkey, double range, int trackedDeviceID, type type){
		this.digitalinputkey = digitalinputkey;
		this.range = range;
		this.type = type;
		this.trackedDeviceID = trackedDeviceID;
		if(type == type.LIMIT_SWITCH_MOTOR && (type != null)){
			for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
				if(tracker.getClass().getName().equals("EncoderTracker")) {
					trackedDeviceEncoder = ((EncoderTracker) tracker).getKey();
				}
		}
	}
	
	public enum type{
		LIMIT_SWITCH_SERVO,
		
		LIMIT_SWITCH_MOTOR
	}
	@Override
	public void RunOneTimeTest() {
		// TODO Auto-generated method stub
		if(type.equals(type.LIMIT_SWITCH_SERVO)){
			Devices.getInstance().getServo(trackedDeviceID).setPosition(range);
			if(!Database.getInstance().getConditional(digitalinputkey)){
				System.out.println("Limit Switch: " + digitalinputkey + " - not functional");
			}
		}
		if(type.equals(type.LIMIT_SWITCH_MOTOR)){
			while(Database.getInstance().getNumeric(trackedDeviceEncoder) < range){
				Devices.getInstance().getTalon(trackedDeviceID).set(0.2);
			}
			if(!Database.getInstance().getConditional(digitalinputkey)){
				System.out.println("Limit Switch: " + digitalinputkey + " - not functional");
			}
		}
	}

	@Override
	public void RunSimultaneousTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getMultiplier() {
		// TODO Auto-generated method stub
		return 1.0;
	}
}