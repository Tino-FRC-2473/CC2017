package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Servo;

public class DeviceThread extends Thread{
	private static Map<String,DoubleSupplier> suppliers = new HashMap<String, DoubleSupplier>();
	
	public static ArrayList<String> motorsvolt = new ArrayList<String>();
	public static ArrayList<String> motorspow = new ArrayList<String>();
	public static ArrayList<String> servos = new ArrayList<String>();
	public static ArrayList<String> gyros = new ArrayList<String>();
	
	//public static ArrayList<CANTalon> motorrefs = new ArrayList();
	//public static ArrayList<Servo> servorefs = new ArrayList();
	//public static ArrayList<AnalogGyro> gyrorefs = new ArrayList();
	
	public enum Type{
		MOTOR_VOLTAGE,MOTOR_POWER,SERVO,GYRO
	}
	
	public void run(){
		while(isAlive()){
			update();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addDevice(String ref, DoubleSupplier sup, Type type, Object object){
		suppliers.put(ref,sup);
		if(type == Type.MOTOR_POWER){
			motorspow.add(ref);
			//motorrefs.add(object);
		}
		if(type == Type.MOTOR_VOLTAGE){
			motorsvolt.add(ref);
		}
		if(type == Type.GYRO){
			gyros.add(ref);
		}
		if(type == Type.SERVO){
			servos.add(ref);
		}
	}
	
	private void update(){
		for (String key : suppliers.keySet()){
			DataBase.setDeviceValues(key, suppliers.get(key).getAsDouble());
		}
	}
}
