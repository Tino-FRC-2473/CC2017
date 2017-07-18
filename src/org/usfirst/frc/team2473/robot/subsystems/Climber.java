package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public CANTalon ClimberMotorOne;
    public CANTalon ClimberMotorTwo;
    
    DigitalInput climberLimitSwitch;

    public Climber() {
    	ClimberMotorOne = new CANTalon(RobotMap.climberTalonOne);
    	ClimberMotorTwo = new CANTalon(RobotMap.climberTalonTwo);
    	climberLimitSwitch = new DigitalInput(RobotMap.Climber_Limit_Swith);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean switchVal() {
    	return climberLimitSwitch.get();
    }
    
    public double getPower(String name) {
    	if(name.equals("LeftMotor")) {
    		return ClimberMotorOne.get();
    	} else if(name.equals("RightMotor")) {
    		return ClimberMotorTwo.get();
    	} else {
    		return -1;
    	}
	}
    
    public double getVoltage(String name) {
    	if(name.equals("LeftMotor")) {
    		return ClimberMotorOne.getBusVoltage();
    	} else if(name.equals("RightMotor")) {
    		return ClimberMotorTwo.getBusVoltage();
    	} else {
    		return -1;
    	}
    }
}

