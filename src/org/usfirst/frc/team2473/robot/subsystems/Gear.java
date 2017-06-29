package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
	
    CANTalon talonLeft;
    CANTalon talonRight;
    CANTalon talonPivot;
    AnalogGyro gyro;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Gear() {

    	talonLeft = new CANTalon(RobotMap.gearTalonLeft);
    	talonRight = new CANTalon(RobotMap.gearTalonRight);
    	talonPivot = new CANTalon(RobotMap.gearPivotTalon);
    	gyro = new AnalogGyro(RobotMap.gyro);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    
    public double getPower(String name) {
    	if(name.equals("LeftMotor")) {
    		return talonLeft.get();
    	} else if(name.equals("RightMotor")) {
    		return talonRight.get();
    	} else  if(name.equals("PivotMotor")) {
    		return talonPivot.get();
    	} else {
    		return -1;
    	}
	}
    
    public double getVoltage(String name) {
    	if(name.equals("LeftMotor")) {
    		return talonLeft.getBusVoltage();
    	} else if(name.equals("RightMotoor")) {
    		return talonRight.getBusVoltage();
    	}  else if(name.equals("PivotMotoor")) {
    		return talonPivot.getBusVoltage();
    	}else {
    		return -1;
    	}
    }
    
    public double getAngle() {
    	return gyro.getAngle();
    }
}

