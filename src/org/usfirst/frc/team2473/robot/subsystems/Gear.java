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
	
    public CANTalon pickupTalon;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Gear() {

    	pickupTalon = new CANTalon(RobotMap.gearPickupMotor);

    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    
    public double getPower(String name) {
    	if(name.equals("pickupMotor")) {
    		return pickupTalon.get();
    	}  else {
    		return -1;
    	}
	}
    
    public double getVoltage(String name) {
    	if(name.equals("pickupMotor")) {
    		return pickupTalon.getBusVoltage();
    	} else {
    		return -1;
    	}
    }
}
