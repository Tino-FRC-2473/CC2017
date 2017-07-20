package org.usfirst.frc.team2473.framework.trackers;

import org.usfirst.frc.team2473.framework.components.Controls;

/**
 * Tracks values of buttons that are parts of joysticks.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class ButtonTracker extends DeviceTracker {

	/**
	 * Constructor allowing for key assignment, joystick assignment, and button assignment.
	 * @param key <code>String</code> value representing user-assigned reference key for the tracker
	 * @param joystick <code>int</code> value representing joystick port number for button
	 * @param button <code>int</code> value representing button number on given joystick
	 * */
	public ButtonTracker(String key, int joystick, int button) {
		super(key, Type.CONDITIONAL, button); //create the tracker using the information, tracks boolean values
		setEvokeConditional(() -> Controls.getInstance().getJoystick(joystick).getRawButton(button)); //set the conditional evoke to the button value of the given joystick in given port
	}
}