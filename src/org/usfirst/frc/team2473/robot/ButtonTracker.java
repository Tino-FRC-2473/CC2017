package org.usfirst.frc.team2473.robot;

public class ButtonTracker extends DeviceTracker {

	public ButtonTracker(String key, int joystick, int button) {
		super(key, Type.CONDITIONAL, button);
		setEvokeConditional(Controls.getInstance().getJoystick(joystick).getRawButton(button));
	}
}