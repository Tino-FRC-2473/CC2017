package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;

public class DriverTrainDiagnoser extends Diagnoser {
	private CANTalon fr;
	private CANTalon fl;
	private CANTalon bl;
	private CANTalon br;
	private String keyfre;
	private String keyfle;
	private String keyble;
	private String keybre;
	private String keyfrp;
	private String keyflp;
	private String keyblp;
	private String keybrp;
	
	private Joystick stick;
	
	private double EcnoderTicksPerRotation = 6000.0;
	
	private double rpmfr = 0.0;
	private double rpmfl = 0.0;
	private double rpmbr = 0.0;
	private double rpmbl = 0.0;
	
	private double encoders = 1600;
	
	private double maxtorque = 0.7;
	
	public DriverTrainDiagnoser(CANTalon fr, CANTalon fl, CANTalon bl, CANTalon br, String keyfrp, 
								String keyflp, String keybrp, String keyblp, String keyfre, 
								String keyfle, String keybre, String keyble, Joystick stick){
		this.fr = fr;
		this.fl = fl;
		this.bl = bl;
		this.br = br;
		this.keyfre = keyfre;
		this.keyfle = keyfle;
		this.keybre = keybre;
		this.keyble = keyble;
		this.keyfrp = keyfrp;
		this.keyflp = keyflp;
		this.keybrp = keybrp;
		this.keyblp = keyblp;
		this.stick = stick;
	}

	@Override
	public void RunOneTimeTest() {
		// TODO Auto-generated method stub
		fr.changeControlMode(TalonControlMode.Position);
		fl.changeControlMode(TalonControlMode.Position);
		bl.changeControlMode(TalonControlMode.Position);
		br.changeControlMode(TalonControlMode.Position);
	
		fr.setPosition(0);
		fl.setPosition(0);
		bl.setPosition(0);
		br.setPosition(0);
		
		fr.setPosition(encoders);
		fl.setPosition(encoders);
		bl.setPosition(encoders);
		br.setPosition(encoders);
		
		double encoderfr = DataBase.getDeviceValue(keyfre);
		double encoderfl = DataBase.getDeviceValue(keyfle);
		double encoderbl = DataBase.getDeviceValue(keyble);
		double encoderbr = DataBase.getDeviceValue(keybre);
		
		if((encoderfr + encoderfl + encoderbr + encoderbl)/4 > encoders + 50 &&
				(encoderfr + encoderfl + encoderbr + encoderbl)/4 < encoders - 50){
					System.out.println("Overall System: Positive");
		}else{
			if(encoders - encoderfr < -50 || encoders - encoderfr > 50){
				System.out.println("Front Right Motor: Functional");
			}else{
				System.out.println("Front Right Motor: Disfunctional");
			}
			if(encoders - encoderfl < -50 || encoders - encoderfl > 50){
				System.out.println("Front Left Motor: Functional");
			}else{
				System.out.println("Front Left Motor: Disfunctional");
			}
			if(encoders - encoderbl < -50 || encoders - encoderbl > 50){
				System.out.println("Back Left Motor: Functional");
			}else{
				System.out.println("Back Left Motor: Disfunctional");
			}
			if(encoders - encoderbr < -50 || encoders - encoderbr > 50){
				System.out.println("Back Right Motor: Functional");
			}else{
				System.out.println("Back Right Motor: Disfunctional");
			}
		}
		
	}

	@Override
	public void RunManualTest() {
		// TODO Auto-generated method stub
		this.setPowerToALl(stick.getY());
		System.out.println("frontright Power: " + DataBase.getDeviceValue(keyfrp));
		System.out.println("backright Power: " + DataBase.getDeviceValue(keybrp));
		System.out.println("frontleft Power: " + DataBase.getDeviceValue(keyflp));
		System.out.println("backleft Power: " + DataBase.getDeviceValue(keyblp));
	}

	@Override
	public void RunSimultaneousTest() {
		// TODO Auto-generated method stub
		//current power speed
		double torquefr;
		double torquefl;
		double torquebr;
		double torquebl;
		if(DiagnosticThread.getTime()%1000 == 0){
			double pastrpmfr = rpmfr;
			double pastrpmfl = rpmfl;
			double pastrpmbr = rpmbr;
			double pastrpmbl = rpmbl;
			rpmfr = ((fr.getSpeed()/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			rpmfl = ((fl.getSpeed()/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			rpmbr = ((br.getSpeed()/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			rpmbl = ((bl.getSpeed()/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			torquefr = (rpmfr - pastrpmfr);
			torquefl = (rpmfl - pastrpmfl);
			torquebr = (rpmbr - pastrpmbr);
			torquebl = (rpmbl - pastrpmbl);
		}
	}
	
	private void setPowerToALl(double pow){
		fl.set(pow);
		fr.set(pow);
		bl.set(pow);
		br.set(pow);
	}
	
}
