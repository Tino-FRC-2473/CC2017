package org.usfirst.frc.team2473.framework.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team2473.framework.threading.ThreadSafeInternalButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class Controls {
	private ArrayList<Joystick> joysticks;
	private Map<String, ThreadSafeInternalButton> buttons;

	public enum ButtonAction {
		PRESSED, RELEASED, TOGGLE_PRESSED, CANCEL_PRESSED, HELD
	}
	
	private Controls() {
		joysticks = new ArrayList<Joystick>();
		buttons = new HashMap<>();
	}
	
	private static Controls theInstance;

	static {
		theInstance = new Controls();
	}
	
	public static Controls getInstance() {
		return theInstance;
	}
	
	public void addJoystick(int port) {
		joysticks.add(new Joystick(port));
	}
	
	public void removeJoystick(int port) {
		for(Joystick stick : joysticks)
			if(stick.getPort()==port) {
				joysticks.remove(stick);
				break;
			}
	}
	
	public Joystick getJoystick(int port) {
		for(Joystick stick : joysticks) if(stick.getPort() == port) return stick;
		addJoystick(port);
		return joysticks.get(joysticks.size() - 1);
	}
	
	public synchronized Button getButton(String key) {
		if(buttons.get(key)==null) buttons.put(key, new ThreadSafeInternalButton());
		return buttons.get(key);
	}
	
	public synchronized void setButtonValue(String key, boolean val) {
		buttons.get(key).setPressed(val);
	}

	public void addButtonCommand(String key, ButtonAction action, Command cmd) throws InstantiationException, IllegalAccessException {
		if(buttons.get(key)==null) buttons.put(key, new ThreadSafeInternalButton());
		switch(action) {
			case PRESSED:
				buttons.get(key).whenPressed(cmd);
				break;
			case RELEASED:
				buttons.get(key).whenReleased(cmd);
				break;
			case TOGGLE_PRESSED:
				buttons.get(key).toggleWhenPressed(cmd);
				break;
			case CANCEL_PRESSED:
				buttons.get(key).cancelWhenActive(cmd);
				break;
			case HELD:
				buttons.get(key).whileHeld(cmd);
				break;
		}
	}
}