package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.framework.diagnostic.commands.*;

public class DigitalInputDiagnoser extends Diagnoser {
	private String digitalinputkey;
	private int trackedDeviceID;
	private String trackedDeviceEncoder;
	private double range;
	private Type type;
	
	private Command command;
	
	public DigitalInputDiagnoser(String digitalinputkey, double range, int trackedDeviceID, Type type){
		this.digitalinputkey = digitalinputkey;
		this.range = range;
		this.type = type;
		this.trackedDeviceID = trackedDeviceID;
		if((type != null) && type == type.LIMIT_SWITCH){
			for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
				if(tracker.getClass().getName().indexOf("EncoderTracker") != -1 && tracker.getPort() == trackedDeviceID) {
					trackedDeviceEncoder = ((EncoderTracker) tracker).getKey();
				}
		}
	}
	
	public enum Type{
		LIMIT_SWITCH,
		
		BREAKBEAM,
		
		ROTARY_SWITCH
	}
	@Override
	public Command RunOneTimeTest() {
		// TODO Auto-generated method stub
		return new DigitalInputDiagnoserCommand(type,trackedDeviceID,digitalinputkey,trackedDeviceEncoder,range);
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