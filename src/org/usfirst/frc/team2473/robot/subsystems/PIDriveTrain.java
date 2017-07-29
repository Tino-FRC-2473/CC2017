package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDriveTrain extends PIDSubsystem {

	private static final double KP = 0.035;
	private static final double KI = 0.0005;
	private static final double KD = 0.035;

	private RobotDrive driver;
	private AHRS gyro;

	private double rotateToAngleRate;

	private CANTalon frontLeft, frontRight, backLeft, backRight;

	private static final double K_TOLERANCE_DEGREES = 2.0f;

	// Initialize your subsystem here
	public PIDriveTrain() {
		super(KP, KI, KD);

		rotateToAngleRate = 0;

		try {
			gyro = new AHRS(SPI.Port.kMXP);
			gyro.zeroYaw();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		setInputRange(-180.0f, 180.0f);
		setOutputRange(-1.0, 1.0);
		setAbsoluteTolerance(K_TOLERANCE_DEGREES);
		getPIDController().setContinuous(true);
		setSetpoint(gyro.getYaw());

		frontLeft = new CANTalon(RobotMap.FRONT_LEFT);
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT);
		backLeft = new CANTalon(RobotMap.BACK_LEFT);
		backRight = new CANTalon(RobotMap.BACK_RIGHT);

		driver = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	}

	public void initDefaultCommand() {

	}

	protected double returnPIDInput() {
		return gyro.getYaw();
	}

	protected void usePIDOutput(double output) {
		rotateToAngleRate = output;
	}

	public void drive(double speed, double rotation) {
		driver.arcadeDrive(speed, rotation);
	}

	public AHRS getGyro() {
		return gyro;
	}

	public double getAngleRate() {
		return rotateToAngleRate;
	}
}