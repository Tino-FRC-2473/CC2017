//package org.usfirst.frc.team2473.framework;
//
//import java.io.IOException;
//import java.io.PrintStream;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Scanner;
//
//import org.usfirst.frc.team2473.robot.RobotMap;
//
///**
// * Networking class for communication between Jetson and RIO and other stuff????
// * @author Deep Sethi
// * @author Harmony He
// * @version 1.0
// */
//public class Networking {
//	
//	private static Networking theInstance; //instance of the class
//
//	/**
//	 * Creates an instance of the class
//	 * */
//	static {
//		theInstance = new Networking();
//	}	
//	
//	/**
//	 * Default constructor
//	 * */
//	private Networking() {
//	
//	}
//	
//	/**
//	 * Returns the instance of the Networking class
//	 * @return theInstance value as a <code>Networking</code>
//	 * */
//	public static Networking getInstance() {
//		return theInstance;
//	}
//	
//	/**
//	 * Calls and reads from <code>receive</code> for the <code>double</code> values representing distance and angle
//	 * which are stored into <code>Database</code> 
//	 * @param host a <code>String</code> value representing the specified remote host
//	 * @param port a <code>int</code> value representing the specified port number
// 	 * @return true to confirm code has completed execution
// 	 * @throws UnknownHostException if IP address of host could not be determined
//	 * @throws IOException if some sort of I/O exception has occurred 
//	 * */
//	public Boolean runCV(String host, int port) throws UnknownHostException, IOException{
//		Scanner s = new Scanner(receive(host, port)); //Create Scanner to read output from receive method
//		Database.getInstance().setNumeric(RobotMap.PEG_DISTANCE, s.nextDouble()); //Sets the next double under PEG_DISTANCE
//		Database.getInstance().setNumeric(RobotMap.PEG_ANGLE, s.nextDouble()); //Sets the next double under PEG_ANGLE
//		s.close();//Closes the Scanner
//		return true;
//	}
//	
//	/**
//	 * Returns the data passed in from the specified host and port
//	 * @param host a <code>String</code> value representing the specified remote host
//	 * @param port a <code>int</code> value representing the specified port number
//	 * @return final output from host before the socket is closed
//	 * @throws UnknownHostException if IP address of host could not be determined
//	 * @throws IOException if some sort of I/O exception has occurred 
//	 * */
//	public String receive(String host, int port) throws UnknownHostException, IOException{
//		String cli, //Client input 
//			serv; //Server input
//		Scanner sc1 = new Scanner(System.in); //Scanner for client's input
//		Socket s = new Socket(host, port); //Create socket connecting to the specified host and port
//		Scanner sc2 = new Scanner(s.getInputStream()); //Scanner for server's input
//		
//		serv = sc2.nextLine(); 
//		System.out.println(serv); 
//		while(!(cli = sc1.nextLine()).equals(Database.getInstance().getMessage(RobotMap.FUNCTION_TRIGGER))){ //Updates client and while client does not type in function trigger 
//			PrintStream p = new PrintStream(s.getOutputStream()); //Create a PrintStream to get output from the Socket
//			p.println(cli);  
//			serv = sc2.nextLine();
//			System.out.println(serv);
//		}
//		sc1.close(); //Closes the scanners
//		sc2.close(); 
//		s.close(); //Closes the socket
//		return serv;
//	}
//	
//}