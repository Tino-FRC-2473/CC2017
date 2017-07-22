package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputDiagnoser extends Diagnoser {
	int deviceID;
	DigitalInput limitSwitch;
	private double range;
	public DigitalInputDiagnoser(int ID, double r){
		deviceID = ID;
		limitSwitch = new DigitalInput(ID);
		range = r;
	}
	
	@Override
	public void RunOneTimeTest() {
		// TODO Auto-generated method stub
		while(limitSwitch.get()){
			System.out.println("On");
		}
		System.out.println("Off");
	}

	@Override
	public void RunSimultaneousTest() {
		// TODO Auto-generated method stub
		
	}
	
	public void turnOff(){
		System.out.println("TURN OFF RIGHT NOW!!!! DO YOU WANT TO GET ROASTED BY WILLIAMS!!???!!");
	}
}
