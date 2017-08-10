package org.usfirst.frc.team2473.framework.diagnostic;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team2473.framework.diagnostic.commands.*;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.Diagnoser;

import com.ctre.CANTalon;

public class Diagnostics {
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private HashMap<String, Diagnoser> diagnosersmap = new HashMap<String,Diagnoser>();
	private DiagnosticCommands commands = new DiagnosticCommands();
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
				commands.addSequential(diagnoser.RunOneTimeTest());				
			}
		}
		if(type.equals(TestType.SIMULTANEOUS)){
			System.out.println("thread started");
			DiagnosticThread thread = new DiagnosticThread();
			thread.start();
		}
		if(type.equals(TestType.ONETIME)){
			commands.start();
		}
	}
	
	public double getMultiplier(String key){
		return diagnosersmap.get(key).getMultiplier();
	}
}
