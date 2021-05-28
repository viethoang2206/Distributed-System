package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Staff3 {
	public static void main(String[] args) {
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("staff3.xml"));

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			System.out.println("------");
			
			NodeList nl= doc.getElementsByTagName("staff");
			for(int i=0; i<nl.getLength();i++) {
				Node node = nl.item(i);
				Element e= (Element) node;
				String firstname = e.getElementsByTagName("firstname").item(0).getTextContent();
                String lastname = e.getElementsByTagName("lastname").item(0).getTextContent();
                String nickname = e.getElementsByTagName("nickname").item(0).getTextContent();
                String salary = e.getElementsByTagName("salary").item(0).getTextContent();
                String id = e.getAttribute("id");
                String currency = e.getAttribute("curency");
                System.out.println("ID : " + id);
                System.out.println("First Name : " + firstname);
                System.out.println("Last Name : " + lastname);
                System.out.println("NickName : " + nickname);
    			System.out.println("salary and curency  : " + salary + "-" + currency +"\n");
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}