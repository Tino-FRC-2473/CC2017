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
	private String keyfrs;
	private String keyfls;
	private String keybls;
	private String keybrs;
	private String keyfrc;
	private String keyflc;
	private String keyblc;
	private String keybrc;
	
	private Joystick stick;
	
	private double EcnoderTicksPerRotation = 6000.0;
	
	private double rpmfr = 0.0;
	private double rpmfl = 0.0;
	private double rpmbr = 0.0;
	private double rpmbl = 0.0;
	double torquefr = 0.0;
	double torquefl = 0.0;
	double torquebr = 0.0;
	double torquebl = 0.0;
	
	private double encoders = 1600;
	
	private final double MAX_TORQUE = 0.03;
	private final double MAX_CURRENT = 10.0;
	
	private double SpeedMultiplier = 1.0;
	
	public DriverTrainDiagnoser(CANTalon fr, CANTalon fl, CANTalon bl, CANTalon br, Joystick stick, 
								String keyfrp, String keyflp, String keybrp, String keyblp, 
								String keyfre, String keyfle, String keybre, String keyble, 
								String keyfrs, String keyfls, String keybrs, String keybls, 
								String keyfrc, String keyflc, String keybrc, String keyblc){
		this.fr = fr;
		this.fl = fl;
		this.bl = bl;
		this.br = br;
		//encoder values
		this.keyfre = keyfre;
		this.keyfle = keyfle;
		this.keybre = keybre;
		this.keyble = keyble;
		//power values
		this.keyfrp = keyfrp;
		this.keyflp = keyflp;
		this.keybrp = keybrp;
		this.keyblp = keyblp;
		//speed values(CANTalon.getSpeed())
		this.keyfrp = keyfrs;
		this.keyflp = keyfls;
		this.keybrp = keybrs;
		this.keyblp = keybls;
		//input current values
		this.keyfrp = keyfrc;
		this.keyflp = keyflc;
		this.keybrp = keybrc;
		this.keyblp = keyblc;
		//the joystick
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
		double pastrpmfr;
		double pastrpmfl;
		double pastrpmbr;
		double pastrpmbl;
		double currentfr = DataBase.getDeviceValue(keyfrc);
		double currentfl = DataBase.getDeviceValue(keyflc);
		double currentbr = DataBase.getDeviceValue(keybrc);
		double currentbl = DataBase.getDeviceValue(keyblc);
		
		if(DiagnosticThread.getTime()%1000 == 0){
			pastrpmfr = rpmfr;
			pastrpmfl = rpmfl;
			pastrpmbr = rpmbr;
			pastrpmbl = rpmbl;
			rpmfr = ((DataBase.getDeviceValue(keyfrs)/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			rpmfl = ((DataBase.getDeviceValue(keyfls)/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			rpmbr = ((DataBase.getDeviceValue(keybrs)/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			rpmbl = ((DataBase.getDeviceValue(keybls)/100)/this.EcnoderTicksPerRotation)*(2*Math.PI);
			torquefr = (rpmfr - pastrpmfr);
			torquefl = (rpmfl - pastrpmfl);
			torquebr = (rpmbr - pastrpmbr);
			torquebl = (rpmbl - pastrpmbl);
		}
		
		if(torquefr >= this.MAX_TORQUE || torquefl >= this.MAX_TORQUE || 
		   torquebr >= this.MAX_TORQUE || torquebl >= this.MAX_TORQUE ||
		   currentfr >= this.MAX_CURRENT || currentfl >= this.MAX_CURRENT ||
		   currentbr >= this.MAX_CURRENT || currentbl >= this.MAX_CURRENT){
			System.out.println("FrontRightMotor: high torque");
			this.SpeedMultiplier -= 0.1;
		}else{
			this.SpeedMultiplier = 1.0;
		}
	}
	
	private void setPowerToALl(double pow){
		fl.set(pow);
		fr.set(pow);
		bl.set(pow);
		br.set(pow);
	}
	
}
