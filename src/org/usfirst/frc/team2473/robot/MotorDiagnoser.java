package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Servo;

public class MotorDiagnoser extends Diagnoser{
	//constructor values
	private String keys; //speed
	private String keyc; //current
	private String keye; //encoder
	private String keyp; //power
	private int deviceID; //device id
	private double range;
	private Type type; 
	
	//torque calculations
	private double rpm;
	private double torque;
	
	//speed multiplier
	private double SpeedMultiplier;
	
	//time
	//private double time;
	//motor constants
	//private final double MAX_TORQUE = 0.03;
//	private final double MAX_CURRENT = 10.0;
//	private final double EcnoderTicksPerRotation = 6000.0;
//	private final double GearRatio = 14;
	
	public MotorDiagnoser(int deviceID, String keys, String keyc, String keye, String keyp, Double range, Type type){
		this.deviceID = deviceID;
		this.keys = keys;
		this.keye = keye;
		this.keyc = keyc;
		this.keyp = keyp;
		this.range = range;
		this.type = type;
		//Diagnostics.addToQueue(this);
	}
	
	public enum Type{
		M775
	}
	
	@Override
	public void RunSimultaneousTest() {
		double pastrpm;
		double current = (Database.getInstance().getNumeric(keyc));
		if(DiagnosticThread.getTime()%1000 == 0){
			if(type.equals(Type.M775)){
				pastrpm = rpm;
				rpm = (((Database.getInstance().getNumeric(keys)*600))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
				torque = (rpm - pastrpm);
			}
		}
		if(type.equals(Type.M775)){
			if((torque >= DiagnosticMap.MAX_TORQUE775) || (current >= DiagnosticMap.MAX_CURRENT775)){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
		}
	}

	@Override
	public void RunOneTimeTest() {
		reset();
		while(Database.getInstance().getNumeric(keye) <= range){
			if(Database.getInstance().getNumeric(keyp) != 0.3){
				Devices.getInstance().getTalon(deviceID).set(0.3);
			}
		}
		if(Database.getInstance().getNumeric(keye) <= range + 50 && Database.getInstance().getNumeric(keye) >= range - 50){
			System.out.println("Motor: " + deviceID + "Disfunctional");
		}
		reset();
		System.out.println("Turn motor mannually in any direction");
		while(Math.abs(Database.getInstance().getNumeric(keye)) <= range){
				System.out.println("Motor: " + deviceID + "Encoder Count: " + Database.getInstance().getNumeric(keye));
		}
		System.out.println("STOP! If this is as far as the motor goes, everything is working.");
		DigitalInputDiagnoser motorswitch = new DigitalInputDiagnoser(deviceID, range);
		motorswitch.turnOff();
	}
	
	private void reset(){
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(deviceID).setPosition(0);
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public double getMultiplier(){
		return SpeedMultiplier;
	}

}
