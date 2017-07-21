package org.usfirst.frc.team2473.framework.components;

import java.util.ArrayList;

import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.GyroTracker;

/**
 * This class stores all DeviceTrackers.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class Trackers {
	private ArrayList<DeviceTracker> trackers; //list to store all the trackers
	
	private static Trackers theInstance; //serves as the static instance to use at all times
	
	static { //construct theInstance as a static function
		theInstance = new Trackers();
	}

	private Trackers() { //private constructor prevents the creation of such an object elsewhere, forcing the use of the public static getInstance()
		trackers = new ArrayList<DeviceTracker>();
	}

	/**
	 * Returns a usable instance of this class for program use.
	 * @return a <code>static Trackers</code> object
	 */
	public static Trackers getInstance() { 
		return theInstance;
	}
	
	/**
	 * Adds a tracker to the collection of <code>DeviceTracker</code> objects
	 * @param tracker the <code>DeviceTracker</code> to add to the collection
	 * @see org.usfirst.frc.team2473.framework.trackers.DeviceTracker
	 */
	public void addTracker(DeviceTracker tracker) {
		trackers.add(tracker);
	}
	
	/**
	 * For any existing <code>EncoderTracker</code> objects, resets the encoders.
	 * @see org.usfirst.frc.team2473.framework.trackers.EncoderTracker
	 */
	public void resetEncoders() {
		//loop through all the trackers
		for(DeviceTracker tracker : trackers)
			//if the class of the tracker is EncoderTrackers, call resetEncoder
			if(tracker.getClass().getName().equals("EncoderTracker")) ((EncoderTracker) tracker).resetEncoder();
	}
	
	/**
	 * For any existing <code>GyroTracker</code> object, calibrates and resets the gyro.
	 * @see org.usfirst.frc.team2473.framework.trackers.GyroTracker
	 */
	public void resetGyro() {
		//loop through all the trackers
		for(DeviceTracker tracker : trackers)
			//if the class of the tracker is GyroTracker, call resetGyro
			if(tracker.getClass().getName().equals("GyroTracker")) ((GyroTracker) tracker).resetGyro();
	}
	
	/**
	 * Returns the collection of <code>DeviceTracker</code> objects
	 * @return the <code>ArrayList</code> of <code>DeviceTracker</code> objects
	 */
	public ArrayList<DeviceTracker> getTrackers() {
		return trackers;
	}
}