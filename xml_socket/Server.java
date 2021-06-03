package xml_socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Server {

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
	
	public static Employee xmlToObject(String xml) {
		Document doc = convertXmlString2Document(xml);
		Element rootElement = (Element) doc.getDocumentElement();
        NodeList employees = rootElement.getChildNodes();
       
        Employee e=null;
        for(int i=0;i<employees.getLength();i++) {
        	Element employeeElement= (Element) employees.item(i);
	        String id=employeeElement.getElementsByTagName("id").item(0).getTextContent();
			String name=employeeElement.getElementsByTagName("name").item(0).getTextContent();
			int age=Integer.parseInt(employeeElement.getElementsByTagName("age").item(0).getTextContent());
			e = new Employee(id,name,age);
        }     
        
		return e;
		
	}
	public static void main(String[] args) {
		int port = 9999;
		ServerSocket server=null;
		try {
			server = new ServerSocket(port);
			System.out.println("Server is running on "+port);
			Socket client =server.accept();
			
			DataInputStream in = new DataInputStream(client.getInputStream());
			Employee e = xmlToObject(in.readUTF());
			System.out.println(e);
			in.close();
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
