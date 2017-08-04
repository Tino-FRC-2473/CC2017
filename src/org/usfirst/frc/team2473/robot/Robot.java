package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.framework.components.Controls;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.components.Controls.ButtonAction;
import org.usfirst.frc.team2473.framework.readers.ControlsReader;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;
import org.usfirst.frc.team2473.framework.trackers.ButtonTracker;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.DigitalTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.ServoTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker.Target;
import org.usfirst.frc.team2473.robot.commands.ClimberTeleOp;
import org.usfirst.frc.team2473.robot.commands.GearTele;
import org.usfirst.frc.team2473.robot.commands.RunAll;
import org.usfirst.frc.team2473.robot.commands.ShooterAuto;
import org.usfirst.frc.team2473.robot.subsystems.Climber;
import org.usfirst.frc.team2473.robot.subsystems.Gear;
import org.usfirst.frc.team2473.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends ThreadingRobot {
	DeviceReader reader;
	GearTele tele = new GearTele();
	
	public static Shooter shooter = new Shooter();
	public static Climber climber = new Climber();
	public static Gear gear = new Gear();
	Timer robotControlLoop;
	private boolean timerRunning;

	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotControlLoop = new Timer();
		addTrackers();
		addDevices();
		reader = new DeviceReader();
		reader.start();
		System.out.println("PRINTING ADDED TRACKER KEYS");
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()) System.out.println(tracker.getKey());
		ControlsReader.getInstance().init();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		timerRunning = true;
		
		autonomousCommand = new ShooterAuto();
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		timerRunning = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
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
		addButtonCommands();
		ControlsReader.getInstance().updateAll();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
//	/** 
//	 * This function adds all the devices
//	 */
//	public void updateDeviceCalls() {
//		addDeviceCall("ClimberRight", ()-> climber.getPower("RightMotor"));
//		addDeviceCall("ClimberLeft", ()-> climber.getPower("LeftMotor"));
//		addDeviceCall("GearPickupMotor", ()-> gear.getPower("gearPickupMotor"));
//		addDeviceCall("ShooterRight", ()-> shooter.getPower("RightMotor"));
//		addDeviceCall("ShooterLeft", ()-> shooter.getPower("LeftMotor"));
//	}
	
	public void addDevices() {

	}
	
	public void addTrackers() {
		Trackers.getInstance().addTracker(new EncoderTracker(RobotMap.Shooter_Enc, RobotMap.SHOOTER_TALON_ONE));
		Trackers.getInstance().addTracker(new EncoderTracker(RobotMap.Shooter_Enc, RobotMap.SHOOTER_TALON_TWO));
		Trackers.getInstance().addTracker(new EncoderTracker(RobotMap.Gear_Pickup_Enc, RobotMap.GEAR_PICKUP_MOTOR));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.GEAR_PICKUP_POWER, RobotMap.GEAR_PICKUP_MOTOR, Target.POWER));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.climberTalonOneCurrent, RobotMap.CLIMBER_TALON_ONE, Target.CURRENT));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.climberTalonTwoCurrent, RobotMap.CLIMBER_TALON_TWO, Target.CURRENT));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.shooterTalonOneCurrent, RobotMap.SHOOTER_TALON_ONE, Target.CURRENT));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.shooterTalonTwoCurrent, RobotMap.SHOOTER_TALON_TWO, Target.CURRENT));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.shooterTalonOnePower, RobotMap.SHOOTER_TALON_ONE, Target.POWER));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.shooterTalonTwoPower, RobotMap.SHOOTER_TALON_TWO, Target.POWER));

		
		
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.gearPickupTalonCurrent, RobotMap.GEAR_PICKUP_MOTOR, Target.CURRENT));
		Trackers.getInstance().addTracker(new ButtonTracker(ControlsMap.Joy_ZERO_Climber_EncVal, ControlsMap.Joy_ZERO, ControlsMap.Joy_ZERO_Climber_Button));
		Trackers.getInstance().addTracker(new ButtonTracker(ControlsMap.Joy_ZERO_Gear_Motor_EncVal, ControlsMap.Joy_ZERO, ControlsMap.Joy_ZERO_Gear_Motor_Button));
		Trackers.getInstance().addTracker(new DigitalTracker(RobotMap.CLIMBER_LIMIT_SWITCH, RobotMap.Climber_Limit_Switch));
	}
	
	public void addButtonCommands() {
		Controls.getInstance().getButton(ControlsMap.Joy_ZERO_Gear_Motor_EncVal).whenPressed(new GearTele());
		Controls.getInstance().getButton(ControlsMap.Joy_ZERO_Climber_EncVal).whenPressed(new ClimberTeleOp());
	}
}
