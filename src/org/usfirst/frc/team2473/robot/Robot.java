package org.usfirst.frc.team2473.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.readers.ControlsReader;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;
import org.usfirst.frc.team2473.framework.trackers.JoystickTracker;
import org.usfirst.frc.team2473.framework.trackers.JoystickTracker.Type;
import org.usfirst.frc.team2473.robot.commands.JoystickControl;
import org.usfirst.frc.team2473.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2473.robot.subsystems.PIDriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	DeviceReader reader;
	Timer robotControlLoop;
	private boolean timerRunning = true;
	public static OI oi;
	public static DriveTrain driveTrain;
	public static JoystickControl joycom;
	public static PIDriveTrain piDriveTrain;
	Command autonomousCommand;

	@Override
	public void robotInit() {
		addDevices();
		addTrackers();
		reader = new DeviceReader();
		reader.start();
		
		piDriveTrain = new PIDriveTrain();
		driveTrain = new DriveTrain();
		joycom = new JoystickControl();
		
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
		timerRunning = false;
		joycom.start();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic() {
		if(!timerRunning){
			robotControlLoop.scheduleAtFixedRate(new TimerTask(){
				@Override
				public void run(){
					Scheduler.getInstance().run();
				}
			}, 0, 20);
			timerRunning=true;
		}
		ControlsReader.getInstance().updateAll();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	public void addDevices(){
		Devices.getInstance().addTalon(RobotMap.FRONT_LEFT);
		Devices.getInstance().addTalon(RobotMap.FRONT_RIGHT);
		Devices.getInstance().addTalon(RobotMap.BACK_LEFT);
		Devices.getInstance().addTalon(RobotMap.BACK_RIGHT);
	}
	public void addTrackers(){
		Trackers.getInstance().addTracker(new JoystickTracker(ControlsMap.STEERING_WHEEL_X,ControlsMap.STEERING_WHEEL,Type.X));
		Trackers.getInstance().addTracker(new JoystickTracker(ControlsMap.THROTTLE_Z,ControlsMap.THROTTLE,Type.Z));
	}
}