package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.command.Command;

public abstract class Diagnoser {
	public abstract Command RunOneTimeTest();
	public abstract void RunSimultaneousTest();
	public abstract double getMultiplier();
}
