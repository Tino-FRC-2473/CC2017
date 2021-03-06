package org.usfirst.frc.team2473.framework.components;

import java.util.ArrayList;

import org.usfirst.frc.team2473.framework.trackers.ExternalDevice;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;

public class Devices {
	private ArrayList<CANTalon> talons;
	private AnalogGyro[] gyros;
	private ArrayList<ExternalDevice> externals;
	private ArrayList<AnalogInput> analogs;
	private ArrayList<Servo> servos;	
	private static Devices theInstance;
	
	static {
		theInstance = new Devices();
	}
	
	public static Devices getInstance() {
		return theInstance;
	}
	
	private Devices() {
		talons = new ArrayList<CANTalon>();
		gyros = new AnalogGyro[1];
		externals = new ArrayList<ExternalDevice>();
		analogs = new ArrayList<AnalogInput>();
		servos = new ArrayList<Servo>();
	}

	public void removeTalon(int port) {
		for(CANTalon talon : talons) 
			if(talon.getDeviceID() == port) {
				talons.remove(talon); 	
				break;
			}
	}
	
	public CANTalon getTalon(int port) {
		for(CANTalon talon : talons) if(talon.getDeviceID() == port) return talon;
		addTalon(port);
		return talons.get(talons.size() - 1);
	}
	
	public void addTalon(int port) {
		talons.add(new CANTalon(port));
	}

	public void removeServo(int port) {
		for(Servo servo : servos) {
			if(servo.getChannel()==port) {				
				servos.remove(servo);
				break;	
			}
		}
	}
	
	public Servo getServo(int port) {
		for(Servo servo : servos) if(servo.getChannel()==port) return servo;
		addServo(port);
		return servos.get(servos.size()-1);
	}
	
	public void addServo(int port) {
		servos.add(new Servo(port));
	}
	
	public void removeAnalogInput(int channel) {
		for(AnalogInput input : analogs)
			if(input.getChannel() == channel) {
				analogs.remove(input);
				break;
			}
	}
	
	public AnalogInput getAnalogInput(int channel) {
		for(AnalogInput input : analogs) if(input.getChannel() == channel) return input;
		addInput(channel);
		return analogs.get(analogs.size() - 1);
	}
	
	public void addInput(int channel) {
		analogs.add(new AnalogInput(channel));
	}
	
	
	public void removeExternalDevice(String name) {
		for(ExternalDevice external : externals)
			if(external.getName().equals(name)) {
				externals.remove(external);
				break;
			}
	}
	
	public ExternalDevice getExternalDevice(String name) {
		for(ExternalDevice external : externals) if(external.getName().equals(name)) return external;
		addExternalDevice(name);
		return externals.get(externals.size() - 1);
	}
	
	public void addExternalDevice(String name) {
		externals.add(new ExternalDevice(name));
	}
	
	public void removeGyro() {
		gyros[0] = null;
	}
	
	public AnalogGyro getGyro(int port) {
		if(gyros[0] != null) return gyros[0];
		setGyro(port);
		return gyros[0];
	}
	
	public void setGyro(int port) {
		gyros[0] = new AnalogGyro(port);
	}
}