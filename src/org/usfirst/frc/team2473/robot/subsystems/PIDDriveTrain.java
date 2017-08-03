package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDDriveTrain extends PIDSubsystem {

	// KP, KI, and KD values used for PID
	private static final double KP = 0.035;
	private static final double KI = 0.0005;
	private static final double KD = 0.048;

	private RobotDrive driver;
	private AHRS gyro; // navx mxp

	private double rotateToAngleRate; // the value changed by PID

	private CANTalon frontLeft, frontRight, backLeft, backRight;

	private static final double K_TOLERANCE_DEGREES = 2.0f; // the absolute error that is tolerable in the PID system

	public PIDDriveTrain() {
		super(KP, KI, KD); // creates a PID controller with the KP, KI, and KD values

		rotateToAngleRate = 0;

		// instantiates the gyro
		try {
			gyro = new AHRS(SPI.Port.kMXP);
			gyro.zeroYaw();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		setInputRange(-180.0f, 180.0f); // sets the maximum and minimum values expected from the gyro
		setOutputRange(-1.0, 1.0); // sets the maximum and minimum output values
		setAbsoluteTolerance(K_TOLERANCE_DEGREES); // sets the absolute error that is tolerable in the PID system
		getPIDController().setContinuous(true); // sets the PID Controller to think of the gyro input as continuous

		// instantiates the CANTalons
		frontLeft = new CANTalon(RobotMap.FL);
		frontRight = new CANTalon(RobotMap.FR);
		backLeft = new CANTalon(RobotMap.BL);
		backRight = new CANTalon(RobotMap.BR);

		// Creates a robot driver
		driver = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	}

	public void initDefaultCommand() {

	}

	/**
	 * Returns the input used in the PID calculations
	 * 
	 * @return the input used in the PID calculations
	 */
	protected double returnPIDInput() {
		return gyro.getYaw();
	}

	/**
	 * Uses KP, KI, KD, and the input from returnPIDInput() to change and output
	 * value
	 */
	protected void usePIDOutput(double output) {
		double out = output;

		if (Math.abs(out) < 0.01) {
			out = 0;
		} else if (Math.abs(out) < 0.2) {
			out = 0.2 * Math.signum(out);
		}

		rotateToAngleRate = out;
	}

	/**
	 * Drives the robot given the speed and the rotation angle.
	 */
	public void drive(double speed, double rotation) {
		driver.arcadeDrive(speed, rotation);
	}

	/**
	 * Stops the robot
	 */
	public void stop() {
		driver.arcadeDrive(0, 0);
	}

	/**
	 * @return the gyro object
	 */
	public AHRS getGyro() {
		return gyro;
	}

	/**
	 * @return the value changed by the PID system
	 */
	public double getAngleRate() {
		return rotateToAngleRate;
	}

	/**
	 * Sets the desired angle in the PID system
	 * 
	 * @param angle
	 */
	public void setTargetAngle(double angle) {
		setSetpoint(angle);
	}
}
