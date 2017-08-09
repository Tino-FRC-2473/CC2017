package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import com.ctre.CANTalon;
import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.robot.DiagnosticMap;
import org.usfirst.frc.team2473.framework.diagnostic.DiagnosticThread;
import org.usfirst.frc.team2473.framework.diagnostic.commands.*;

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
	private EncType enctype;
	
	//torque calculations
	private double rpm;
	private double deltaRPM;
	private double current;
	private double deltaCURRENT;
	
	//speed multiplier
	private double SpeedMultiplier;
	
	private Command command;
	
	private int direction;
	
	
	//time
	//private double time;
	//motor constants
	//private final double MAX_TORQUE = 0.03;
//	private final double MAX_CURRENT = 10.0;
//	private final double EcnoderTicksPerRotation = 6000.0;
//	private final double GearRatio = 14;
	
	public MotorDiagnoser(int deviceID, Double range, Type type, EncType enctype){
		this.deviceID = deviceID;
		System.out.println("key added");
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
			if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort()==deviceID) {
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
			} else if(tracker.getClass().getName().indexOf("EncoderTracker") != -1) {
				keye = ((EncoderTracker) tracker).getKey();
			}
		this.keys = keys;
		this.keye = keye;
		this.keyc = keyc;
		this.keyp = keyp;
		this.range = range;
		this.Type = type;
		this.enctype = enctype;
		//Diagnostics.addToQueue(this);
	}
	public MotorDiagnoser(int deviceID, Type type, int direction){
		this.deviceID = deviceID;
		System.out.println("key added");
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
			if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort()==deviceID) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					System.out.println("power key added1");
					keyp = tracker.getKey();
					System.out.println("power key added2");
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
		this.Type = type;
		this.direction = direction;
		Devices.getInstance().getTalon(deviceID).enableLimitSwitch(true, true);
		Devices.getInstance().getTalon(deviceID).ConfigFwdLimitSwitchNormallyOpen(false);
		Devices.getInstance().getTalon(deviceID).ConfigRevLimitSwitchNormallyOpen(false);
		//Diagnostics.addToQueue(this);
	}
	
	public enum Type{
		M775,
		
		M550,
		
		AM3102,
		
		CIM
	}
	
	public enum EncType{
		STANDARD
	}
	
	@Override
	public void RunSimultaneousTest() {
		if(range != 0){
			double pastrpm;
			double pastcurrent;
			switch(enctype){
				case STANDARD:
					if(DiagnosticThread.getInstance().getTime()%1000 == 0){
						pastrpm = rpm;
						pastcurrent = current;
						rpm = (((Database.getInstance().getNumeric(keys)*600))/DiagnosticMap.ENCODER_PER_ROTATION);
						current = Database.getInstance().getNumeric(keyc);
						deltaRPM = (rpm - pastrpm);
						deltaCURRENT = (current - pastcurrent);
					}
					break;
				default:
					break;
			}
		}
		if(range != 0){
			if((deltaRPM < 0) || deltaCURRENT > 0){
				System.out.println("Motor: " + deviceID + " -Lowering max speed");
				this.SpeedMultiplier -= 0.1;
			}else{
				this.SpeedMultiplier = 1.0;
			}
		}else{
			switch(Type){
			case M775:
				if((current >= DiagnosticMap.MAX_CURRENT775)){
					System.out.println("Motor: " + deviceID + " -Lowering max speed");
					this.SpeedMultiplier -= 0.1;
				}else{
					this.SpeedMultiplier = 1.0;
				}
				break;
			case M550:
				if((current >= DiagnosticMap.MAX_CURRENT550)){
					System.out.println("Motor: " + deviceID + " -Lowering max speed");
					this.SpeedMultiplier -= 0.1;
				}else{
					this.SpeedMultiplier = 1.0;
				}
				break;
			case AM3102:
				if((current >= DiagnosticMap.MAX_CURRENT3102)){
					System.out.println("Motor: " + deviceID + " -Lowering max speed");
					this.SpeedMultiplier -= 0.1;
				}else{
					this.SpeedMultiplier = 1.0;
				}
				break;
			case CIM:
				if((current >= DiagnosticMap.MAX_CURRENTCIM)){
					System.out.println("Motor: " + deviceID + " -Lowering max speed");
					this.SpeedMultiplier -= 0.1;
				}else{
					this.SpeedMultiplier = 1.0;
				}
			default:
				break;
			}
		}
	}

	@Override
	public Command RunOneTimeTest() {
		return new MotorDiagnoserCommand(deviceID,keye,keyp,range,direction);
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