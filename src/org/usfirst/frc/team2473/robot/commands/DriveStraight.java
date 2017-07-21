package org.usfirst.frc.team2473.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2473.robot.Robot;

/**
 *
 */
public class DriveStraight extends Command implements PIDOutput {
	private PIDController pidController;
	private double rotateToAngleRate;

	private static final double KP = 0.035;
	private static final double KI = 0.00;
	private static final double KD = 0.04;
	private static final double KF = 0.00;

	private static final double K_TOLERANCE_DEGREES = 2.0f;

	public DriveStraight() {
		requires(Robot.DT);

		System.out.println("Started command");
		
		pidController = new PIDController(KP, KI, KD, KF, Robot.DT.getGyro(), this);
		pidController.setInputRange(-180.0f, 180.0f);
		pidController.setOutputRange(-1.0, 1.0);
		pidController.setAbsoluteTolerance(K_TOLERANCE_DEGREES);
		pidController.setContinuous(true);
		pidController.disable();
	}

	@Override
	protected void initialize() {
		System.out.println("Init");
	}

	@Override
	protected void execute() {
		if (!pidController.isEnabled()) {
			pidController.setSetpoint(Robot.DT.getGyro().getYaw());
			rotateToAngleRate = 0;
			pidController.enable();
		}
		Robot.DT.drive(Robot.oi.getThrottleStick().getZ(), rotateToAngleRate);
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
