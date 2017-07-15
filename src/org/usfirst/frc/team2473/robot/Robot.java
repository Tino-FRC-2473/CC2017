package org.usfirst.frc.team2473.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Controls;
import org.usfirst.frc.team2473.framework.components.Controls.ButtonAction;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.readers.ControlsReader;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;
import org.usfirst.frc.team2473.framework.trackers.ButtonTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.GyroTracker;
import org.usfirst.frc.team2473.framework.trackers.JoystickTracker;
import org.usfirst.frc.team2473.framework.trackers.ServoTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.robot.commands.MotorCommand;
import org.usfirst.frc.team2473.robot.commands.MotorStopCommand;
import org.usfirst.frc.team2473.robot.commands.ServoCommand;
import org.usfirst.frc.team2473.robot.commands.ServoStopCommand;
import org.usfirst.frc.team2473.robot.subsystems.MotorSystem;
import org.usfirst.frc.team2473.robot.subsystems.ServoSystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


public class Robot extends IterativeRobot {

	public static ServoSystem servoSystem = new ServoSystem();
	public static MotorSystem motorSystem = new MotorSystem();
	MotorCommand cmd1 = new MotorCommand();
	ServoCommand cmd2 = new ServoCommand();

	int counter = 0;

	boolean timerRunning;
	private DeviceReader reader;
	Timer robotControlLoop = new Timer();

	@Override
	public void robotInit() {
		addTrackers();
		reader = new DeviceReader();
		reader.start();
		print();
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
			Controls.getInstance().addButtonCommand(ControlsMap.BUTTON_ONE, ButtonAction.PRESSED, new ServoStopCommand());
			Controls.getInstance().addButtonCommand(ControlsMap.BUTTON_TWO, ButtonAction.PRESSED, new MotorStopCommand());
			//so on and so forth
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		cmd1.start();
		cmd2.start();
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
		print();
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void addTrackers() {
		//in the case below, both device trackers point to the same device, a talon with the device id stored in RobotMap.SHOOTER
		Trackers.getInstance().addTracker(new EncoderTracker(RobotMap.MOTOR_ENCODER_KEY, RobotMap.MOTOR));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.MOTOR_POWER_KEY, RobotMap.MOTOR, TalonTracker.Target.POWER));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.MOTOR_VOLTAGE_KEY, RobotMap.MOTOR, TalonTracker.Target.VOLTAGE));
		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.MOTOR_CURRENT_KEY, RobotMap.MOTOR, TalonTracker.Target.CURRENT));
		
		Trackers.getInstance().addTracker(new ServoTracker(RobotMap.SERVO_POSITION_KEY, RobotMap.SERVO, ServoTracker.Target.POSITION));
		Trackers.getInstance().addTracker(new ServoTracker(RobotMap.SERVO_POWER_KEY, RobotMap.SERVO, ServoTracker.Target.POWER));
		Trackers.getInstance().addTracker(new JoystickTracker(ControlsMap.JOYSTICK_ONE, ControlsMap.JOY_ONE, JoystickTracker.Type.X));
		Trackers.getInstance().addTracker(new ButtonTracker(ControlsMap.BUTTON_ONE, ControlsMap.JOY_ONE, ControlsMap.JOY_ONE_BUTTON_ONE));
		Trackers.getInstance().addTracker(new JoystickTracker(ControlsMap.JOYSTICK_TWO, ControlsMap.JOY_TWO, JoystickTracker.Type.Y));
		Trackers.getInstance().addTracker(new ButtonTracker(ControlsMap.BUTTON_TWO, ControlsMap.JOY_TWO, ControlsMap.JOY_TWO_BUTTON_ONE));
		
		Trackers.getInstance().addTracker(new GyroTracker(RobotMap.GYRO_HEADING_KEY, RobotMap.GYRO));
	}
	
	void horizontal() {
		for(int i = 0; i < 20; i++) System.out.print("_");
		System.out.println();
	}
	
	void print() {
		horizontal();
		System.out.println("ITERATION: " + ++counter);
		
		System.out.println("JOYSTICK 1 DATA: ");
		System.out.println("Joystick 1 Y Position: " + Database.getInstance().getNumeric(ControlsMap.JOYSTICK_ONE));
		System.out.println("Button 1 Value: " + Database.getInstance().getConditional(ControlsMap.BUTTON_ONE));

		System.out.println("SERVO DATA: ");
		System.out.println("Servo Position: " + Database.getInstance().getNumeric(RobotMap.SERVO_POSITION_KEY));
		System.out.println("Servo Power: " + Database.getInstance().getNumeric(RobotMap.SERVO_POWER_KEY));

		System.out.println("JOYSTICK 2 DATA: ");
		System.out.println("Joystick 2 Y Position: " + Database.getInstance().getNumeric(ControlsMap.JOYSTICK_TWO));
		System.out.println("Button 2 Value: " + Database.getInstance().getConditional(ControlsMap.BUTTON_TWO));		
		
		System.out.println("TALON DATA: ");
		System.out.println("Talon Position: " + Database.getInstance().getNumeric(RobotMap.MOTOR_ENCODER_KEY));
		System.out.println("Talon Power: " + Database.getInstance().getNumeric(RobotMap.MOTOR_POWER_KEY));
		System.out.println("Talon Current: " + Database.getInstance().getNumeric(RobotMap.MOTOR_CURRENT_KEY));
		System.out.println("Talon Voltage: " + Database.getInstance().getNumeric(RobotMap.MOTOR_VOLTAGE_KEY));
		
		System.out.println("GYRO DATA: ");
		System.out.println("Gyro Heading: " + Database.getInstance().getNumeric(RobotMap.GYRO_HEADING_KEY));
		
	}
}