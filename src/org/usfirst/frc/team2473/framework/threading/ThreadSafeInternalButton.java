package org.usfirst.frc.team2473.framework.threading;

import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A threadsafe modification of InternalButton
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/buttons/InternalButton.html"><code>InternalButton</code></a>
 */
public class ThreadSafeInternalButton extends InternalButton {
	Command cmd;
	/**
	 * Set the inverted mode of the button
	 * @param inverted <code>boolean</code> value representing the new inverted mode of the button
	 */
	@Override
	public synchronized void setInverted(boolean inverted) {
		// TODO Auto-generated method stub
		super.setInverted(inverted);
	}

	/**
	 * Set whether the button is pressed
	 * @param pressed <code>boolean</code> value representing the new pressed mode of the button
	 */
	@Override
	public synchronized void setPressed(boolean pressed) {
		// TODO Auto-generated method stub
		super.setPressed(pressed);
	}

	/**
	 * Receives the value of the button
	 * @return a <code>boolean</code> value representing the value of the button, with down being <code>true</code>
	 */
	@Override
	public synchronized boolean get() {
		// TODO Auto-generated method stub
		return super.get();
	}

	/**
	 * Runs the given command when the button is pressed
	 * @param command a <code>Command</code> that is run when the button is pressed
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Command.html"><code>Command</code></a>
	 */
	@Override
	public synchronized void whenPressed(Command command) {
		// TODO Auto-generated method stub
		super.whenPressed(command);
	}

	/**
	 * Runs the given command as long as the button is being held
	 * @param command a <code>Command</code> that is run while the button is held
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Command.html"><code>Command</code></a>
	 */
	@Override
	public synchronized void whileHeld(Command command) {
		// TODO Auto-generated method stub
		super.whileHeld(command);
		
	}
	
	/**
	 * Runs the given command when the button is released after being pressed.
	 * @param command a <code>Command</code> that is run after the button has been released.
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Command.html"><code>Command</code></a>
	 */
	@Override
	public synchronized void whenReleased(Command command) {
		// TODO Auto-generated method stub
		super.whenReleased(command);
	}

	/**
	 * Toggles the given command when the button is used as a toggle.
	 * @param command a <code>Command</code> that is run or canceled while the button is toggled.
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Command.html"><code>Command</code></a>
	 */
	@Override
	public synchronized void toggleWhenPressed(Command command) {
		// TODO Auto-generated method stub
		super.toggleWhenPressed(command);
	}

	/**
	 * Stops the given command when the button is pressed.
	 * @param command a <code>Command</code> that is stopped when the button has been pressed.
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Command.html"><code>Command</code></a>
	 */
	@Override
	public synchronized void cancelWhenPressed(Command command) {
		// TODO Auto-generated method stub
		super.cancelWhenPressed(command);
	}	
}