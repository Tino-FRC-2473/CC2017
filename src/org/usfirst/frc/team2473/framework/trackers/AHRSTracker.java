package org.usfirst.frc.team2473.framework.trackers;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class AHRSTracker extends DeviceTracker {

	private AHRS gyro;
	
	public AHRSTracker(String key) {
		super(key, Type.NUMERIC, SPI.Port.kMXP.value);
		
		try {
			gyro = new AHRS(SPI.Port.kMXP);
			gyro.zeroYaw();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

}
