package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.robot.commands.DriveTrainDiagnoserCommand;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriverTrainDiagnoser extends Diagnoser {
	//contructor values
	private int fr; //front right motor
	private int fl; //front left motor
	private int bl; //back left motor
	private int br; //back right motor
	private int gyro; //gyro
	private String gyroangle;
	private String keyre; //encoder
	private String keyle;
	//private String keyble;
	//private String keybre;
	private String keyfrp; //power
	private String keyflp;
	private String keyblp;
	private String keybrp;
	private String keyrs; //speed
	private String keyls;
	private String keyfrc; //current
	private String keyflc;
	private String keyblc;
	private String keybrc;
	
	//private Joystick stick; unused code for now
	
	//torque calculations
	private double rpmr = 0.0;
	private double rpml = 0.0;
	private double torquer = 0.0;
	private double torquel = 0.0;
	
	//encoder goal for onetime test
	private double encoders = 1600;
	
	//motor constants
	//private final double MAX_TORQUE = 0.03;
	//private final double MAX_CURRENT = 10.0;
	//private double EncoderTicksPerRotation = 6000.0;
	//private final double GearRatio = 14;
	
	//speed multiplier
	private double SpeedMultiplier = 1.0;
	
	private Command command;
	
	public DriverTrainDiagnoser(int keyfr, int keyfl, int keybl, int keybr, int gyro){
		this.fr = keyfr;
		this.fl = keyfl;
		this.bl = keybl;
		this.br = keybr;
		this.gyro = gyro;
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers())
			if(tracker.getClass().getName().equals("TalonTracker") && tracker.getPort() == fr) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					keyfrp = tracker.getKey();
					break;
				case CURRENT:
					keyfrc = tracker.getKey();
					break;
				case SPEED:
					keyrs = tracker.getKey();
					break;
				default:
					break;
				}
			} else if(tracker.getClass().getName().equals("EncoderTracker")) {
				keyre = ((EncoderTracker) tracker).getKey();
			}else if(tracker.getClass().getName().equals("TalonTracker") && tracker.getPort() == fl) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					keyflp = tracker.getKey();
					break;
				case CURRENT:
					keyflc = tracker.getKey();
					break;
				case SPEED:
					keyls = tracker.getKey();
					break;
				default:
					break;
				}
			} else if(tracker.getClass().getName().equals("EncoderTracker")) {
				keyle = ((EncoderTracker) tracker).getKey();
			}else if(tracker.getClass().getName().equals("TalonTracker") && tracker.getPort() == br) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					keybrp = tracker.getKey();
					break;
				case CURRENT:
					keybrc = tracker.getKey();
					break;
				default:
					break;
				}
			} else if(tracker.getClass().getName().equals("TalonTracker") && tracker.getPort() == bl) {
				switch(((TalonTracker) tracker).getTarget()) {
				case POWER:
					keyblp = tracker.getKey();
					break;
				case CURRENT:
					keyblc = tracker.getKey();
					break;
				default:
					break;
				}
			} else if(tracker.getClass().getName().equals("GyroTracker") && tracker.getPort() == gyro){
				gyroangle = tracker.getKey();
			}
	}

	@Override
	public Command RunOneTimeTest() {
		command = new DriveTrainDiagnoserCommand(fr,fl,br,bl,gyro,keyre,keyle,keyfrp,encoders,gyroangle);
		return command;
	}

	@Override
	public void RunSimultaneousTest() {
		// TODO Auto-generated method stub
		//current power speed
		double pastrpmr;
		double pastrpml;
		
		double currentfr = Database.getInstance().getNumeric(keyfrc);
		double currentfl = Database.getInstance().getNumeric(keyflc);
		double currentbr = Database.getInstance().getNumeric(keybrc);
		double currentbl = Database.getInstance().getNumeric(keyblc);
		
		if(DiagnosticThread.getInstance().getTime()%1000 == 0){
			pastrpmr = rpmr;
			pastrpml = rpml;
			rpmr = (((Database.getInstance().getNumeric(keyrs)*600)*(DiagnosticMap.DRIVETRAIN_GEAR_RATIO))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
			rpml = (((Database.getInstance().getNumeric(keyls)*600)*(DiagnosticMap.DRIVETRAIN_GEAR_RATIO))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
			torquer = (rpmr - pastrpmr);
			torquel = (rpml - pastrpml);
		}	
		if(torquer >= DiagnosticMap.MAX_TORQUE775 || torquel >= DiagnosticMap.MAX_TORQUE775 || 
		   currentfr >= DiagnosticMap.MAX_CURRENT775 || currentfl >= DiagnosticMap.MAX_CURRENT775 ||
		   currentbr >= DiagnosticMap.MAX_CURRENT775 || currentbl >= DiagnosticMap.MAX_CURRENT775){
			System.out.println("Drive Train in CRITICAL condition, lowering max speed");
			this.SpeedMultiplier -= 0.1;
		}else{
			this.SpeedMultiplier = 1.0;
		}
		
		if(Database.getInstance().getNumeric(keyle) != 0 && Math.abs(Database.getInstance().getNumeric(keyre)/Database.getInstance().getNumeric(keyle)) < 1 && (Database.getInstance().getNumeric(gyroangle) < 180 && Database.getInstance().getNumeric(gyroangle) > 0)){
			System.out.println("Drive Train Gyro: not calibrated");
		}
		if(Database.getInstance().getNumeric(keyre) != 0 && Math.abs(Database.getInstance().getNumeric(keyle)/Database.getInstance().getNumeric(keyre)) < 1 && (Database.getInstance().getNumeric(gyroangle) <= 359 && Database.getInstance().getNumeric(gyroangle) > 180)){
			System.out.println("Drive Train Gyro: not calibrated");
		}
	}
	
	private void setPowerToALl(double pow){
		Devices.getInstance().getTalon(fr).set(pow);
		Devices.getInstance().getTalon(fl).set(pow);
		Devices.getInstance().getTalon(br).set(pow);
		Devices.getInstance().getTalon(bl).set(pow);
	}
	
	private void reset(){
		Devices.getInstance().getTalon(fr).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(fl).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(bl).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(br).changeControlMode(TalonControlMode.Position);
	
		Devices.getInstance().getTalon(fr).setPosition(0);
		Devices.getInstance().getTalon(fl).setPosition(0);
		Devices.getInstance().getTalon(br).setPosition(0);
		Devices.getInstance().getTalon(bl).setPosition(0);
		
		Devices.getInstance().getTalon(fr).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(fl).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(bl).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(br).changeControlMode(TalonControlMode.PercentVbus);
		
		Devices.getInstance().getGyro(gyro).reset();
	}
	
	@Override
	public double getMultiplier(){
		return SpeedMultiplier;
	}
	
}