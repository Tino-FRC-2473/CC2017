package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

public class TalonTracker extends DeviceTracker {
	private Target target;
	public enum Target {
		POWER, VOLTAGE, CURRENT, SPEED
	}

	public TalonTracker(String key, int port, Target target) {
		super(key, Type.NUMERIC, port);
		this.target = target;
		switch(target) {
		case POWER:
			setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).get());
			break;
		case VOLTAGE:
			setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getOutputVoltage());
			break;
		case CURRENT:
			setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getOutputCurrent());
			break;
		case SPEED:
			setEvokeNumeric(() -> Devices.getInstance().getTalon(getPort()).getSpeed());
		}
	}
	
	public Target getTarget() {
		return target;
	}
}