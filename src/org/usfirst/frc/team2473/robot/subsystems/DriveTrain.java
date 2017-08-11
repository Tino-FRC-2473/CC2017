package org.usfirst.frc.team2473.robot.subsystems;

import javax.swing.SortingFocusTraversalPolicy;

import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	CANTalon frontRightTalon;
	CANTalon frontLeftTalon;
	CANTalon backRightTalon;
	CANTalon backLeftTalon;

	private AHRS gyro;
	private RobotDrive drive;
	
	public int leftEncoderCap = 0;
	public int rightEncoderCap = 0;

	public DriveTrain() {
		frontRightTalon = new CANTalon(RobotMap.FRONT_RIGHT);
		frontLeftTalon = new CANTalon(RobotMap.FRONT_LEFT);
		backRightTalon = new CANTalon(RobotMap.BACK_RIGHT);
		backLeftTalon = new CANTalon(RobotMap.BACK_LEFT);
		
		try {
			gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		drive = new RobotDrive(frontLeftTalon, backLeftTalon, frontRightTalon, backRightTalon);
	}

	public void setPower(double pow) {
		System.out.println("pow = " + pow);
		frontRightTalon.set(-pow);
		frontLeftTalon.set(pow);
		backRightTalon.set(-pow);
		backLeftTalon.set(pow);
		
	}

	public void resetEncoders() {
		leftEncoderCap = (int) frontLeftTalon.getPosition();
		rightEncoderCap = (int) frontRightTalon.getPosition();
	}

	public int getRightEnc() {
		return Math.abs(frontRightTalon.getEncPosition());
	} 

	public int getLeftEnc() {
		return Math.abs(frontLeftTalon.getEncPosition()) ;
	}

	public AHRS getGyro() {
		return gyro;
	}
	
	public void resetGyro() {
		gyro.zeroYaw();
	}

	public void drive(double d, double rotateToAngleRate) {
		drive.arcadeDrive(-d, rotateToAngleRate);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	

}
