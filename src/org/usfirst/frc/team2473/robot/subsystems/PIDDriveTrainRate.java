package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDDriveTrainRate extends PIDSubsystem {

	private static final double KP = 0;
	private static final double KI = 0;
	private static final double KD = 0;

	private RobotDrive driver;

	private double pidValue;

	private static final double K_TOLERANCE_DEGREES = 2.0f;

	public PIDDriveTrainRate() {
		super(KP, KI, KD); 

		pidValue = 0;

		Devices.getInstance().getNavXGyro().zeroYaw();
		
		setInputRange(-180.0f, 180.0f);
		setOutputRange(-1.0, 1.0);
		setAbsoluteTolerance(K_TOLERANCE_DEGREES);
		getPIDController().setContinuous(true); 
		
		driver = new RobotDrive(Devices.getInstance().getTalon(RobotMap.FRONT_LEFT), Devices.getInstance().getTalon(RobotMap.BACK_LEFT), Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT), Devices.getInstance().getTalon(RobotMap.BACK_RIGHT));
	}

	public void initDefaultCommand() {

	}

	protected double returnPIDInput() {
		return Database.getInstance().getNumeric(RobotMap.GYRO_RATE);
	}

	protected void usePIDOutput(double output) {
		pidValue = output;
	}

	public void drive(double speed, double rotation) {
		driver.arcadeDrive(speed, rotation);
	}

	public AHRS getGyro() {
		return Devices.getInstance().getNavXGyro();
	}

	public double getPidValue() {
		return pidValue;
	}
}