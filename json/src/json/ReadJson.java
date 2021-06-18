package json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJson {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		FileReader fr= new FileReader("C:\\Users\\Hoang\\eclipse-workspace\\json\\src\\json\\customer.json");
		JSONObject jo = (JSONObject) new JSONParser().parse(fr);
		
		long id = (long) jo.get("id");
		
		System.out.println(id);
		
		JSONArray nameArr =  (JSONArray) jo.get("names");
		Iterator nameItr= nameArr.iterator();
		while(nameItr.hasNext()) {
			String item = (String) nameItr.next();
			System.out.println(item);
		}
		JSONArray addressesArr = (JSONArray) jo.get("addresses");
		Iterator addIter = addressesArr.iterator();
		while(addIter.hasNext()) {
			String addresses= (String) addIter.next();
			System.out.println(addresses);
		}
	}

}
