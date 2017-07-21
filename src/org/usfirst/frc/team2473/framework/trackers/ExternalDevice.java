package org.usfirst.frc.team2473.framework.trackers;

/**
 * Unique device tracker storing values for devices not connected to the RIO.
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0 
 * */
public class ExternalDevice {
	private String name; //reference name for ExternalDevice
	private double numeric; //numeric value for device
	private boolean conditional; //conditional value for device
	private String state; //device state, stored as a String
		
	/**
	 * Constructor allowing for the assignment of a name for reference
	 * @param name <code>String</code> value representing user assigned reference name
	 */
	public ExternalDevice(String name) {
		this.name = name; //assign name
		numeric = Double.MIN_VALUE; //arbitrary value assignment
		state = ""; //arbitrary value assignment
		conditional = false; //arbitrary value assignment
	}
	
	/**
	 * Get the numeric value stored by this external device
	 * @return <code>double</code> numeric value
	 */
	public double getNumeric() {
		return numeric;
	}
	
	/**
	 * Set the numeric value stored by this external device
	 * @param val <code>double</code> numeric value to be stored
	 */
	public void setNumeric(double val) {
		numeric = val;
	}
	
	/**
	 * Get the conditional value stored by this external device
	 * @return <code>boolean</code> conditional value
	 */	
	public boolean getConditional() {
		return conditional;
	}

	/**
	 * Set the conditional value stored by this external device
	 * @param conditional <code>boolean</code> conditional value to be stored
	 */
	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}
	
	/**
	 * Get the state value stored by this external device
	 * @return <code>String</code> state value
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Set the state value stored by this external device
	 * @param s <code>String</code> state value to be stored
	 */
	public void setState(String s) {
		state = s;
	}
	
	/**
	 * Get the reference name for this external device
	 * @return <code>String</code> value representing the reference name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the reference name for this external device
	 * @param s <code>String</code> value representing the name to use as reference 
	 */
	public void setName(String s) {
		name = s;
	}
}