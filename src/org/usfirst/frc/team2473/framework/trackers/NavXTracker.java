package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Devices;

import edu.wpi.first.wpilibj.SPI;

public class NavXTracker extends DeviceTracker {

	public enum NavXTarget {
		YAW, RATE
	}

	public NavXTracker(String key, NavXTarget target) {
		super(key, Type.NUMERIC, SPI.Port.kMXP.value);
		switch (target) {
		case YAW:
			setEvokeNumeric(() -> Devices.getInstance().getNavXGyro().getYaw());
			break;
		case RATE:
			setEvokeNumeric(() -> Devices.getInstance().getNavXGyro().getRate());
			break;
		default:
			throw new IllegalArgumentException("Requested value not tracked");
		}
	}

}
