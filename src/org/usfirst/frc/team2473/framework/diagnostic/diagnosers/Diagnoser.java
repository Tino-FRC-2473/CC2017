package org.usfirst.frc.team2473.framework.diagnostic.diagnosers;

import edu.wpi.first.wpilibj.command.Command;

public abstract class Diagnoser {
	public abstract Command RunOneTimeTest();
	public abstract void RunSimultaneousTest();
	public abstract double getMultiplier();
}
