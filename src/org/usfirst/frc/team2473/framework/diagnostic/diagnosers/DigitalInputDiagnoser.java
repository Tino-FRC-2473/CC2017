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
	private Type type;
	
	private Command command;
	
	public DigitalInputDiagnoser(String digitalinputkey, Type type){
		this.digitalinputkey = digitalinputkey;
		this.type = type;
	}
	
	public enum Type{
		LIMIT_SWITCH,
		
		BREAKBEAM,
		
		ROTARY_SWITCH
	}
	@Override
	public Command RunOneTimeTest() {
		// TODO Auto-generated method stub
		return new DigitalInputDiagnoserCommand(type, digitalinputkey);
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