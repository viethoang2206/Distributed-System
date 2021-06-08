package practice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



import java.util.ArrayList;
import java.util.List;
import practice.serverManagement;


public class Server {

	
	public static void main(String[] args) {
		int port = 9999;
		
		
		ServerSocket server=null;
		try {
			server = new ServerSocket(port);
			System.out.println("Server is running on "+port);
			Socket client =server.accept();
			
			DataInputStream in = new DataInputStream(client.getInputStream());
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			
			List<Student>students=serverManagement.getStudents();
			int size=Integer.parseInt(in.readUTF());
			System.out.println(size);
			Connection databaseConnect= serverManagement.connectToDatabase("user1","123456789");
			for(int i =0;i<size;i++) {
				
				Student s=serverManagement.getStudent(in.readUTF());
				System.out.println(s.toString());
				
				 String query="INSERT INTO students VALUES(?,?,?)"; 
				  try { PreparedStatement st=
				  databaseConnect.prepareStatement(query); st.setInt(1,s.getId());
				  st.setString(2,s.getName()); st.setDouble(3, s.getGrade());
				  st.executeUpdate(); 
				  }catch (Exception e) { e.printStackTrace(); }
				 
				  
			}
			
			out.writeUTF(String.valueOf(students.size()));
			for (Student temp : students) {
				String xml=serverManagement.convertObject2Doc(temp);
				System.out.println(temp.toString());
				out.writeUTF(xml);
	        }
			in.close();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				server.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
