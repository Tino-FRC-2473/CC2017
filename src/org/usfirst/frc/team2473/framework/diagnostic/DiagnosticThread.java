package org.usfirst.frc.team2473.framework.diagnostic;

import java.util.ArrayList;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.Diagnoser;

public class DiagnosticThread extends Thread{
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	public double initialTime;
	
	private static DiagnosticThread theInstance;
	
	static {
		theInstance = new DiagnosticThread();
	}
	
	public static DiagnosticThread getInstance() {
		return theInstance;
	}

	private DiagnosticThread(){
		initialTime = System.currentTimeMillis();
	}
	
	public void addToList(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}
	
	@Override
	public void run(){
		
		while(isAlive()){
			for(Diagnoser diagnoser:diagnosers){
				diagnoser.RunSimultaneousTest();
			}
			errorPrinting();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void errorPrinting(){
		
	}
	
	public double getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}
