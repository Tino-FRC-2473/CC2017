package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class DriverTrainDiagnoser extends Diagnoser {
	private CANTalon fr;
	private CANTalon fl;
	private CANTalon bl;
	private CANTalon br;
	private String keyfr;
	private String keyfl;
	private String keybl;
	private String keybr;
	private double encoders = 1600;
	public DriverTrainDiagnoser(CANTalon fr, CANTalon fl, CANTalon bl, CANTalon br, String keyfr, String keyfl, 
			String keybr, String keybl){
		this.fr = fr;
		this.fl = fl;
		this.bl = bl;
		this.br = br;
		this.keyfr = keyfr;
		this.keyfl = keyfl;
		this.keybr = keybr;
		this.keybl = keybl;
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
		
		double encoderfr = DataBase.getDeviceValue(keyfr);
		double encoderfl = DataBase.getDeviceValue(keyfl);
		double encoderbl = DataBase.getDeviceValue(keybl);
		double encoderbr = DataBase.getDeviceValue(keybr);
		if((encoderfr + encoderfl + encoderbr + encoderbl)/4 > encoders + 50 &&
				(encoderfr + encoderfl + encoderbr + encoderbl)/4 < encoders - 50){
					System.out.println("Overall System: Positive");
		}else{
			if(encoders - encoderfr < -50 || encoders - encoderfr > 50){
				System.out.println("Front Right Motor: Confirmed");
			}
			if(encoders - encoderfl < -50 || encoders - encoderfl > 50){
				System.out.println("Front Left Motor: Confirmed");
			}
			if(encoders - encoderbl < -50 || encoders - encoderbl > 50){
				System.out.println("Back Left Motor: Confirmed");
			}
			if(encoders - encoderbr < -50 || encoders - encoderbr > 50){
				System.out.println("Back Right Motor: Confirmed");
			}
		}
		
	}

	@Override
	public void RunManualTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RunSimultaneousTest() {
		// TODO Auto-generated method stub
		
	}
	
}
