package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class DisplayData extends Command {
	public DisplayData() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DT);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		gyro().zeroYaw();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Yaw: " + gyro().getYaw());
		System.out.println("Pitch: " + gyro().getPitch());
		System.out.println("Roll: " + gyro().getRoll());
		System.out.println("Is Moving: " + gyro().isMoving());
		System.out.println("Is Rotating: " + gyro().isRotating());
		System.out.println("Raw X: " + gyro().getRawGyroX());
		System.out.println("Raw Y: " + gyro().getRawGyroY());
		System.out.println("Raw Z: " + gyro().getRawGyroZ());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {

	}

	private AHRS gyro() {
		return Robot.DT.getGyro();
	}
}
