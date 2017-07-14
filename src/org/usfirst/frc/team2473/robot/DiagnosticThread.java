package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

public class DiagnosticThread extends Thread{
	private static ArrayList<Diagnoser> diagnosers = new ArrayList();
	
	public static double initialTime;
	
	public DiagnosticThread(){
		this.initialTime = System.currentTimeMillis();
	}
	
	public static void addToList(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}
	
	public void run(){
		while(this.isAlive()){
			for(Diagnoser diagnoser:diagnosers){
				diagnoser.RunSimultaneousTest();
			}
			this.errorHandling();
		}
	}
	
	private void errorHandling(){
		
	}
	
	public static double getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}
