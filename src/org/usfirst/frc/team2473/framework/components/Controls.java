package org.usfirst.frc.team2473.framework.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team2473.framework.threading.ThreadSafeInternalButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class stores all joysticks and buttons for tele-op use on the robot.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class Controls {
	private ArrayList<Joystick> joysticks; //collection for all Joystick objects
	private Map<String, ThreadSafeInternalButton> buttons; //collection for all buttons on the joysticks, thread-safe

	/**
	 * Represents different button actions by the driver.
	 * @author Deep Sethi
	 * @author Harmony He
	 * @version 1.0
	 */
	public enum ButtonAction {
		/**
		 * Button is pressed
		 */
		PRESSED, 
		/**
		 * Button has been released
		 */
		RELEASED, 
		/**
		 * Button is being pressed and used for a toggle function
		 */
		TOGGLE_PRESSED, 
		/**
		 * Button is being pressed and used as a cancellation function
		 */
		CANCEL_PRESSED, 
		/**
		 * Button is being held down
		 */
		HELD
	}
	
	private Controls() { //private constructor prevents the creation of such an object elsewhere, forcing the use of the public static getInstance()
		joysticks = new ArrayList<Joystick>();
		buttons = new HashMap<>();
	}
	
	private static Controls theInstance; //serves as the static instance to use at all times

	static { //construct theInstance as a static function
		theInstance = new Controls();
	}
	
	/**
	 * Returns a usable instance of this class for program use.
	 * @return a <code>static Controls</code> object
	 */
	public static Controls getInstance() {
		return theInstance;
	}
	
	
	/**
	 * Adds a joystick to the collection of joysticks with the given port number
	 * @param port an <code>int</code> value representing the port number of the joystick
	 */	
	public void addJoystick(int port) {
		joysticks.add(new Joystick(port));
	}
	
	/**
	 * Removes a given joystick from the collection of joysticks in this class.
	 * @param port an <code>int</code> value representing the port of the joystick to be removed.
	 */
	public void removeJoystick(int port) {
		for(Joystick stick : joysticks) //loop through the joysticks stored in this class
			if(stick.getPort()==port) { //if the port matches, remove the joystick and break out of the loop
				joysticks.remove(stick);
				break;
			}
	}
	
	/**
	 * Returns a joystick with the given port number
	 * @param port an <code>int</code> value representing the port number of the joystick
	 * @return a <code>Joystick</code> object with the given port number
	 */
	public Joystick getJoystick(int port) {
		/* loop through the collection of existing joysticks:
		 * if a joystick exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the joystick doesn't exist, the loop will go to completion, with nothing returned
		 * after which the joystick can be added through addJoystick and then returned in the next line as the last element in the collection
		 */
		for(Joystick stick : joysticks) if(stick.getPort() == port) return stick;
		addJoystick(port);
		return joysticks.get(joysticks.size() - 1);
	}
	
	/**
	 * Returns a button with the given reference key
	 * @param key an <code>String</code> value representing the reference key of the button
	 * @return a <code>Button</code> object with the given reference key
	 */
	public synchronized Button getButton(String key) {
		/* check for existence of button in the collection of existing buttons:
		 * if the button doesn't exist, add it
		 * next, return it, which will now work regardless of whether or not it existed before it was added the line before
		 */
		if(buttons.get(key)==null) buttons.put(key, new ThreadSafeInternalButton());
		return buttons.get(key);
	}
	
	/**
	 * Sets the pressed value of a given thread-safe button given its reference key.
	 * @param key a <code>String</code> representing the button reference key.
	 * @param val a <code>boolean</code> representing the new pressed value of the button
	 */	
	public synchronized void setButtonValue(String key, boolean val) {
		buttons.get(key).setPressed(val);
	}

	/**
	 * Assigns a button action listener to a button with a given reference key, allowing for a <code>Command</code> to be canceled or run.
	 * @param key a <code>String</code> representing the key of the button to listen to
	 * @param action a <code>ButtonAction</code> value representing an action to listen for
	 * @param cmd a <code>Command</code> object representing the command to be run or canceled when the event has been fired
	 * @throws InstantiationException if there is an error in <code>Command</code> instantiation
	 * @throws IllegalAccessException if the <code>Command</code> is not accessible
	 */
	public void addButtonCommand(String key, ButtonAction action, Command cmd) throws InstantiationException, IllegalAccessException {
		switch(action) { //based on the action trigger the correct Button method, passing in the command
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