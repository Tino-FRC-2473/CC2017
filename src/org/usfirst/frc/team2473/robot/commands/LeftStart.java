package org.usfirst.frc.team2473.robot.commands;

import org.usfirst.frc.team2473.robot.Robot;

public class LeftStart extends AccelStraight{
	
	private boolean beganStageTwo = false;
	private boolean finishedStageTwo = false;
	
	private long startTurnTime = 0;
	
	public LeftStart() {
		super(50000, 2500);
	}
	
	@Override
	public void execute() {
		// Stage 1 ---> Moving forward
		if(!finished) {
			System.out.println("Stage 1");
			super.execute();
			return;
		}
		
		
		// Stage 2 --> here we need to turn in place 45 degrees
		else if(!beganStageTwo) {
			System.out.println("Stage 2");
			rotateToAngleRate += 45;
			Robot.driveTrain.drive(0, rotateToAngleRate);
			beganStageTwo = true;
			startTurnTime = System.currentTimeMillis();
		}
		/*
		else if(!finishedStageTwo) {
			if(System.currentTimeMillis() - startTurnTime > 2500) {
				finishedStageTwo = true;
			}
		}
		
		else if(finishedStageTwo) {
			// Here you need to communicate with cv
			completelyFinished = true;
		}*/
		
		//completelyFinished = true;
	}
	
}
