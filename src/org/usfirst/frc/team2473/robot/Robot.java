
package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker.Type;
import org.usfirst.frc.team2473.framework.trackers.ServoTracker;
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
	public static OI oi;
	
	private Command command;
	
	DeviceReader reader;


	@Override
	public void robotInit() {
		oi = new OI();
		addTrackers();
		addDevices();
		reader = new DeviceReader();
		reader.start();
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

	}


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
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
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
		
	}
}
