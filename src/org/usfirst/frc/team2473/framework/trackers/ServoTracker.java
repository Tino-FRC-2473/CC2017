package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

public class ServoTracker extends DeviceTracker{

	public enum Target {
		POWER, POSITION
	}

	public ServoTracker(String key, int port, Target target) {
		super(key, Type.NUMERIC, port);
		// TODO Auto-generated constructor stub
		if(target == Target.POSITION) setEvokeNumeric(() -> Devices.getInstance().getServo(getPort()).getPosition());
		else if(target == Target.POWER) setEvokeNumeric(() -> Devices.getInstance().getServo(getPort()).get());
	}

}
