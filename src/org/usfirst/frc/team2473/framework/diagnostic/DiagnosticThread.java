package org.usfirst.frc.team2473.framework.diagnostic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.Diagnoser;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DiagnosticThread extends Thread{
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private HashMap<String, String> errors = new HashMap<String, String>();
	private HashMap<String, Subsystem> systems = new HashMap<String, Subsystem>();
	
	public long initialTime;
	
	private static DiagnosticThread theInstance;
	
	static {
		theInstance = new DiagnosticThread();
	}
	
	public static DiagnosticThread getInstance() {
		return theInstance;
	}
	
	public void addError(String key, Subsystem sys, String error){
		errors.put(key, error);
		systems.put(key,sys);
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
		String Key = "";
		Scanner scanner = new Scanner(System.in);
		for(String error : errors.values()){
			for(String key : errors.keySet()){
				if(errors.get(key) == error){
					Key = key;
				}
			}
			if(scanner.nextLine().equals(error)){
				for(Subsystem sys : systems.values()){
					for(String key : systems.keySet()){
						if((systems.get(key) == sys) && (key == Key)){
							sys.getCurrentCommand().cancel();
						}
					}
				}
			}
		}
	}
	
	public long getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}
