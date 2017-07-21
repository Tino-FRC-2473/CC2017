package org.usfirst.frc.team2473.framework.readers;

import org.usfirst.frc.team2473.framework.components.Controls;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.AnalogTracker;
import org.usfirst.frc.team2473.framework.trackers.ButtonTracker;
import org.usfirst.frc.team2473.framework.trackers.DigitalTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.GyroTracker;
import org.usfirst.frc.team2473.framework.trackers.JoystickTracker;
import org.usfirst.frc.team2473.framework.trackers.ServoTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker.Target;

public class Parser {
	public static void parseDevice(String encodedDevice) {
		String[] temp = encodedDevice.toLowerCase().split(";");
		switch(temp[0]) {
		case "talon":
			Devices.getInstance().addTalon(Integer.parseInt(temp[1]));
			if(encodedDevice.indexOf("voltage") != -1) Trackers.getInstance().addTracker(new TalonTracker(key(temp, "voltage"), Integer.parseInt(temp[1]),Target.VOLTAGE));
			if(encodedDevice.indexOf("current") != -1) Trackers.getInstance().addTracker(new TalonTracker(key(temp, "current"), Integer.parseInt(temp[1]), Target.CURRENT));
			if(encodedDevice.indexOf("power") != -1) Trackers.getInstance().addTracker(new TalonTracker(key(temp, "power"), Integer.parseInt(temp[1]), Target.POWER));
			if(encodedDevice.indexOf("encoder") != -1) Trackers.getInstance().addTracker(new EncoderTracker(key(temp, "current"), Integer.parseInt(temp[1])));			
			break;
		case "gyro":
			Trackers.getInstance().addTracker(new GyroTracker(key(temp, "heading"), Integer.parseInt(temp[1])));
			break;
		case "joystick":
			Controls.getInstance().addJoystick(Integer.parseInt(temp[1]));
			if(encodedDevice.indexOf("z-axis") != -1) Trackers.getInstance().addTracker(new JoystickTracker(key(temp, "zAxis"), Integer.parseInt(temp[1]),JoystickTracker.Type.Z));
			if(encodedDevice.indexOf("y-axis") != -1) Trackers.getInstance().addTracker(new JoystickTracker(key(temp, "yAxis"), Integer.parseInt(temp[1]),JoystickTracker.Type.Y));
			if(encodedDevice.indexOf("x-axis") != -1) Trackers.getInstance().addTracker(new JoystickTracker(key(temp, "xAxis"), Integer.parseInt(temp[1]), JoystickTracker.Type.X));
			if(encodedDevice.indexOf("throttle") != -1) Trackers.getInstance().addTracker(new JoystickTracker(key(temp, "throttle"), Integer.parseInt(temp[1]), JoystickTracker.Type.THROTTLE));
			if(encodedDevice.indexOf("twist") != -1) Trackers.getInstance().addTracker(new JoystickTracker(key(temp, "twist"), Integer.parseInt(temp[1]), JoystickTracker.Type.TWIST));			
			break;
		case "servo":
			Trackers.getInstance().addTracker(new ServoTracker(key(temp, "servoAngle"), Integer.parseInt(temp[1])));
			break;
		case "button":
			Trackers.getInstance().addTracker(new ButtonTracker(key(temp, "pressed"), Integer.parseInt(temp[1].split("_")[0]), Integer.parseInt(temp[1].split("_")[1])));
			break;
		case "digital":
			Trackers.getInstance().addTracker(new DigitalTracker(key(temp, "mode"), Integer.parseInt(temp[1])));
			break;
		case "analog":
			Trackers.getInstance().addTracker(new AnalogTracker(key(temp, "value"), Integer.parseInt(temp[1])));
			break;
		default:
			throw new IllegalArgumentException("such a device does not exist");
		}
	}
	
	private static String key(String[] deviceSpecs, String element) {
		return deviceSpecs[0] + deviceSpecs[1] + "-" + element;
	}

	public static String key(String deviceSpecs, String target) {
		String[] arr = deviceSpecs.split(";");
		return arr[0] + arr[1] + "-" + target;
	}
	
	public static int id(String deviceSpecs) {
		String[] arr = deviceSpecs.split(";");
		if(arr[1].indexOf("/") != -1) return Integer.parseInt(arr[1]);
		else return Integer.parseInt(arr[1].split("/")[1]);			
	}
}