package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

public class ServoDiagnoser extends Diagnoser{
	Servo servo;
	String key;
	Double range;
	private Command command;
	
	public ServoDiagnoser(Servo servo,String key, Double range){
		this.servo = servo;
		this.key = key;
		this.range = range;
		//Diagnostics.addToQueue(this);
	}


	@Override
	public void RunSimultaneousTest() {
		
	}

	@Override
	public Command RunOneTimeTest() {
		return command;
	}
	@Override
	public double getMultiplier(){
		return 1.0;
	}

}
