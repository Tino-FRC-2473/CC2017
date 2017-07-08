package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.Servo;

public class ServoDiagnoser extends Diagnoser{
	Servo servo;
	String key;
	
	public ServoDiagnoser(Servo servo,String key){
		this.servo = servo;
		this.key = key;
		Diagnostics.addToQue(this);
	}

	@Override
	public void RunManualTest() {
		
	}

	@Override
	public void RunSimultaneousTest() {
		
	}

	@Override
	public void RunOneTimeTest() {
		
	}

}
