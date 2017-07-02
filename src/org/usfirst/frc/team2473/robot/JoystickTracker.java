package org.usfirst.frc.team2473.robot;

public class JoystickTracker extends DeviceTracker {

	public JoystickTracker(String key,int port, String type) {
		super(key, Type.NUMERIC, port);
		switch(type.toLowerCase()) {
			case "x":
				setEvokeNumeric(Controls.getInstance().getJoystick(getPort()).getX());
				break;
			case "y":
				setEvokeNumeric(Controls.getInstance().getJoystick(getPort()).getY());
				break;
			case "z":
				setEvokeNumeric(Controls.getInstance().getJoystick(getPort()).getZ());
				break;
			case "throttle":
				setEvokeNumeric(Controls.getInstance().getJoystick(getPort()).getThrottle());
				break;
			case "twist":
				setEvokeNumeric(Controls.getInstance().getJoystick(getPort()).getTwist());
				break;
			default:
				throw new IllegalArgumentException("Insufficient getter argument(joystick)");
		}
	}	
}