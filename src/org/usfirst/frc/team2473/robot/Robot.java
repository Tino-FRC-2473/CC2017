
package org.usfirst.frc.team2473.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.readers.ControlsReader;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;
import org.usfirst.frc.team2473.framework.trackers.NavXTracker;
import org.usfirst.frc.team2473.framework.trackers.NavXTracker.NavXTarget;
import org.usfirst.frc.team2473.robot.commands.DriveStraight;
import org.usfirst.frc.team2473.robot.subsystems.PIDDriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static final PIDDriveTrain driveTrain = new PIDDriveTrain();
	public static final DriveStraight driveStraight = new DriveStraight();
	public static boolean networkingRunning = true;

	boolean timerRunning;
	Timer robotControlLoop;

	private Command command;

	DeviceReader reader;

	@Override
	public void robotInit() {
		addTrackers();
		addDevices();
		reader = new DeviceReader();
		reader.start();
		robotControlLoop = new Timer();

		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture(0);
		cam.setBrightness(0);
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		timerRunning = true;
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		command = driveStraight;
		if (command != null) {
			driveTrain.getGyro().zeroYaw();
			command.start();
		}
		timerRunning = false;
		GyroLoggingThread.getInstance().start();
	}

	@Override
	public void teleopPeriodic() {
		if (!timerRunning) {
			robotControlLoop.scheduleAtFixedRate(new TimerTask() { // run the control loop timer if the competition
																	// timer is not running
				@Override
				public void run() {
					Scheduler.getInstance().run(); // run the scheduler over the periodic function
				}
			}, 0, 20);
			timerRunning = true; // ultimately set the running timer to true
		}
		ControlsReader.getInstance().updateAll();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void addDevices() {
		Devices.getInstance().addTalon(RobotMap.BACK_LEFT);
		Devices.getInstance().addTalon(RobotMap.BACK_RIGHT);
		Devices.getInstance().addTalon(RobotMap.FRONT_LEFT);
		Devices.getInstance().addTalon(RobotMap.FRONT_RIGHT);
	}

	public void addTrackers() {
		Trackers.getInstance().addTracker(new NavXTracker(RobotMap.GYRO_YAW, NavXTarget.YAW));
		Trackers.getInstance().addTracker(new NavXTracker(RobotMap.GYRO_RATE, NavXTarget.RATE));
		// s Trackers.getInstance().addTracker(new
		// JoystickTracker(ControlsMap.THROTTLE_KEY, ControlsMap.THROTTLE,
		// JoystickType.Z));
	}
}
