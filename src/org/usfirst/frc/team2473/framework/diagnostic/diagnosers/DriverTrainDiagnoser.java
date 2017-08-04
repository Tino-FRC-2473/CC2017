package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import com.ctre.CANTalon;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.trackers.DeviceTracker;
import org.usfirst.frc.team2473.framework.trackers.EncoderTracker;
import org.usfirst.frc.team2473.framework.trackers.TalonTracker;
import org.usfirst.frc.team2473.robot.DiagnosticMap;
import org.usfirst.frc.team2473.framework.diagnostic.DiagnosticThread;
import org.usfirst.frc.team2473.framework.diagnostic.commands.*;

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
	private double deltaENCODERL;
	private double deltaENCODERR;
	private double deltaANGLE;
	private double encl;
	private double encr;
	private double angle;
	
	
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
			if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort() == fr) {
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
			}else if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort() == fl) {
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
			}else if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort() == br) {
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
			} else if(tracker.getClass().getName().indexOf("TalonTracker") != -1 && tracker.getPort() == bl) {
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
			} else if(tracker.getClass().getName().indexOf("GyroTracker") != -1 && tracker.getPort() == gyro){
				gyroangle = tracker.getKey();
			}
	}

	@Override
	public Command RunOneTimeTest() {
		return new DriveTrainDiagnoserCommand(fr,fl,br,bl,gyro,keyre,keyle,keyfrp,encoders,gyroangle);
	}

	@Override
	public void RunSimultaneousTest() {
		// TODO Auto-generated method stub
		//current power speed
		double pastrpmr;
		double pastrpml;
		
		double pastencr;
		double pastencl;
		
		double pastangle;
		
		double currentfr = Database.getInstance().getNumeric(keyfrc);
		double currentfl = Database.getInstance().getNumeric(keyflc);
		double currentbr = Database.getInstance().getNumeric(keybrc);
		double currentbl = Database.getInstance().getNumeric(keyblc);
		
		if(DiagnosticThread.getInstance().getTime()%1000 == 0){
			pastrpmr = rpmr;
			pastrpml = rpml;
			pastencr = encr;
			pastencl = encl;
			pastangle = angle;
			rpmr = (((Database.getInstance().getNumeric(keyrs)*600)*(DiagnosticMap.DRIVETRAIN_GEAR_RATIO))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
			rpml = (((Database.getInstance().getNumeric(keyls)*600)*(DiagnosticMap.DRIVETRAIN_GEAR_RATIO))/DiagnosticMap.ENCODER_PER_ROTATION775)*(2*Math.PI);
			encl = Database.getInstance().getNumeric(keyle);
			encr = Database.getInstance().getNumeric(keyre);
			angle = Database.getInstance().getNumeric(gyroangle);
			torquer = (rpmr - pastrpmr);
			torquel = (rpml - pastrpml);
			deltaENCODERR = encr - pastencr;
			deltaENCODERL = encl - pastencl;
			deltaANGLE = angle - pastangle;
		}	
		if(torquer >= DiagnosticMap.MAX_TORQUE775 || torquel >= DiagnosticMap.MAX_TORQUE775 || 
		   currentfr >= DiagnosticMap.MAX_CURRENT775 || currentfl >= DiagnosticMap.MAX_CURRENT775 ||
		   currentbr >= DiagnosticMap.MAX_CURRENT775 || currentbl >= DiagnosticMap.MAX_CURRENT775){
			System.out.println("Drive Train in CRITICAL condition, lowering max speed");
			this.SpeedMultiplier -= 0.1;
		}else{
			this.SpeedMultiplier = 1.0;
		}
		if((deltaENCODERR < 0 && deltaENCODERL > 0) || (deltaENCODERR < deltaENCODERL)){
			if(deltaANGLE > 0){
				System.out.println("Drivetrain gyro: Disfunctional");
			}
		}else if((deltaENCODERR > 0 && deltaENCODERL < 0) || (deltaENCODERR > deltaENCODERL)){
			if(deltaANGLE < 0){
				System.out.println("Drivetrain gyro: Disfunctional");
			}
		}else if(deltaENCODERR == deltaENCODERL){
			if(!(deltaANGLE <= 2 && deltaANGLE >= -2)){
				System.out.println("Drivetrain gyro: Disfunctional");
			}
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