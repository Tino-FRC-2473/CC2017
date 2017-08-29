package org.usfirst.frc.team2473.robot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team2473.framework.Database;

public class GyroLoggingThread extends Thread {
	private DataOutputStream outToClient;

	private static GyroLoggingThread theInstance;

	private boolean isEnabled;

	private GyroLoggingThread() {
		isEnabled = true;
	}

	public static GyroLoggingThread getInstance() {
		return theInstance;
	}

	static {
		theInstance = new GyroLoggingThread();
	}

	public void run() {
		while (isEnabled) {
			try {
				Files.write(Paths.get("/U/gyrolog.txt"), Arrays.asList("" + Database.getInstance().getNumeric(RobotMap.GYRO_YAW)), StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isDead() {
		return !isEnabled;
	}

	public void kill() {
		isEnabled = false;
	}
}
