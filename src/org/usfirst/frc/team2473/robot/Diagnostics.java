package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

import org.usfirst.frc.team2473.robot.commands.ManualTestCommand;
import org.usfirst.frc.team2473.robot.commands.ManualTestCommands;

import com.ctre.CANTalon;

public class Diagnostics {
	private static ArrayList<Diagnoser> diagnosers = new ArrayList();
	private static ManualTestCommands command;
	public static void addToQue(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}
	public enum TestType{
		manual,simultaneous,onetime
	}
	public static void startTests(TestType type){
		if(type.equals(TestType.onetime) || type.equals(TestType.simultaneous)){
			for(Diagnoser diagnoser : diagnosers){
				if(type.equals(TestType.simultaneous)){
					DiagnosticThread.addToList(diagnoser);
				}
				if(type.equals(TestType.onetime)){
					diagnoser.RunOneTimeTest();
					diagnosers.remove(diagnoser);
				}
			}
		}else if(type.equals(TestType.manual)){
			command = new ManualTestCommands();
			for(Diagnoser diagnoser:diagnosers){
				command.addSequential(new ManualTestCommand(diagnoser,()-> Robot.oi.getButton()));
			}
			command.start();
		}
		DiagnosticThread thread = new DiagnosticThread();
		thread.start();
	}
}
