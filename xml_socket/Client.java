package xml_socket;

import java.io.DataOutputStream;
import java.io.StringWriter;
import java.net.Socket;

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

import xml.Book;

public class Client {

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
	public static String convertObject2Doc(Employee employee) throws ParserConfigurationException  {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		// create a document object
		Document doc = builder.newDocument();
		Element rootElement=doc.createElement("Employees");
		doc.appendChild(rootElement);
		
		Element empElement=doc.createElement("Employee");
		rootElement.appendChild(empElement);
		
		Element id=doc.createElement("id");
		id.appendChild(doc.createTextNode(employee.getID()));
		
		Element name=doc.createElement("name");
		name.appendChild(doc.createTextNode(employee.getName()));
		
		Element age =doc.createElement("age");
		age.appendChild(doc.createTextNode(String.valueOf(employee.getAge())));
		
		empElement.appendChild(id);
		empElement.appendChild(name);
		empElement.appendChild(age);
		
		String xmlString=convertDoc2XmlString(doc);
		return xmlString;
	}

	public static void main(String[] args)throws ParserConfigurationException, TransformerException {
		
		String host="127.0.0.1";
		int port=9999;
		try {
			Socket server = new Socket(host,port);
			DataOutputStream out = new DataOutputStream(server.getOutputStream());
			
			Employee e= new Employee("15041","Hoang",21);
			out.writeUTF(convertObject2Doc(e));
			
			out.close();
		}catch (Exception e) {
			//e.printStackTrace();
		}

	}

}
