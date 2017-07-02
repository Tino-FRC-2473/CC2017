package org.usfirst.frc.team2473.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.robot.Controls.ButtonAction;
import org.usfirst.frc.team2473.robot.TalonTracker.Target;
import org.usfirst.frc.team2473.robot.commands.ShooterCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


public class Robot extends IterativeRobot {
	
	boolean timerRunning;
	private DeviceReader reader;
	Timer robotControlLoop;
	
	@Override
	public void robotInit() {
		reader = new DeviceReader();
		reader.start();
		addTrackers();
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
		try {
			Controls.getInstance().addButtonCommand(ControlsMap.SHOOTER, ButtonAction.PRESSED, new ShooterCommand());
			//so on and so forth
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		if (!timerRunning) {
			robotControlLoop.scheduleAtFixedRate(new TimerTask(){
				@Override
				public void run() {
					Scheduler.getInstance().run();
				}
			}, 0, 20);
			timerRunning = true;
		}
		
		ControlsReader.getInstance().updateAll();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void addTrackers() {
		//in the case below, both device trackers point to the same device, a talon with the device id stored in RobotMap.SHOOTER
		Trackers.getInstance().addTracker(new EncoderTracker(RobotMap.SHOOTER_ENCODER_KEY, RobotMap.SHOOTER)); //encoder tracker for shooter team
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.SHOOTER_VOLTAGE_KEY, RobotMap.SHOOTER, Target.VOLTAGE)); //voltage tracker for diagnostic team		
	}
}