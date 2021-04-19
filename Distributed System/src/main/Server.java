package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import Student.Student;

public class Server {
	public static void main(String[] args) {
		int serverPort = 1999;
		ServerSocket serverSocket = null;
		ObjectOutputStream toClient = null;
		ObjectInputStream fromClient = null;
		
		try {
			serverSocket = new ServerSocket(serverPort);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Just connected to " + socket.getRemoteSocketAddress());
				
				toClient = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				fromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				Message msgRequest = (Message) fromClient.readObject();
                                Student student = msgRequest.getStudent();
                                try {
                                    String insertQuery = "INSERT INTO student (name, id, year) VALUES (?, ?, ?) ";
                                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:5042/pe2018", "user1", "123456789");
                                    if(connection != null) {
                                        System.out.println("Connected");
                                    }
                                    
                                    PreparedStatement statement = connection.prepareStatement(insertQuery);
                                    statement.setString(1, student.getName());
                                    statement.setString(2, student.getID());
                                    statement.setInt(3, student.getYear());
                                    statement.executeUpdate();
                                    
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                               
				
                                
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
