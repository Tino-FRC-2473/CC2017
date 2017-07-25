package org.usfirst.frc.team2473.framework.components;

import java.util.ArrayList;

import org.usfirst.frc.team2473.framework.trackers.ExternalDevice;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;

/**
 * This class stores all existing hardware devices on the robot.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class Devices {
	private ArrayList<CANTalon> talons; //collection of talons
	private AnalogGyro[] gyros; //collection storing the singular gryo, size could be subject to change in the future if needed
	private ArrayList<ExternalDevice> externals; //collection fo references to external devices not wired to the RIO 
	private ArrayList<AnalogInput> analogs; //collection of analog input sensors
	private ArrayList<DigitalInput> digitals; //collection of digital input sensors
	private ArrayList<Servo> servos;	 //collection of servos
	private ArrayList<Encoder> encoders;	 //collection of endcoders
	private static Devices theInstance; //serves as the static instance to use at all times
	
	static { //construct theInstance as a static function
		theInstance = new Devices();
	}
		
	private Devices() { //private constructor prevents the creation of such an object elsewhere, forcing the use of the public static getInstance()
		talons = new ArrayList<CANTalon>();
		gyros = new AnalogGyro[1];
		externals = new ArrayList<ExternalDevice>();
		analogs = new ArrayList<AnalogInput>();
		servos = new ArrayList<Servo>();
		encoders = new ArrayList<Encoder>();
	}

	/**
	 * Returns a usable instance of this class for program use.
	 * @return a <code>static Devices</code> object
	 */
	public static Devices getInstance() { 
		return theInstance;
	}

	/**
	 * Removes a given talon from the collection of talons in this class.
	 * @param port an <code>int</code> value representing the device id of the talon to be removed.
	 */
	public void removeTalon(int port) {
		for(CANTalon talon : talons) //loop through the talons stored in this class
			if(talon.getDeviceID() == port) { //if the device id matches, remove the talon and break out of the loop
				talons.remove(talon); 	
				break;
			}
	}
	
	/**
	 * Returns a talon with the given device id
	 * @param id an <code>int</code> value representing the device id of the talon
	 * @return a <code>CANTalon</code> object with the given device id
	 */
	public CANTalon getTalon(int id) {
		/* loop through the collection of existing talons:
		 * if a talon exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the talon doesn't exist, the loop will go to completion, with nothing returned
		 * after which the talon can be added through addTalon and then returned in the next line as the last element in the collection
		 */
		for(CANTalon talon : talons) if(talon.getDeviceID() == id) return talon;
		addTalon(id);
		return talons.get(talons.size() - 1);
	}
	
	/**
	 * Adds a talon to the collection of talons with the given device id
	 * @param id an <code>int</code> value representing the device id of the talon
	 */
	public void addTalon(int id) {
		talons.add(new CANTalon(id));
	}

	/**
	 * Removes a given servo from the collection of servos in this class.
	 * @param port an <code>int</code> value representing the port number of the servo to be removed.
	 */
	public void removeServo(int port) {
		for(Servo servo : servos) { //loop through the servos stored in this class
			if(servo.getChannel()==port) { //if the channel port matches, remove the servo and break out of the loop		
				servos.remove(servo);
				break;	
			}
		}
	}
	
	/**
	 * Returns a servo with the given channel port
	 * @param port an <code>int</code> value representing the channel port of the servo
	 * @return a <code>Servo</code> object with the given channel port
	 */
	public Servo getServo(int port) {
		/* loop through the collection of existing servos:
		 * if a servo exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the servo doesn't exist, the loop will go to completion, with nothing returned
		 * after which the servo can be added through addServo and then returned in the next line as the last element in the collection
		 */
		for(Servo servo : servos) if(servo.getChannel()==port) return servo;
		addServo(port);
		return servos.get(servos.size()-1);
	}
	
	/**
	 * Adds a servo to the collection of servos with the given channel port
	 * @param port an <code>int</code> value representing the channel port of the servo
	 */
	public void addServo(int port) {
		servos.add(new Servo(port));
	}
	
	/**
	 * Removes a given analog input sensor from the collection of such sensors in this class.
	 * @param channel an <code>int</code> value representing the channel port of the analog input sensor to be removed.
	 */	
	public void removeAnalogInput(int channel) {
		for(AnalogInput input : analogs) //loop through the analog inputs stored in this class
			if(input.getChannel() == channel) { //if the channel port matches, remove the sensor and break out of the loop
				analogs.remove(input);
				break;
			}
	}
	
	/**
	 * Returns a analog input sensor with the given channel port
	 * @param channel an <code>int</code> value representing the channel port of the sensor
	 * @return a <code>AnalogInput</code> object with the given channel port
	 */
	public AnalogInput getAnalogInput(int channel) {
		/* loop through the collection of existing analog input sensors:
		 * if the sensor exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the sensor doesn't exist, the loop will go to completion, with nothing returned
		 * after which the sensor can be added through addAnalogInput and then returned in the next line as the last element in the collection
		 */
		for(AnalogInput input : analogs) if(input.getChannel() == channel) return input;
		addAnalogInput(channel);
		return analogs.get(analogs.size() - 1);
	}
	
	/**
	 * Adds a analog input sensor to the collection of such sensors with the given channel port
	 * @param channel an <code>int</code> value representing the channel port of the servo
	 */
	public void addAnalogInput(int channel) {
		analogs.add(new AnalogInput(channel));
	}
	
	/**
	 * Removes a given digital input sensor from the collection of such sensors in this class.
	 * @param channel an <code>int</code> value representing the channel port of the digital input sensor to be removed.
	 */	
	public void removeDigitalInput(int channel) {
		for(DigitalInput input : digitals) //loop through the analog inputs stored in this class
			if(input.getChannel() == channel) { //if the channel port matches, remove the sensor and break out of the loop
				analogs.remove(input);
				break;
			}
	}
	
	/**
	 * Returns a digital input sensor with the given channel port
	 * @param channel an <code>int</code> value representing the channel port of the sensor
	 * @return a <code>DigitalInput</code> object with the given channel port
	 */
	public DigitalInput getDigitalInput(int channel) {
		/* loop through the collection of existing digital input sensors:
		 * if the sensor exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the sensor doesn't exist, the loop will go to completion, with nothing returned
		 * after which the sensor can be added through addDigitalInput and then returned in the next line as the last element in the collection
		 */
		for(DigitalInput input : digitals) if(input.getChannel() == channel) return input;
		addDigitalInput(channel);
		return digitals.get(digitals.size() - 1);
	}
	
	/**
	 * Adds a analog input sensor to the collection of such sensors with the given channel port
	 * @param channel an <code>int</code> value representing the channel port of the servo
	 */
	public void addDigitalInput(int channel) {
		digitals.add(new DigitalInput(channel));
	}
	
	/**
	 * Removes a given external device from the collection of such devices in this class
	 * @param name a <code>String</code> value representing the reference name of the external device to be removed
	 */
	public void removeExternalDevice(String name) {
		for(ExternalDevice external : externals) //loop through all the external devices stored in this class
			if(external.getName().equals(name)) { //if the reference name matches, remove the device and break out of the loop
				externals.remove(external);
				break;
			}
	}
	
	/**
	 * Returns an external device with the given referene name
	 * @param name a <code>String</code> value representing the reference name of the device
	 * @return an <code>ExternalDevice</code> object with the given reference name
	 */
	public ExternalDevice getExternalDevice(String name) {
		/* loop through the collection of existing external devices:
		 * if the device exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the device doesn't exist, the loop will go to completion, with nothing returned
		 * after which the device can be added through addExternalDevice and then returned in the next line as the last element in the collection
		 */
		for(ExternalDevice external : externals) if(external.getName().equals(name)) return external; 
		addExternalDevice(name);
		return externals.get(externals.size() - 1);
	}
	
	/**
	 * Adds an external device to the collection of such devices with the given reference name
	 * @param name an <code>String</code> value representing the reference name of the device
	 */
	public void addExternalDevice(String name) {
		externals.add(new ExternalDevice(name));
	}
	
	/**
	 * Removes the gyro from the collection of gyros.
	 */
	public void removeGyro() {
		gyros[0] = null;
	}
	
	
	/**
	 * Returns an gyro with the given channel port
	 * @param port a <code>int</code> value representing the channel port of the gyro
	 * @return an <code>AnalogGyro</code> object with the given channel port
	 */
	public AnalogGyro getGyro(int port) {
		/* check the collection of existing gyros:
		 * if the gyro exists in the collection, return it, effectively breaking out of the loop and the function
		 * if the gyro doesn't exist: add it to the collection
		 * then return it in the next line as the last element in the collection
		 */
		if(gyros[0] != null) return gyros[0]; 
		setGyro(port);
		return gyros[0];
	}
	
	/**
	 * Sets the robot's gyro to the collection of such sensors with the given channel port
	 * @param port an <code>int</code> value representing the channel port of the gyro
	 */
	public void setGyro(int port) {
		gyros[0] = new AnalogGyro(port);
	}
}