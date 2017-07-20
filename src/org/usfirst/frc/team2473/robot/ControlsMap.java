package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team2473.framework.readers.Parser;

/**
 * Class stores constants for devices installed on the drive station and respective access keys. These constants can be value limits, joystick numbers, button numbers, and more.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class ControlsMap {
	private List<String> all = new ArrayList<String>();
	public final String JOY1 = "joystick;0;x-axis;y-axis";
	public final String JOY1_BTN1 = "button;0/1";
	
	private static ControlsMap theInstance;
	
	static {
		theInstance = new ControlsMap();
	}
	
	private ControlsMap() {
		all = Arrays.asList(JOY1, JOY1_BTN1);
	}
	
	public static ControlsMap getInstance() {
		return theInstance;
	}		
	
	public void execute() {
		for(String control : all) Parser.parseDevice(control);
	}
}