package org.usfirst.frc.team2473.robot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.usfirst.frc.team2473.framework.Database;

public class GyroLoggingThread extends Thread {
	private DataOutputStream outToClient;

	private static GyroLoggingThread theInstance;

	private boolean isEnabled;

	private GyroLoggingThread() {
		isEnabled = true;

		if (Robot.networkingRunning) {
			try {
				System.out.println(".............................");
				ServerSocket ss = new ServerSocket(5001);
				Socket connection = ss.accept();
				System.out.println("..............................");
				outToClient = new DataOutputStream(connection.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static GyroLoggingThread getInstance() {
		return theInstance;
	}

	static {
		theInstance = new GyroLoggingThread();
	}

	public void run() {
		while (isEnabled) {
			if (Robot.networkingRunning) {
				try {
					System.out.println("..........................................");
					outToClient.writeUTF("Gyro: " + Database.getInstance().getNumeric(RobotMap.GYRO_YAW));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
