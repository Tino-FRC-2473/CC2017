package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Servo;

public class MotorDiagnoser extends Diagnoser{
	
	CANTalon motor;
	String key;
	Double range;
	
	public MotorDiagnoser(CANTalon motor,String key, Double range){
		this.motor = motor;
		this.key = key;
		this.range = range;
		Diagnostics.addToQueue(this);
	}


//	@Override
//	public void RunManualTest() {
//		
//	}

	@Override
	public void RunSimultaneousTest() {
		
	}

	@Override
	public void RunOneTimeTest() {
		
	}

}
