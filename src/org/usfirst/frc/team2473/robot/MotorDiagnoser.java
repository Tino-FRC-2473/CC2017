package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;
import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.robot.DiagnosticMap;
import org.usfirst.frc.team2473.robot.commands.MotorDiagnoserCommand;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;

public class MotorDiagnoser extends Diagnoser{
	//constructor values
	private String keys; //speed
	private String keyc; //current
	private String keye; //encoder
	private String keyp; //power
	private int deviceID; //device id
	private double range;
	private Type Type; 
	
	//torque calculations
	private double rpm;
	private double torque;
	
	//speed multiplier
	private double SpeedMultiplier;
	
	private Command command;
	
	//time
	//private double time;
	//motor constants
	//private final double MAX_TORQUE = 0.03;
//	private final double MAX_CURRENT = 10.0;
//	private final double EcnoderTicksPerRotation = 6000.0;
//	private final double GearRatio = 14;
	
	public MotorDiagnoser(int deviceID, Double range, Type type){
		this.deviceID = deviceID;
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
			if(tracker.getClass().getName().equals("TalonTracker") && tracker.getPort()==deviceID) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					keyp = tracker.getKey();
					break;
				case CURRENT:
					keyc = tracker.getKey();
					break;
				case SPEED:
					keys = tracker.getKey();
					break;
				default:
						break;
				}
			} else if(tracker.getClass().getName().equals("EncoderTracker")) {
				keye = ((EncoderTracker) tracker).getKey();
			}
		this.keys = keys;
		this.keye = keye;
		this.keyc = keyc;
		this.keyp = keyp;
		this.range = range;
		this.Type = type;
		//Diagnostics.addToQueue(this);
	}
	
	public enum Type{
		M775,
		
		M550,
		
		AM3102,
		
		CIM
	}
	
	@Override
	public void RunSimultaneousTest() {
		double pastrpm;
		double current = (Database.getInstance().getNumeric(keyc));
		if(DiagnosticThread.getInstance().getTime()%1000 == 0){
			switch(Type){
			case M775:
				pastrpm = rpm;
				rpm = (((Database.getInstance().getNumeric(keys)*600))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
				torque = (rpm - pastrpm);
				break;
			case M550:
				pastrpm = rpm;
				rpm = (((Database.getInstance().getNumeric(keys)*600))/DiagnosticMap.ENCODER_PER_ROTATION550)*(2*Math.PI);
				torque = (rpm - pastrpm);
				break;
			case AM3102:
				pastrpm = rpm;
				rpm = (((Database.getInstance().getNumeric(keys)*600))/DiagnosticMap.ENCODER_PER_ROTATION3102)*(2*Math.PI);
				torque = (rpm - pastrpm);
				break;
			case CIM:
				pastrpm = rpm;
				rpm = (((Database.getInstance().getNumeric(keys)*600))/DiagnosticMap.ENCODER_PER_ROTATIONCIM)*(2*Math.PI);
				torque = (rpm - pastrpm);
			default:
				break;
			}
		}
		
		switch(Type){
		case M775:
			if((torque >= DiagnosticMap.MAX_TORQUE775) || (current >= DiagnosticMap.MAX_CURRENT775)){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
			break;
		case M550:
			if((torque >= DiagnosticMap.MAX_TORQUE550) || (current >= DiagnosticMap.MAX_CURRENT550)){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
			break;
		case AM3102:
			if((torque >= DiagnosticMap.MAX_TORQUE3102) || (current >= DiagnosticMap.MAX_CURRENT3102)){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
			break;
		case CIM:
			if((torque >= DiagnosticMap.MAX_TORQUECIM) || (current >= DiagnosticMap.MAX_CURRENTCIM)){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
		default:
			break;
		}
	}

	@Override
	public Command RunOneTimeTest() {
		command = new MotorDiagnoserCommand(deviceID,keye,keyp,range);
		return command;
		
	}
	
	private void reset(){
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(deviceID).setPosition(0);
		Devices.getInstance().getTalon(deviceID).changeControlMode(TalonControlMode.PercentVbus);
	}
	@Override
	public double getMultiplier(){
		return SpeedMultiplier;
	}

}