package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

import org.usfirst.frc.team2473.robot.commands.ManualTestCommand;
import org.usfirst.frc.team2473.robot.commands.ManualTestCommands;

import com.ctre.CANTalon;

public class Diagnostics {
	private static ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private static ManualTestCommands command;
	
	public static void addToQueue(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}
	public enum TestType{
		SIMULTANEOUS,ONETIME
	}
	public static void startTests(TestType type){
		if(type.equals(TestType.ONETIME) || type.equals(TestType.SIMULTANEOUS)){
			for(Diagnoser diagnoser : diagnosers){
				if(type.equals(TestType.SIMULTANEOUS)){
					DiagnosticThread.addToList(diagnoser);
				}
				if(type.equals(TestType.ONETIME)){
					diagnoser.RunOneTimeTest();
					diagnosers.remove(diagnoser);
				}
			}
		}
		DiagnosticThread thread = new DiagnosticThread();
		thread.start();
	}
}
