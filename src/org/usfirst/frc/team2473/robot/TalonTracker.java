package org.usfirst.frc.team2473.robot;

public class TalonTracker extends DeviceTracker {
	
	enum Target {
		POWER, VOLTAGE
	}

	public TalonTracker(String key, int port, Target target) {
		super(key, Type.NUMERIC, port);
		if(target == Target.POWER) setEvokeNumeric(Devices.getInstance().getTalon(getPort()).get());
		else if(target == Target.VOLTAGE) setEvokeNumeric(Devices.getInstance().getTalon(getPort()).getOutputVoltage());
	}
}