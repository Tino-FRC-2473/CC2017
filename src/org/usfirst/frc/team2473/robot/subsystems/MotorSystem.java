package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.framework.readers.Parser;
import org.usfirst.frc.team2473.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MotorSystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void moveMotor(double pow) {
    	Devices.getInstance().getTalon(Parser.id(RobotMap.getInstance().TALON_ONE)).set(pow);
    }
    
    public double encPosition() {
    	return Database.getInstance().getNumeric(Parser.key(RobotMap.getInstance().TALON_ONE, "encoder"));
    }
}

