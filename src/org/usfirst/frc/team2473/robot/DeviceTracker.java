package org.usfirst.frc.team2473.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class DeviceTracker {
	private String key;
	private Type dataType;
	private int port;
	private DoubleSupplier evokeNumeric;
	private BooleanSupplier evokeConditional;
	private StringSupplier evokeMessage;
	
	public DeviceTracker(String key, Type type, int port) {
		this.key = key;
		dataType = type;
		this.port = port;
	}
	
	public enum Type {
		NUMERIC, CONDITIONAL, MESSAGE
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String s) {
		key = s;
	}
	
	public Type getType() {
		return dataType;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public DoubleSupplier getterNumeric() {
		if(evokeNumeric != null) return evokeNumeric;
		else throw new NullPointerException("No numeric getter set");
	}
	
	public BooleanSupplier getterConditional() {
		if(evokeConditional != null) return evokeConditional;
		else throw new NullPointerException("No conditional getter set.");
	}
	
	public StringSupplier getterMessage() {
		if(evokeMessage != null) return evokeMessage;
		else throw new NullPointerException("No message getter set");
	}
	
	public void setEvokeNumeric(double numeric) {
		evokeNumeric = () -> numeric;
	}
	
	public void setEvokeConditional(boolean conditional) {
		evokeConditional = () -> conditional;
	}
	
	public void setEvokeMessage(String message) {
		evokeMessage = () -> message;
	}
}