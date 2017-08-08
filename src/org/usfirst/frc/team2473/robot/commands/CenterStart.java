package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;

public class CenterStart extends AccelStraight{

	public CenterStart() {
		super(50000, 2500);
	}

	@Override
	public void execute() {
		if(!finished) {
			super.execute();
		}
	}
}
