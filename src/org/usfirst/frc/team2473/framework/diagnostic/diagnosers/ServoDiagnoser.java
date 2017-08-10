package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import org.usfirst.frc.team2473.framework.diagnostic.commands.ServoDiagnoserCommand;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

public class ServoDiagnoser extends Diagnoser{
	int id;
	Double range;
	private Command command;
	Type type;
	
	public ServoDiagnoser(int id, Double range, Type type){
		this.id = id;
		this.range = range;
		this.type = type;
		//Diagnostics.addToQueue(this);
	}
	
	public enum Type{
		FREE_ROTATION,
		
		ONE_EIGHTY
	}

	@Override
	public void RunSimultaneousTest() {
		
	}

	@Override
	public Command RunOneTimeTest() {
		return new ServoDiagnoserCommand(id,range,type);
	}
	@Override
	public double getMultiplier(){
		return 1.0;
	}

}
