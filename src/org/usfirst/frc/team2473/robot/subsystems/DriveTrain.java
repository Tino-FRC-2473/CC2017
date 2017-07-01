package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	public CANTalon front_right, front_left, back_right, back_left;
	
    public DriveTrain(){
    	front_right = new CANTalon(RobotMap.FRONT_RIGHT);
    	front_left = new CANTalon(RobotMap.FRONT_LEFT);
    	back_right = new CANTalon(RobotMap.BACK_RIGHT);
    	back_left = new CANTalon(RobotMap.BACK_LEFT);
    }

    public void initDefaultCommand() {
        
    }
    
    public void setPow(double pow){
    	front_right.set(pow);
    	front_left.set(pow);
    	back_right.set(pow);
    	back_left.set(pow);
    }
    
    public void turnRight(double pow){
    	front_right.set(-pow);
    	back_right.set(-pow);
    	front_left.set(pow);
    	back_left.set(pow);
    }
    
    public void turnLeft(double pow){
    	front_right.set(pow);
    	back_right.set(pow);
    	front_left.set(-pow);
    	back_left.set(-pow);
    }
    
    public void resetEnconders(){
    	front_right.changeControlMode(TalonControlMode.Position);
    	front_left.changeControlMode(TalonControlMode.Position);
    	front_right.setPosition(0);
    	front_left.setPosition(0);
    	front_right.changeControlMode(TalonControlMode.PercentVbus);
    	front_left.changeControlMode(TalonControlMode.PercentVbus);
    }
    
    public double getEncPosition(String motorName){
    	switch(motorName){
    	case "fr": return front_right.get();
    	case "fl": return front_left.get();
    	case "br": return back_right.get();
    	case "bl": return back_left.get();
    	}
    	return 0;
    }
    
    public void stop(){
    	front_right.set(0);
    	front_left.set(0);
    	back_right.set(0);
    	back_left.set(0);
    }
}

