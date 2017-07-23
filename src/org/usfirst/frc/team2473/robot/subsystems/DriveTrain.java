package org.usfirst.frc.team2473.robot.subsystems;

import org.usfirst.frc.team2473.framework.components.Devices;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;
import org.usfirst.frc.team2473.robot.commands.JoystickControl;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private RobotDrive drive;
	
	public DriveTrain() {
		super();
		System.out.println("DT CREATED");

		drive = new RobotDrive(Devices.getInstance().getTalon(RobotMap.FRONT_LEFT),Devices.getInstance().getTalon(RobotMap.BACK_LEFT), Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT), Devices.getInstance().getTalon(RobotMap.BACK_RIGHT));
		
		drive.setMaxOutput(.70);
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		
	}

    public void initDefaultCommand() {
         setDefaultCommand(new JoystickControl());
    }
    
    public void drive(double left, double right) {
    	drive.tankDrive(left, right);
   
	}
    
    public double getLeftPow() {
    	return Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).get();
    }

    public double getRightPow() {
    	return Devices.getInstance().getTalon(RobotMap.BACK_RIGHT).get();
    }
    
    public void driveArcade(double speed, double rotate) {
    	drive.arcadeDrive(speed, rotate);
	}

   
    public void resetEncoders(){
    	
    	Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).changeControlMode(TalonControlMode.Position);
    	Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).setEncPosition(0);
    	Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).setEncPosition(0);
    	Devices.getInstance().getTalon(RobotMap.FRONT_RIGHT).changeControlMode(TalonControlMode.PercentVbus);
    	Devices.getInstance().getTalon(RobotMap.FRONT_LEFT).changeControlMode(TalonControlMode.PercentVbus);
    }
    
}