package org.usfirst.frc.team2473.framework.trackers;

public class ExternalDevice {
	private String name;
	private double numeric;
	private boolean conditional;
	private String state;
		
	public ExternalDevice(String name) {
		this.name = name;
		numeric = Double.MIN_VALUE;
		state = "";
		conditional = false;
	}
	
	public double getNumeric() {
		return numeric;
	}
	
	public void setNumeric(double val) {
		numeric = val;
	}
	
	public boolean getConditional() {
		return conditional;
	}
	
	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String s) {
		state = s;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
}