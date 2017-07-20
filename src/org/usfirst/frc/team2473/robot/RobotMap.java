package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team2473.framework.readers.Parser;

/**
 * Class stores constants for devices installed on the robot and respective access keys. These constants can be value limits, port numbers, device ids, and more.
 * @author Deep Sethi
 * @author Harmony He
 * @version 2.0
 * */
public class RobotMap {
	private List<String> all = new ArrayList<String>();
	public final String TALON_ONE = "TALON;4;current;voltage;power;encoders";
	public final String ANALOG_ONE = "ANALOG;1";
	
	private static RobotMap theInstance;
	
	static {
		theInstance = new RobotMap();
	}
	
	private RobotMap() {
		all = Arrays.asList(TALON_ONE, ANALOG_ONE);
	}
	
	public static RobotMap getInstance() {
		return theInstance;
	}
	
	public void execute() {
		for(String deviceSpecs : all) Parser.parseDevice(deviceSpecs);
	}
}