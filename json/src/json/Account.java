package json;

public class Account {
	private String accID;
	private long balance;
	public Account() {
		// TODO Auto-generated constructor stub
	}
	public Account(String accID, long balance) {
		super();
		this.accID = accID;
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account [accID=" + accID + ", balance=" + balance + "]";
	}

}
