package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.robot.Robot;

/**
 *
 */
public class DriveStraight extends Command implements PIDOutput {
	private PIDController turnController;
	private double rotateToAngleRate;

	private static final double KP = 0.03;
	private static final double KI = 0.00;
	private static final double KD = 0.00;
	private static final double KF = 0.00;

	private static final double K_TOLERANCE_DEGREES = 2.0f;

	public DriveStraight() {
		requires(Robot.driveTrain);

		turnController = new PIDController(KP, KI, KD, KF, Robot.driveTrain.getGyro(), this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(K_TOLERANCE_DEGREES);
		turnController.setContinuous(true);
		turnController.disable();
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		if (!turnController.isEnabled()) {
			turnController.setSetpoint(Robot.driveTrain.getGyro().getYaw());
			rotateToAngleRate = 0;
			turnController.enable();
		}
		Robot.driveTrain.drive(Robot.oi.getThrottleStick().getThrottle(), rotateToAngleRate);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}
}
