package org.usfirst.frc.team2473.framework.diagnostic;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.usfirst.frc.team2473.framework.trackers.*;
import org.usfirst.frc.team2473.robot.Robot;
import org.usfirst.frc.team2473.robot.RobotMap;
import org.usfirst.frc.team2473.framework.Database;
import org.usfirst.frc.team2473.framework.components.Trackers;
import org.usfirst.frc.team2473.framework.diagnostic.diagnosers.Diagnoser;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DiagnosticThread extends Thread{
	private ArrayList<Diagnoser> diagnosers = new ArrayList<Diagnoser>();
	private HashMap<String, String> errors = new HashMap<String, String>();
	private HashMap<String, Subsystem> systems = new HashMap<String, Subsystem>();
	private ArrayList<EncoderTracker> encodertrackers = new ArrayList<EncoderTracker>();

	public long initialTime;
	
	private DataOutputStream outToClient;

	private static DiagnosticThread theInstance;

	static {
		theInstance = new DiagnosticThread();
	}

	public static DiagnosticThread getInstance() {
		return theInstance;
	}

	public void addError(String key, Subsystem sys, String error){
		errors.put(key, error);
		systems.put(key,sys);
	}

	public DiagnosticThread(){
		initialTime = System.currentTimeMillis();
		for(DeviceTracker tracker : Trackers.getInstance().getTrackers()){
			if(tracker.getClass().getName().equals("EncoderTracker") && (tracker.getPort() == RobotMap.FRONT_LEFT || tracker.getPort() == RobotMap.FRONT_RIGHT ||  tracker.getPort() == RobotMap.BACK_LEFT ||  tracker.getPort() == RobotMap.BACK_RIGHT)){
				encodertrackers.add((EncoderTracker) tracker);
			}
		}
		if(Robot.networkingRunning){
			try{
				ServerSocket ss = new ServerSocket(5800);
				Socket connection = ss.accept();
				outToClient = new DataOutputStream(connection.getOutputStream());
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void addToList(Diagnoser diagnoser){
		diagnosers.add(diagnoser);
	}

	public void run(){
		while(isAlive()){
			for(Diagnoser diagnoser:diagnosers){
				diagnoser.RunSimultaneousTest();
			}
			if(Robot.networkingRunning){
				try {
					outToClient.writeUTF("time: " + getTime());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				for(EncoderTracker tracker : encodertrackers){
					try {
						if(tracker.getPort() == RobotMap.FRONT_RIGHT){
							outToClient.writeUTF("fr: " + Database.getInstance().getNumeric(tracker.getKey()) + ", ");
						}else if(tracker.getPort() == RobotMap.FRONT_LEFT){
							outToClient.writeUTF("fl: " + Database.getInstance().getNumeric(tracker.getKey()) + ", ");
						}else if(tracker.getPort() == RobotMap.BACK_LEFT){
							outToClient.writeUTF("bl: " + Database.getInstance().getNumeric(tracker.getKey()) + ", ");
						}else if(tracker.getPort() == RobotMap.BACK_RIGHT){
							outToClient.writeUTF("br: " + Database.getInstance().getNumeric(tracker.getKey()) + ", ");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			errors();
		}
	}

	private void errors(){
		String Key = "";
		Scanner scanner = new Scanner(System.in);
		for(String error : errors.values()){
			for(String key : errors.keySet()){
				if(errors.get(key) == error){
					Key = key;
				}
			}
			if(scanner.nextLine().equals(error)){
				for(Subsystem sys : systems.values()){
					for(String key : systems.keySet()){
						if((systems.get(key) == sys) && (key == Key)){
							sys.getCurrentCommand().cancel();
						}
					}
				}
			}
		}
	}

	public long getTime(){
		return System.currentTimeMillis() - initialTime;
	}
}
