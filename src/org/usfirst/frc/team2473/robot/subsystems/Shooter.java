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
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public Shooter() {

    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getPower(String name) {
    	if(name.equals("LeftMotor")) return Database.getInstance().getNumeric(RobotMap.shooterTalonOnePower);
    	if(name.equals("RightMotor")) return Database.getInstance().getNumeric(RobotMap.shooterTalonTwoPower);
    	return -1;
	}
    
    public double getVoltage(String name) {
    	if(name.equals("LeftMotor")) return Database.getInstance().getNumeric(RobotMap.shooterTalonOneCurrent);
    	if(name.equals("RightMotor")) return Database.getInstance().getNumeric(RobotMap.shooterTalonTwoCurrent);
    	return -1;
    }

	public void setPowerShoot(double i) {
//		talonOne.set(i);
		Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_ONE).set(i);
		// TODO Auto-generated method stub
		
	}
	public void setPowerIntake(double i) {
//		talonTwo.set(i);
		Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_TWO).set(i);
		// TODO Auto-generated method stub
		
	}


	public double getTalonOneEncoder() {
		// TODO Auto-generated method stub
		return Database.getInstance().getNumeric(RobotMap.SHOOTER_TALON1_ENC);
	}
	

    
    //public double getPosition() {
    	//return servo.getPosition();
    //}
	
	public void resetEncoders() {
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_ONE).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_ONE).setPosition(0);
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_ONE).changeControlMode(TalonControlMode.PercentVbus);
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_TWO).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_TWO).setPosition(0);
    	Devices.getInstance().getTalon(RobotMap.SHOOTER_TALON_TWO).changeControlMode(TalonControlMode.PercentVbus);
    }
	
	
}

