package json;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJsonMap {

	public static void main(String[] args) throws FileNotFoundException {
		JSONObject jo = new JSONObject();
		List names = new ArrayList();
		
		names.add("Smith");
		names.add("Jones");
		
		jo.put("id", 15041);
		jo.put("age",21);
		jo.put("names", names);
		
		//JSONArray accounts= new JSONArray();
		List accounts = new ArrayList();
		Map map = new LinkedHashMap(2);
		map.put("accountID","123");
		map.put("balance",567);
		accounts.add(map);
		
		map.put("accountID","123");
		map.put("balance",567);
		accounts.add(map);
		
		
		jo.put("accounts", accounts);
		
		String jsonString = jo.toJSONString();
		System.out.println(jsonString);
		
		PrintWriter pr = new PrintWriter("C:\\Users\\Hoang\\eclipse-workspace\\json\\src\\json\\customer2.json");
		pr.write(jsonString);
		pr.flush();
		pr.close();
		
		
		
	}

}
