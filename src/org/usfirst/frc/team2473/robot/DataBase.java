package org.usfirst.frc.team2473.robot;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;

public class DataBase {
	private static Map<String,ThreadSafeDouble> DoubleValues = new HashMap();
	
	public static double getDeviceValue(String key){
		return DoubleValues.get(key).getValue();
	}
	
	public static void setDeviceValues(String key, Double value){
		DoubleValues.get(key).setValue(value);
	}
}
