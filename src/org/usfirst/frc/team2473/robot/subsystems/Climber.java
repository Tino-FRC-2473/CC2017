package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public Climber() {

    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean switchVal() {
    	return Database.getInstance().getConditional(RobotMap.CLIMBER_LIMIT_SWITCH);
    }
    
    public double getPower(String name) {
    	if(name.equals("LeftMotor")) {
    		return Database.getInstance().getNumeric(RobotMap.climberTalonOnePower);
    	} else if(name.equals("RightMotor")) {
    		return Database.getInstance().getNumeric(RobotMap.climberTalonTwoPower);
    	} else {
    		return -1;
    	}
	}
    
    public double getCurrent(String name) {
    	if(name.equals("LeftMotor")) {
    		return Database.getInstance().getNumeric(RobotMap.climberTalonOneCurrent);
    	} else if(name.equals("RightMotor")) {
    		return Database.getInstance().getNumeric(RobotMap.climberTalonTwoCurrent);
    	} else {
    		return -1;
    	}
    }
    
    public void resetEncoders() {
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_ONE).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_ONE).setPosition(0);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_ONE).changeControlMode(TalonControlMode.PercentVbus);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_TWO).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_TWO).setPosition(0);
    	Devices.getInstance().getTalon(RobotMap.CLIMBER_TALON_TWO).changeControlMode(TalonControlMode.PercentVbus);
    }
}

