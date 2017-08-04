package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Gear() {
    	
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void resetEncoders() {
    	Devices.getInstance().getTalon(RobotMap.GEAR_PICKUP_MOTOR).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.GEAR_PICKUP_MOTOR).setPosition(0);
    	Devices.getInstance().getTalon(RobotMap.GEAR_PICKUP_MOTOR).changeControlMode(TalonControlMode.PercentVbus);
    }
	public double getPower(String name) {
		return Database.getInstance().getNumeric(RobotMap.GEAR_PICKUP_POWER);
	}
    public double getVoltage(String name) {
		return Database.getInstance().getNumeric(RobotMap.gearPickupTalonCurrent);
    }
}
