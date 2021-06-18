package json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJsonMap {
		public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
			FileReader fr= new FileReader("C:\\Users\\Hoang\\eclipse-workspace\\json\\src\\json\\customer2.json");
			JSONObject jo = (JSONObject) new JSONParser().parse(fr);
			
			JSONArray nameArr =  (JSONArray) jo.get("names");
			List names= new ArrayList();
			Iterator nameItr = nameArr.iterator();
			while(nameItr.hasNext()) {
				String item= (String) nameItr.next();
				names.add(item);
			}
			
			
			
			long id= (long)jo.get("id");
			long age = (long) jo.get("age");
			
			System.out.println(names);
			System.out.println(id);
			System.out.println(age);
			
			List<Account> accs= new ArrayList();
			JSONArray accounts= (JSONArray) jo.get("accounts");
			for(Object obj: accounts) {
				Map map = (Map) obj;
				String accountID= (String) map.get("accountID");
				long balance = (long) map.get("balance");
				accs.add(new Account(accountID,balance));
				
				System.out.println(accountID);
				System.out.println(balance);
			}
			Customer2 cus = new Customer2(id, names, age, accounts);
			System.out.println(cus);
			
	}

}
