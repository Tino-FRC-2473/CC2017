package org.usfirst.frc.team2473.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {
	public static void main(String[] args) {
		int portNumber = 8080;

		File file;
		FileWriter writer;

		try (Socket socket = new Socket("RoboRIO-2473-FRC.local", portNumber)) {
			try (InputStream input = socket.getInputStream()) {
				try (Scanner in = new Scanner(input)) {
					System.out.println("Connected to robot!");

					file = new File("./gyrodata.txt");
					writer = new FileWriter(file);

					while (in.hasNextLine()) {
						writer.write(in.nextLine() + "\n");
					}

					writer.close();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
