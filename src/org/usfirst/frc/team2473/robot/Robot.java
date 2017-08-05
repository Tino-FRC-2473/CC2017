package org.usfirst.frc.team2473.robot;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.Networking;
import org.usfirst.frc.team2473.framework.diagnostic.DiagnosticThread;
import org.usfirst.frc.team2473.framework.diagnostic.Diagnostics;
import org.usfirst.frc.team2473.framework.diagnostic.Diagnostics.TestType;
import org.usfirst.frc.team2473.framework.diagnostic.commands.DiagnosticCommands;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DriverTrainDiagnoser;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.MotorDiagnoser;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.MotorDiagnoser.Type;
import org.usfirst.frc.team2473.framework.readers.ControlsReader;
import org.usfirst.frc.team2473.framework.readers.DeviceReader;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.DigitalTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.GyroTracker;
import org.usfirst.frc.team2473.framework.trackers.JoystickTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker.Target;
import org.usfirst.frc.team2473.robot.commands.DriveStraight;
import org.usfirst.frc.team2473.robot.commands.OneTime;
import org.usfirst.frc.team2473.robot.commands.Simultaneous;
import org.usfirst.frc.team2473.robot.subsystems.PIDriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * Central class for RIO-side code-base. Calls to commands and directly executable code are made here. Threads are created and run in this class.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class Robot extends IterativeRobot {

	private boolean networkingRunning = false; //set to true if networking is running
	private boolean deviceReadingRunning = false; //set to true if using framework for threading
	private boolean timerRunning; //this timer is set to true for autonomous and false for tele-op
	private DeviceReader reader; //this is the device reader thread, which reads device values and looks up memes
	private Timer robotControlLoop = new Timer(); //timer allows for even periodic execution of teleOpPeriodic
	private Networking network; //this is the networking thread
	private SendableChooser<String> chooser;
	private boolean diagnosticsRunning = false;
	public static PIDriveTrain piDriveTrain;
	public static DriveStraight driveCommand;
	/*no special constructor is required for this class. you will never need to make an object of this class*/
	
	/**
	 * Is executed when the robot is first started up. Overridden from <code>IterativeRobot</code>
	 * Calls <code>addTrackers</code> for trackers to be added and starts the <code>DeviceReader</code> thread.
	 * @see DeviceReader
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void robotInit() {
		System.out.println("robot started");
//		chooser = new SendableChooser<>();
//		chooser.addObject(name, );
//		String in= chooser.getSelected();
//		if(in.equals("1")){
//			OneTime onetime = new OneTime();
//			onetime.execute();
//		} else {
//			DiagnosticThread.getInstance().start();
//		}
		if(deviceReadingRunning) {
			System.out.println("device thread started");
			addTrackers(); //add the trackers before anything else
			addDevices(); //add the devices if not covered by trackers
			reader = new DeviceReader(); //create device reader thread
			reader.start(); //start the thread once the robot is started			
		}
		if(diagnosticsRunning){
			addTests();
		}
		
		if(networkingRunning) {
			try {
				network = new Networking(); //create the networking thread
			} catch (IOException e) {
				e.printStackTrace();
			} 
			network.start(); //start the thread once the robot is started			
		}
		piDriveTrain = new PIDriveTrain();
		driveCommand = new DriveStraight();
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
		
	}	

	/**
	 * Is executed during the robot's autonomous mode as a looping method. Overridden from <code>IterativeRobot</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run(); //run the scheduler over the periodic function
	}

	/**
	 * Is executed when the robot enters tele-op mode. Overriden from <code>IterativeRobot</code>
	 * <br> Sets the <code>timerRunning</code> to <code>true</code>
	 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/IterativeRobot.html"><code>IterativeRobot</code></a>
	 * */
	@Override
	public void teleopInit() {
//		if(deviceReadingRunning) {
//			System.out.println("device thread started");
//			addTrackers(); //add the trackers before anything else
//			addDevices(); //add the devices if not covered by trackers
//			reader = new DeviceReader(); //create device reader thread
//			reader.start(); //start the thread once the robot is started			
//		}
		System.out.println("teleop started");
		if(diagnosticsRunning) Diagnostics.getInstance().startTests(TestType.SIMULTANEOUS);
		timerRunning = false; //the competition timer is not running now that tele-op mode has started
		if (driveCommand != null)
			driveCommand.start();
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
		//System.out.println(Diagnostics.getInstance().getMultiplier("drivetrainguy"));
		ControlsReader.getInstance().updateAll();
	}

	/**
	 * Is executed at the beginning of the robot's test mode. Used for diagnostics. 
	 */
	@Override
	public void testInit() {

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
	
	public void addDevices(){
		Devices.getInstance().addTalon(RobotMap.FRONT_LEFT);
		Devices.getInstance().addTalon(RobotMap.FRONT_RIGHT);
		Devices.getInstance().addTalon(RobotMap.BACK_LEFT);
		Devices.getInstance().addTalon(RobotMap.BACK_RIGHT);
	}
	public void addTrackers() {
		//call Trackers.getInstance().addTracker(new DeviceTracker(String key, int port, Type dataType);
//		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.MOTOR_CURRENT_KEY, RobotMap.MOTOR, Target.CURRENT));
//		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.MOTOR_POWER_KEY, RobotMap.MOTOR, Target.POWER));
//		Trackers.getInstance().addTracker(new TalonTracker(RobotMap.MOTOR_SPEED_KEY, RobotMap.MOTOR, Target.SPEED));
//		Trackers.getInstance().addTracker(new EncoderTracker(RobotMap.MOTOR_ENCODER_KEY, RobotMap.MOTOR));
//		Trackers.getInstance().addTracker(new DigitalTracker("aids", 1));
		Trackers.getInstance().addTracker(new JoystickTracker(ControlsMap.STEERING_WHEEL_X,ControlsMap.STEERING_WHEEL,org.usfirst.frc.team2473.framework.trackers.JoystickTracker.Type.X));
		Trackers.getInstance().addTracker(new JoystickTracker(ControlsMap.THROTTLE_Z,ControlsMap.THROTTLE,org.usfirst.frc.team2473.framework.trackers.JoystickTracker.Type.Z));
		Trackers.getInstance().addTracker(new GyroTracker("GYROGUY", SPI.Port.kMXP.value));
	}

	/**
	 * Responsible for addition of hardware device objects to <code>Device</code> objects.
	 * @see org.usfirst.frc.team2473.framework.components.Devices
	 * */
	public void addTests() {
		//Diagnostics.getInstance().addToQueue("MOTORBOI", new MotorDiagnoser(RobotMap.MOTOR, Type.M550, "aids",-1));
		//Diagnostics.getInstance().addToQueue("SWITCHBOI" , new DigitalInputDiagnoser("aids",org.usfirst.frc.team2473.framework.diagnostic.diagnosers.DigitalInputDiagnoser.Type.LIMIT_SWITCH));
		Diagnostics.getInstance().addToQueue("drivetrainguy", new DriverTrainDiagnoser(RobotMap.FRONT_RIGHT, RobotMap.FRONT_LEFT, RobotMap.BACK_LEFT, RobotMap.BACK_RIGHT, "GYROGUY"));
	}
}