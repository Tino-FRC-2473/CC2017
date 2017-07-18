package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;

public class GyroDiagnoser extends Diagnoser{
	
	AnalogGyro gyro;
	String key;
	
	public GyroDiagnoser(AnalogGyro gyro,String key){
		this.gyro = gyro;
		this.key = key;
		Diagnostics.addToQueue(this);
	}

//Another diagnoser for DriveTrain. 
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
