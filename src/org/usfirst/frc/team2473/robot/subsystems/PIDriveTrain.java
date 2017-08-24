package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDriveTrain extends PIDSubsystem {
	private static final double KP = 0.035;
	private static final double KI = 0.0005;
	private static final double KD = 0.035;

	private RobotDrive drive;
	private AHRS gyro;

	private double rotateToAngleRate;

	private static final double K_TOLERANCE_DEGREES = 2.0f;

	// Initialize your subsystem here
	public PIDriveTrain() {
		super(KP, KI, KD);

		rotateToAngleRate = 0; //based on PID

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

		drive = new RobotDrive(Devices.getInstance().getTalon(RobotMap.FRONT_LEFT),
				Devices.getInstance().getTalon(RobotMap.BACK_LEFT),
				Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT),
				Devices.getInstance().getTalon(RobotMap.BACK_RIGHT));

		drive.setMaxOutput(.70);
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
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
		drive.arcadeDrive(speed, rotation);
	}

	public AHRS getGyro() {
		return gyro;
	}

	public double getAngleRate() {
		return rotateToAngleRate;
	}

	public void setTargetAngle(double angle) {
		setSetpoint(angle);
	}

	public void resetEncoders() {
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).changeControlMode(TalonControlMode.Position);
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).setEncPosition(0);
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).setEncPosition(0);
		Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).changeControlMode(TalonControlMode.PercentVbus);
		Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).changeControlMode(TalonControlMode.PercentVbus);
	}
}
