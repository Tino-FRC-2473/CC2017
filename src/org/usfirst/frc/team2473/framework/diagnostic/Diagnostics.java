package org.usfirst.frc.team2473.framework.diagnostic;

import java.util.ArrayList;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.Diagnoser;

public class Diagnostics {
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private static Diagnostics theInstance;

	static {
		theInstance = new Diagnostics();
	}
	
	private Diagnostics() {
		
	}

	public static Diagnostics getInstance() {
		return theInstance;
	}

	public void addToQueue(Diagnoser diagnoser){
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
				diagnoser.runOneTimeTest();
				diagnosers.remove(diagnoser);
			}
		}
		if(type.equals(TestType.SIMULTANEOUS)) DiagnosticThread.getInstance().start();
	}
}