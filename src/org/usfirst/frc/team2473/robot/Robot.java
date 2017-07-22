package org.usfirst.frc.team2473.robot;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.framework.Networking;
import org.usfirst.frc.team2473.framework.diagnostic.Diagnostics;
import org.usfirst.frc.team2473.framework.diagnostic.Diagnostics.TestType;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.MotorDiagnoser;
import org.usfirst.frc.team2473.framework.readers.ControlsReader;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Central class for RIO-side code-base. Calls to commands and directly executable code are made here. Threads are created and run in this class.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class Robot extends IterativeRobot {
	
	private boolean timerRunning; //this timer is set to true for autonomous and false for tele-op
	private DeviceReader reader; //this is the device reader thread, which reads device values and looks up memes
	private Timer robotControlLoop = new Timer(); //timer allows for even periodic execution of teleOpPeriodic
	private Networking network; //this is the networking thread
	
	/*no special constructor is required for this class. you will never need to make an object of this class*/
	
	/**
	 * Is executed when the robot is first started up. Overridden from <code>IterativeRobot</code>
	 * Calls <code>addTrackers</code> for trackers to be added and starts the <code>DeviceReader</code> thread.
	 * @see DeviceReader
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void robotInit() {
		addTrackers(); //add the trackers before anything else
		addDevices(); //add the devices if not covered by trackers
		reader = new DeviceReader(); //create device reader thread
		reader.start(); //start the thread once the robot is started
		try {
			network = new Networking(); //create the networking thread
		} catch (IOException e) {
			e.printStackTrace();
		} 
		network.start(); //start the thread once the robot is started
	}

	/**
	 * Is executed when the robot enters disabled mode. Overridden from <code>IterativeRobot</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void disabledInit() {

	}

	/**
	 * Is executed during the robot's disabled mode as a looping method. Overridden from <code>IterativeRobot</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run(); //run the scheduler over the periodic function
	}


	/**
	 * Is executed when the robot enters autonomous mode. Overridden from <code>IterativeRobot</code>
	 * <br> Sets the <code>timerRunning</code> to <code>false</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void autonomousInit() {
		timerRunning = true; //the competition timer is running now that autonomous mode has started
	}

	/**
	 * Is executed during the robot's autonomous mode as a looping method. Overridden from <code>IterativeRobot</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void autonomousPeriodic() {
		if (!timerRunning) {
			robotControlLoop.scheduleAtFixedRate(new TimerTask(){ //run the control loop timer if the competition timer is not running
				@Override
				public void run() {
					Scheduler.getInstance().run(); //run the scheduler over the periodic function
				}
			}, 0, 20);
			timerRunning = true; //ultimately set the running timer to true
		}
	}

	/**
	 * Is executed when the robot enters tele-op mode. Overriden from <code>IterativeRobot</code>
	 * <br> Sets the <code>timerRunning</code> to <code>true</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void teleopInit() {
		timerRunning = false; //the competition timer is not running now that tele-op mode has started
	}

	/**
	 * Is executed during the robot's tele-op mode as a looping method. Overridden from <code>IterativeRobot</code>
	 * <br>Gets the <code>robotControlLoop Timer</code> running, at a consistent period of 20 milliseconds.
	 * <br><br> The <code>Scheduler</code> is run during this period, and <code>ControlsReader</code> updates OI values constantly.
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * @see org.usfirst.frc.team2473.framework.readers.ControlsReader
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Timer.html"><code>Timer</code></a>
	 * */
	@Override
	public void teleopPeriodic() {
		if (!timerRunning) {
			robotControlLoop.scheduleAtFixedRate(new TimerTask(){ //run the control loop timer if the competition timer is not running
				@Override
				public void run() {
					Scheduler.getInstance().run(); //run the scheduler over the periodic function
				}
			}, 0, 20);
			timerRunning = true; //ultimately set the running timer to true
		}
		
		ControlsReader.getInstance().updateAll();
	}

	/**
	 * Is executed at the beginning of the robot's test mode. Used for diagnostics. 
	 */
	@Override
	public void testInit() {
		addTests();
		Diagnostics.getInstance().startTests(TestType.ONETIME);
	}

	/**
	 * Is executed during the robot's testing mode as a looping method. Overridden from <code>IterativeRobot</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	/**
	 * Responsible for addition of <code>DeviceTracker</code> objects to <code>Trackers</code> and all <code>Devices</code>.
	 * <br>To add a device tracker, evoke <code>addTracker</code> from <code>Trackers</code>
	 * <br><br><br>Example: <code>Trackers.getInstance().addTracker(new EncoderTracker("encoder tracker", 2, Type.NUMERIC)</code>
	 * @see org.usfirst.frc.team2473.framework.trackers.DeviceTracker
	 * @see org.usfirst.frc.team2473.framework.components.Devices
	 * @see org.usfirst.frc.team2473.framework.components.Trackers#addTracker(org.usfirst.frc.team2473.framework.trackers.DeviceTracker)
	 * */
	public void addTrackers() {
		//call Trackers.getInstance().addTracker(new DeviceTracker(String key, int port, Type dataType);
	}	

	/**
	 * Responsible for addition of hardware device objects to <code>Device</code> objects.
	 * @see org.usfirst.frc.team2473.framework.components.Devices
	 * */
	public void addDevices() {
		//call Devices.getInstance() add method in order to add a specific sort of device
	}

	public void addTests() {
		Diagnostics.getInstance().addToQueue(new MotorDiagnoser(RobotMap.MOTOR, DiagnosticMap.MOTOR_RANGE, DiagnosticMap.MOTOR_TYPE));
	}
}