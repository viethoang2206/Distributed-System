package json;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJson {
	public static void main(String[] args) throws FileNotFoundException {
		JSONObject jo= new JSONObject();
		
		jo.put("id",123);
		
		JSONArray names =new JSONArray();
		names.add("Smith");
		names.add("Jones");
		jo.put("names", names);

		JSONArray addresses =new JSONArray();
		addresses.add("Viet Nam");
		addresses.add("Canada");
		jo.put("addresses", addresses);
		
		String jsonString= jo.toJSONString();
		System.out.println(jsonString);
		
		PrintWriter pw= new PrintWriter("C:\\Users\\Hoang\\eclipse-workspace\\json\\src\\json\\customer.json");
		pw.write(jsonString);
		pw.flush();
		pw.close();
		
	}

}
