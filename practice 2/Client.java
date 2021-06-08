package practice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
import practice.clientManagement;


public class Client {

	
	public static void main(String[] args)throws ParserConfigurationException, TransformerException {
		
		String host="127.0.0.1";
		int port=9999;
	
		try {
			
			Socket server = new Socket(host,port);
			DataOutputStream out = new DataOutputStream(server.getOutputStream());
			DataInputStream in = new DataInputStream(server.getInputStream());
			
			//Student s= new Student(4,"Hoang",1.0);
			//Student s1= new Student(2,"huy",1.0);
			//Student s2= new Student(3,"hung",1.0);
			
			//out.writeUTF(convertObject2Doc(s));
			//out.writeUTF(convertObject2Doc(s1));
			//out.writeUTF(convertObject2Doc(s2));
			List<Student>students=clientManagement.getStudents();
			out.writeUTF(String.valueOf(students.size()));
			for (Student temp : students) {
				String xml=clientManagement.convertObject2Doc(temp);
				out.writeUTF(xml);
	        }
			//System.out.println(in.readUTF());
			int size=Integer.parseInt(in.readUTF());
			
			System.out.println(size);
			Connection databaseConnect= clientManagement.connectToDatabase("user1","123456789");
			for(int i =0;i<size;i++) {
				
				Student s=clientManagement.getStudent(in.readUTF());
				System.out.println(s.toString());
				
				 String query="INSERT INTO student VALUES(?,?,?)"; 
				  try { PreparedStatement st=
				  databaseConnect.prepareStatement(query); st.setInt(1,s.getId());
				  st.setString(2,s.getName()); st.setDouble(3, s.getGrade());
				  st.executeUpdate(); }
				  catch (Exception e) 
				  { e.printStackTrace(); }
				 
				
			}
			
			out.close();
			in.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
