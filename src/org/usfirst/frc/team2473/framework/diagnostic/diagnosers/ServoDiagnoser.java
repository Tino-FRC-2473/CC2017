package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import edu.wpi.first.wpilibj.Servo;

public class ServoDiagnoser extends Diagnoser{
	Servo servo;
	String key;
	Double range;
	
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
	public void RunOneTimeTest() {
		
	}

}