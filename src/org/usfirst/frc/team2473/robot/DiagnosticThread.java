package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;
import java.util.HashMap;

public class DiagnosticThread extends Thread{
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private HashMap<String, String> errors = new HashMap<String, String>();
	
	public double initialTime;
	
	private static DiagnosticThread theInstance;
	
	static {
		theInstance = new DiagnosticThread();
	}
	
	public static DiagnosticThread getInstance() {
		return theInstance;
	}

	
	public DiagnosticThread(){
		initialTime = System.currentTimeMillis();
	}
	
	public void addToList(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}
	
	public void run(){
		while(isAlive()){
			for(Diagnoser diagnoser:diagnosers){
				diagnoser.RunSimultaneousTest();
			}
			errors();
		}
	}
	
	private void errors(){
		
	}
	
	public double getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}
