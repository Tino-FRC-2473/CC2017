package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;

public class RightStart extends AccelStraight{

	private boolean beganStageTwo = false;
	private boolean finishedStageTwo = false;
	
	private long startTurnTime = 0;
	
	public RightStart() {
		super(50000, 2500);
	}
	
	@Override
	public void execute() {
		// Stage 1 ---> Moving forward
		if(!finished) {
			super.execute();
		}
		
		// Stage 2 --> here we need to turn in place 45 degrees
		else if(!beganStageTwo) {
			Robot.driveTrain.drive(0, rotateToAngleRate - 45);
			beganStageTwo = true;
			startTurnTime = System.currentTimeMillis();
		}
		
		else if(!finishedStageTwo) {
			if(System.currentTimeMillis() - startTurnTime > 2500) {
				finishedStageTwo = true;
			}
		}
		
		else if(finishedStageTwo) {
			// Here you need to communicate with cv
			completelyFinished = true;
		}
	}
}
