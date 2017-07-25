package org.usfirst.frc.team2473.framework;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.usfirst.frc.team2473.robot.RobotMap;

/**
 * Networking class for communication between Jetson and RIO and possibly others
 * @author Deep Sethi
 * @author Harmony He
 * @version 1.0
 */
public class Networking extends Thread{
	private volatile boolean running; //stores whether the process is running or not
	private Scanner in; //Scanner for receive() values
	private String host; //Host address
	private int port; //Port number
	private Socket s; //Socket
	private Scanner sc1; //Scanner for client input
	private Scanner sc2; //Scanner for server input
	/**
	 * Default constructor
	 *  @throws UnknownHostException if IP address of host could not be determined
	 * @throws IOException if some sort of I/O exception has occurred 
	 * */
	public Networking() throws UnknownHostException, IOException {
		in = new Scanner("");
		host = "";
		port = 8888;
		running = true;
		s = new Socket(host, port); //Create socket connecting to the specified host and port
		sc1= new Scanner(System.in); //Scanner for client input
		sc2 = new Scanner(s.getInputStream()); //Scanner for server input
		Database.getInstance().putNumeric(RobotMap.PEG_DISTANCE);
		Database.getInstance().putNumeric(RobotMap.PEG_ANGLE);
	}

	/**
	 * The method of execution for this Thread, just like all Threads
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#run--"><code>Thread</code></a>
	 * 
	 */
	public void run(){
		while(running){
			switch(sc2.nextLine()) { //does something based on server input
			case RobotMap.FUNCTION_TRIGGER:
				try {
					runCV(host, port);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
					break;
			}
		}
	}

	/**
	 * Kills the DeviceReader process
	 */
	public void kill() {
		running = false; //setting running to false will stop the loop in run()			
	}
	
	/**
	 * Returns the termination status of the DeviceReader process.
	 * @return a <code>boolean</code> value representing the whether or not the DeviceReader process is terminated or not.
	 */
	public boolean isDead() {
		return !running; //returning the opposite of running will return whether or not it is dead or "not running"
	}
	
	/**
	 * Calls and reads from <code>receive</code> for the <code>double</code> values representing distance and angle
	 * which are stored into <code>Database</code> 
	 * @param host a <code>String</code> value representing the specified remote host
	 * @param port a <code>int</code> value representing the specified port number
 	 * @return true to confirm code has completed execution
 	 * @throws UnknownHostException if IP address of host could not be determined
	 * @throws IOException if some sort of I/O exception has occurred 
	 * */
	public boolean runCV(String host, int port) throws UnknownHostException, IOException{
		in = new Scanner(receive(host, port)); //Create Scanner to read output from receive method
		Database.getInstance().setNumeric(RobotMap.PEG_DISTANCE, in.nextDouble()); //Sets the next double under PEG_DISTANCE
		Database.getInstance().setNumeric(RobotMap.PEG_ANGLE, in.nextDouble()); //Sets the next double under PEG_ANGLE
		in.close();//Closes the Scanner
		return true;
	}
	
	/**
	 * Returns the data passed in from the specified host and port
	 * @param host a <code>String</code> value representing the specified remote host
	 * @param port a <code>int</code> value representing the specified port number
	 * @return final output from host before the socket is closed
	 * @throws UnknownHostException if IP address of host could not be determined
	 * @throws IOException if some sort of I/O exception has occurred 
	 * */
	public String receive(String host, int port) throws UnknownHostException, IOException{
		String cli, //Client input 
			serv; //Server input
		String str; //stores previous line, so that this method will return the previous line from when STOP_TRIGGER is received 
		
		cli = sc1.nextLine(); //updates cli to the next line of client input
		serv = sc2.nextLine(); //updates serv to the next line of server input
		
		PrintStream p = new PrintStream(s.getOutputStream()); //Create a PrintStream to get output from the Socket
		
		while(!(str = sc2.nextLine().trim()).equals(Database.getInstance().getMessage(RobotMap.STOP_TRIGGER))){ //while the host does not send the stop trigger
			serv = str; //sets serv to str
			System.out.println(serv);  
			p.println(cli);  
		}
		sc1.close(); //Closes the scanners
		s.close(); //Closes the socket
		return serv; //returns the previous line from when STOP_TRIGGER is received
	}
	
	private static Networking theInstance; //instance of the class
	
	/**
	 * Creates an instance of the class
	 * */
	static {
		try {
			theInstance = new Networking();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Returns the instance of the Networking class
	 * @return theInstance value as a <code>Networking</code>
	 * */
	public static Networking getInstance() {
		return theInstance;
	}
}