package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private RobotDrive drive;
	private AHRS gyro;

	private CANTalon frontLeft, frontRight, backLeft, backRight;

	public DriveTrain() {
		frontLeft = new CANTalon(RobotMap.FL);
		frontRight = new CANTalon(RobotMap.FR);
		backLeft = new CANTalon(RobotMap.BL);
		backRight = new CANTalon(RobotMap.BR);

		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

		try {
			gyro = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public void initDefaultCommand() {

	}

	public void drive(double speed, double rotation) {
		drive.arcadeDrive(speed, rotation);
	}

	public AHRS getGyro() {
		return gyro;
	}
}
