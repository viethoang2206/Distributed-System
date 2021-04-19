package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import Student.Student;

public class Client {
	public static void sendServer(Student student) {
		int serverPort = 1999;
		Socket socket = null;
		ObjectOutputStream toServer = null;
		ObjectInputStream fromServer = null;
		BufferedReader br = null;
		
		try {
			InetAddress serverHost = InetAddress.getByName("localhost");
			System.out.println("Connecting to server on port " + serverPort);
			socket = new Socket(serverHost, serverPort);
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());
			
			toServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			Message msgToSend = new Message(student);
			toServer.writeObject(msgToSend);
			toServer.flush();

			// This will block until the corresponding ObjectOutputStream
			// in the server has written an object and flushed the header
			/*
                        fromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			Message msgFromReply = (Message) fromServer.readObject();
                        */
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}*/ finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
