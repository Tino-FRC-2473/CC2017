package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

public class TalonTracker extends DeviceTracker {
	
	public enum Target {
		POWER, VOLTAGE, CURRENT
	}

	public TalonTracker(String key, int port, Target target) {
		super(key, Type.NUMERIC, port);
		if(target == Target.POWER) setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).get());
		else if(target == Target.VOLTAGE) setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getOutputVoltage());
		else if(target == Target.CURRENT) setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getOutputCurrent());
	}
}