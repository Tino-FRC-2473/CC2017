package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

import com.ctre.CANTalon;

public class SensorThread extends Thread {

	//declare hardware objects here

	ArrayList<CANTalon> encoders;

	private volatile boolean running;
	private int delay;

	public SensorThread(int delay) {
		init(delay);
	}
	
	public SensorThread() {
		//construct hardware objects here
		init(5);
	}
	
	public void init(int delay) {
		running = true;
		this.delay = delay;
		encoders = new ArrayList<CANTalon>();	
	}
	
	@Override
	public void run() {
		while(running) {
			//do something
		}
	}
	
	public void end() {
		running = false;
	}
	
	public boolean isDead() {
		return !running;
	}
	
}