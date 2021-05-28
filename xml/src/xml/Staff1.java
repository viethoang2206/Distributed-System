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

public class Staff1 {
	public static void main(String[] args) {
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// parse XML file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("staff1.xml"));

			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
			System.out.println("------");

			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			String salary = doc.getElementsByTagName("salary").item(0).getTextContent();
			NodeList nl1= doc.getElementsByTagName("name");
			Element e1= (Element) nl1.item(0);
			String attr1= e1.getAttribute("curency");
			System.out.println("Name : " + name);
			System.out.println("salarye : " + salary);
			System.out.println("Attribute : " + attr1);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}