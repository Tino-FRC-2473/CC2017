package org.usfirst.frc.team2473.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem{

	CANTalon frontRightTalon = new CANTalon(RobotMap.FRONT_RIGHT);
	CANTalon frontLeftTalon = new CANTalon(RobotMap.FRONT_LEFT);
	CANTalon backRightTalon = new CANTalon(RobotMap.BACK_RIGHT);
	CANTalon backLeftTalon = new CANTalon(RobotMap.BACK_LEFT);
	
	private AHRS gyro;
	private RobotDrive drive;
	
	public DriveTrain(){
		try {
			gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		drive = new RobotDrive(frontLeftTalon, backLeftTalon, frontRightTalon, backRightTalon);
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void setPower(double pow){
		frontRightTalon.set(pow);
		frontLeftTalon.set(pow);
		backRightTalon.set(pow);
		backLeftTalon.set(pow);
	}
	
	public void resetEncoders(){
		frontRightTalon.changeControlMode(TalonControlMode.Position);
		frontLeftTalon.changeControlMode(TalonControlMode.Position);
		frontRightTalon.setPosition(0);
		frontLeftTalon.setPosition(0);
		
		frontRightTalon.changeControlMode(TalonControlMode.PercentVbus);
		frontLeftTalon.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public int getRightEnc(){return frontRightTalon.getEncPosition();}
	
	public int getLeftEnc(){return frontLeftTalon.getEncPosition();}
	
	public AHRS getGyro(){
		return gyro;
	}

	public void drive(double d, double rotateToAngleRate) {
		drive.arcadeDrive(d, rotateToAngleRate);
	}

}
