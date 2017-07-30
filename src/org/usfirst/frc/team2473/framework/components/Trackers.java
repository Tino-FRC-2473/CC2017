package org.usfirst.frc.team2473.framework.components;

import java.util.ArrayList;

import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.GyroTracker;

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