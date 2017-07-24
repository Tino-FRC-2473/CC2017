package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team2473.robot.commands.ManualTestCommand;
import org.usfirst.frc.team2473.robot.commands.ManualTestCommands;

import com.ctre.CANTalon;

public class Diagnostics {
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private HashMap<String, Diagnoser> diagnosersmap = new HashMap<String,Diagnoser>();
	//private ManualTestCommands command;
	
	private static Diagnostics theInstance;

	static {
		theInstance = new Diagnostics();
	}
	
	private Diagnostics() {
		
	}

	public static Diagnostics getInstance() {
		return theInstance;
	}
	
	public void addToQueue(String key, Diagnoser diagnoser){
		diagnosersmap.put(key, diagnoser);
		diagnosers.add(diagnoser);
	}
	public enum TestType{
		SIMULTANEOUS,ONETIME
	}
	public void startTests(TestType type){
		for(Diagnoser diagnoser : diagnosers){
			if(type.equals(TestType.SIMULTANEOUS)){
				DiagnosticThread.getInstance().addToList(diagnoser);
			}
			if(type.equals(TestType.ONETIME)){
				diagnoser.RunOneTimeTest();
				diagnosers.remove(diagnoser);				
			}
		}
		DiagnosticThread thread = new DiagnosticThread();
		thread.start();
	}
	
	public double getMultiplier(String key){
		return diagnosersmap.get(key).getMultiplier();
	}
}
