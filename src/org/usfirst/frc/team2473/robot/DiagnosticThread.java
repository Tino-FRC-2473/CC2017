package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

public class DiagnosticThread extends Thread{
	private static ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	
	public static double initialTime;
	
	public DiagnosticThread(){
		initialTime = System.currentTimeMillis();
	}
	
	public static void addToList(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}
	
	public void run(){
		while(isAlive()){
			for(Diagnoser diagnoser:diagnosers){
				diagnoser.RunSimultaneousTest();
			}
			errorPrinting();
		}
	}
	
	private void errorPrinting(){
		
	}
	
	public static double getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}