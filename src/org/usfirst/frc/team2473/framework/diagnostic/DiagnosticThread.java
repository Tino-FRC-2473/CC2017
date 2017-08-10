package org.usfirst.frc.team2473.framework.diagnostic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.Diagnoser;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class DiagnosticThread extends Thread{
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private HashMap<String, String> errors = new HashMap<String, String>();
	private HashMap<String, Command> commandsresponsible = new HashMap<String, Command>();
	
	public double initialTime;
	
	private static DiagnosticThread theInstance;
	
	static {
		theInstance = new DiagnosticThread();
	}
	
	public static DiagnosticThread getInstance() {
		return theInstance;
	}

	public void addError(String key, Command command, String error){
		errors.put(key, error);
		commandsresponsible.put(key, command);
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
		Scanner scanner = new Scanner(System.in);
		for(String error : errors.values()){
			if(scanner.nextLine().equals(error)){
				Scheduler.getInstance().disable();
			}
		}
	}
	
	public double getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}
