package practice;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class serverManagement {
	public static String convertDoc2XmlString(Document doc) {
		String output = "";
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	
	
	public static Connection connectToDatabase(String user, String password) {
		String databaseUrl="jdbc:mysql://localhost:5042/database2?user=" + user + "&password=" +password;
		Connection conn= null;
		try {
			conn= DriverManager.getConnection(databaseUrl);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static String convertObject2Doc(Student s) throws ParserConfigurationException  {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		// create a document object
		Document doc = builder.newDocument();
		Element rootElement=doc.createElement("Students");
		doc.appendChild(rootElement);
		
		Element studentElement=doc.createElement("Student");
		rootElement.appendChild(studentElement);
		
		Element id=doc.createElement("id");
		id.appendChild(doc.createTextNode(String.valueOf(s.getId())));
		
		Element name=doc.createElement("name");
		name.appendChild(doc.createTextNode(s.getName()));
		
		Element grade =doc.createElement("grade");
		grade.appendChild(doc.createTextNode(String.valueOf(s.getGrade())));
		
		studentElement.appendChild(id);
		studentElement.appendChild(name);
		studentElement.appendChild(grade);
		
		String xmlString=convertDoc2XmlString(doc);
		return xmlString;
	}
	public static Student xmlToObject(String xml) {
		Document doc = convertXmlString2Document(xml);
		Element rootElement = (Element) doc.getDocumentElement();
        NodeList employees = rootElement.getChildNodes();
        Student s=null;
        for(int i=0;i<employees.getLength();i++) {
        	Element employeeElement= (Element) employees.item(i);
	        int id=Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
			String name=employeeElement.getElementsByTagName("name").item(0).getTextContent();
			double grade=Double.parseDouble(employeeElement.getElementsByTagName("grade").item(0).getTextContent());
			s = new Student(id,name,grade);
			
        }     
        
		return s;
		
	}
	public static List<Student> getStudents() {
		List<Student> students = new ArrayList<Student>();
		Connection databaseConnect = connectToDatabase("user1","123456789");
		Statement st;
		String query="SELECT * FROM students";
		try {
			st = databaseConnect.createStatement();
            ResultSet rs = st.executeQuery(query);
			

			while (rs.next()) {
				students.add(new Student(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return students;
	}
	private static Document convertXmlString2Document(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Student getStudent(String xmlStr) {
		Document doc = convertXmlString2Document(xmlStr);
		int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
		
		String name = doc.getElementsByTagName("name").item(0).getTextContent();
	
		double grade = Double.parseDouble(doc.getElementsByTagName("grade").item(0).getTextContent());
		
		Student student = new Student(id,name,grade);
		return student;
	}

}
