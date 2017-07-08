package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Servo;

public class MotorDiagnoser extends Diagnoser{
	
	CANTalon motor;
	String key;
	
	public MotorDiagnoser(CANTalon motor,String key){
		this.motor = motor;
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
