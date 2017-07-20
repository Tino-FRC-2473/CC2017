package org.usfirst.frc.team2473.framework.trackers;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.usfirst.frc.team2473.framework.threading.StringSupplier;

/**
 * Parent class for device trackers for string, numerical, and logic-related values.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 * */
public class DeviceTracker {
	private String key; //key for accessing the device tracker
	private Type dataType; //each device tracker can only store up to 1 data type
	private int port; //port number that the device is plugged into, or its device id, depending on the device
	private DoubleSupplier evokeNumeric; //if the data type is numeric, the double supplier is the value supplier
	private BooleanSupplier evokeConditional; //if the data type is conditional, the boolean supplier is the value supplier
	private StringSupplier evokeMessage; //if the data type is a message, the string supplier is the value supplier
	
	/**
	 * Constructor with options for key, data type, and device id information.
	 * @param key <code>String</code> representing the reference id of the tracker. Can be named anything.
	 * @param type <code>Type</code> representing the data type of this tracker: conditional, numeric, or message
	 * @param port <code>int</code> value representing the port number of the sensor or OI device, or its device id
	 * @see Type
	 * */
	public DeviceTracker(String key, Type type, int port) { 
		this.key = key;
		dataType = type;
		this.port = port;
	}
	
	/**
	 * Represents the tracked value type
	 * */
	public enum Type {
		/**
		 * Numeric Value
		 * */
		NUMERIC, 
		/**
		 * Conditional Value
		 * */
		CONDITIONAL, 
		/**
		 * Message Value
		 * */
		MESSAGE
	}
	
	/**
	 * Returns the user-assigned reference key.
	 * @return key value as a <code>String</code> 
	 * */
	public String getKey() {
		return key;
	}
	
	/**
	 * Allows the key to be assigned.
	 * @param newKey a <code>String</code> value representing the device tracker's new reference key assignment
	 * */
	public void setKey(String newKey) {
		key = newKey;
	}
	
	/**
	 * Returns the user-assigned data type of the tracker.
	 * @return the <code>Type</code> value representing the value type of this device value tracker
	 * @see Type
	 * */
	public Type getType() {
		return dataType;
	}
	
	/**
	 * Allows the port number of the linked device to be reassigned.
	 * @param port an <code>int</code> value representing the new hardware device's port/identification information 
	 * */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Returns the user-assigned identification information of the hardware device being tracked.
	 * @return the <code>int</code> value representing the port number or device id
	 * */
	public int getPort() {
		return port;
	}
	
	/**
	 * Returns the assigned numerical call value for <code>Type.NUMERIC</code> trackers
	 * @return the <code>DoubleSupplier</code> object representing the numerical value of this <code>DeviceTracker</code>.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/DoubleSupplier.html"><code>DoubleSupplier</code></a>
	 * @throws NullPointerException if <code>dataType</code> is not <code>Type.NUMERIC</code>.
	 * */
	public DoubleSupplier getterNumeric() {
		if(evokeNumeric != null) return evokeNumeric;
		else throw new NullPointerException("No numeric getter set");
	}
	
	/**
	 * Returns the assigned conditional call value for <code>Type.CONDITIONAL</code> trackers
	 * @return the <code>BooleanSupplier</code> object representing the conditional value of this <code>DeviceTracker</code>.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/BooleanSupplier.html"><code>BooleanSupplier</code></a>
	 * @throws NullPointerException if <code>dataType</code> is not <code>Type.CONDITIONAL</code>.
	 * */
	public BooleanSupplier getterConditional() {
		if(evokeConditional != null) return evokeConditional;
		else throw new NullPointerException("No conditional getter set.");
	}
	
	/**
	 * Returns the assigned message call value for <code>Type.MESSAGE</code> trackers
	 * @return the <code>StringSupplier</code> object representing the message value of this <code>DeviceTracker</code>.
	 * @see org.usfirst.frc.team2473.framework.threading.StringSupplier
	 * @throws NullPointerException if <code>dataType</code> is not <code>Type.MESSAGE</code>.
	 * */
	public StringSupplier getterMessage() {
		if(evokeMessage != null) return evokeMessage;
		else throw new NullPointerException("No message getter set");
	}
	
	/**
	 * Allows for the numeric value call to be assigned
	 * <br><br> User lambda expressions for ease like so:
	 * <br><br> Example: setEvokeNumeric(() -&gt; numericalValueMethod()); //the numerical value method will be called by the supplier upon evocation.
	 * @param numeric a <code>DoubleSupplier</code> value representing the numerical value call
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/DoubleSupplier.html"><code>DoubleSupplier</code></a>
	 * */
	public void setEvokeNumeric(DoubleSupplier numeric) {
		evokeNumeric = numeric;
	}
	
	/**
	 * Allows for the conditional value call to be assigned
	 * <br><br> User lambda expressions for ease like so:
	 * <br><br> Example: setEvokeConditional(() -&gt; conditionalValueMethod()); //the conditional value method will be called by the supplier upon evocation.
	 * @param conditional a <code>BooleanSupplier</code> value representing the conditional value call
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/BooleanSupplier.html"><code>BooleanSupplier</code></a>
	 * */
	public void setEvokeConditional(BooleanSupplier conditional) {
		evokeConditional = conditional;
	}
	
	/**
	 * Allows for the message value call to be assigned
	 * <br><br> User lambda expressions for ease like so:
	 * <br><br> Example: setEvokeMessage(() -&gt; messageValueMethod()); //the message value method will be called by the supplier upon evocation.
	 * @param message a <code>StringSupplier</code> value representing the message value call
	 * @see org.usfirst.frc.team2473.framework.threading.StringSupplier
	 * */
	public void setEvokeMessage(StringSupplier message) {
		evokeMessage = message;
	}
}