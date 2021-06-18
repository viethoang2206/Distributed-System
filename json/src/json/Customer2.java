package json;

import java.util.ArrayList;
import java.util.List;

public class Customer2 {
	private long id;
	private List<String> names;
	private long age;
	private List<Account> accounts;
	public Customer2(long id,List<String> names, long age, List<Account> accounts) {
		super();
		this.id = id;
		this.names = names;
		this.age = age;
		this.accounts=accounts;
	}
	@Override
	public String toString() {
		return "Customer2 [id=" + id + ", names=" + names + ", age=" + age + ", accounts=" + accounts + "]";
	}

	
}
