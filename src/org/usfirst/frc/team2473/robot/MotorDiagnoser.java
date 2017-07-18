package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Servo;

public class MotorDiagnoser extends Diagnoser{
	//constructor values
	private CANTalon motor; //the motor
	private String keys; //speed
	private String keyc; //current
	private String keye; //encoder
	private String keyp; //power
	private double range;
	
	//torque calculations
	private double rpm;
	private double torque;
	
	//speed multiplier
	private double SpeedMultiplier;
	
	//motor constants
	private final double MAX_TORQUE = 0.03;
	private final double MAX_CURRENT = 10.0;
	private final double EcnoderTicksPerRotation = 6000.0;
	
	public MotorDiagnoser(CANTalon motor, String keys, String keyc, String keye, String keyp, Double range){
		this.motor = motor;
		this.keys = keys;
		this.keye = keye;
		this.keyc = keyc;
		this.keyp = keyp;
		this.range = range;
		
		Diagnostics.addToQueue(this);
	}


//	@Override
//	public void RunManualTest() {
//		
//	}

	@Override
	public void RunSimultaneousTest() {
		double pastrpm;
		double current = (DataBase.getDeviceValue(keyc));
		if(DiagnosticThread.getTime()%1000 == 0){
			pastrpm = rpm;
			rpm = ((DataBase.getDeviceValue(keys)/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			torque = (rpm - pastrpm);
		}
		if((torque >= MAX_TORQUE) || (current >= MAX_CURRENT)){
			System.out.println("Motor: " + "insert name" + " -Lowering max speed");
			//talk to deep 
			this.SpeedMultiplier -= 0.1;
		}else{
			this.SpeedMultiplier = 1.0;
		}
	}

	@Override
	public void RunOneTimeTest() {
		
		reset();
		
		while(DataBase.getDeviceValue(keye) <= range){
			if(DataBase.getDeviceValue(keyp) != 0.3){
				motor.set(0.3);
			}
		}
		if(DataBase.getDeviceValue(keye) <= range + 50 && DataBase.getDeviceValue(keye) >= range - 50){
			System.out.println("Motor: " + "insert name" + "Disfunctional");
		}
		
		reset();
		
	}
	
	private void reset(){
		motor.changeControlMode(TalonControlMode.Position);
		motor.setPosition(0);
		motor.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public double getMultiplier(){
		return SpeedMultiplier;
	}

}
