package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

public class Trackers {
	private ArrayList<DeviceTracker> trackers;
	
	private static Trackers theInstance;
	
	static {
		theInstance = new Trackers();
	}

	public static Trackers getInstance() {
		return theInstance;
	}

	private Trackers() {
		trackers = new ArrayList<DeviceTracker>();
	}

	public void addTracker(DeviceTracker tracker) {
		trackers.add(tracker);
	}
	
	public void resetEncoders() {
		for(DeviceTracker tracker : trackers)
			if(tracker.getClass().getName().equals("EncoderTracker")) ((EncoderTracker) tracker).resetEncoder();
	}
	
	public void resetGyro() {
		for(DeviceTracker tracker : trackers)
			if(tracker.getClass().getName().equals("GyroTracker")) ((GyroTracker) tracker).resetGyro();
	}
	
	public ArrayList<DeviceTracker> getTrackers() {
		return trackers;
	}
}