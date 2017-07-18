package org.usfirst.frc.team2473.robot.subsystems;

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
	private CANTalon leftFrontCAN;
	private CANTalon rightFrontCAN;
	private CANTalon leftBackCAN;
	private CANTalon rightBackCAN;
	
public CANTalon front_right, front_left, back_right, back_left;

	private RobotDrive drive;
	
	public DriveTrain() {
		super();
		System.out.println("DT CREATED");
		front_right = new CANTalon(RobotMap.FRONT_RIGHT);
    	front_left = new CANTalon(RobotMap.FRONT_LEFT);
    	back_right = new CANTalon(RobotMap.BACK_RIGHT);
    	back_left = new CANTalon(RobotMap.BACK_LEFT);
		
		leftFrontCAN = new CANTalon(RobotMap.FRONT_LEFT);
		rightFrontCAN = new CANTalon(RobotMap.FRONT_RIGHT);
		leftBackCAN = new CANTalon(RobotMap.BACK_LEFT);
		rightBackCAN = new CANTalon(RobotMap.BACK_RIGHT);
		
		drive = new RobotDrive(leftFrontCAN, leftBackCAN, rightFrontCAN, rightBackCAN);
		
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
    	return leftFrontCAN.get();
    }

    public double getRightPow() {
    	return rightFrontCAN.get();
    }
    
    public void driveArcade(double speed, double rotate) {
    	drive.arcadeDrive(speed, rotate);
	}

    public void resetEncoders(){
    	front_right.changeControlMode(TalonControlMode.Position);
    	front_left.changeControlMode(TalonControlMode.Position);
    	front_right.setPosition(0);
    	front_left.setPosition(0);
    	front_right.changeControlMode(TalonControlMode.PercentVbus);
    }

    
    
}