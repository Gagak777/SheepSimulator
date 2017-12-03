package sheepSimulator;

public class User {
	private String ID;
	private String password;
	private String userName;

	public User(String ID, String password, String userName) {
		this.ID = ID;
		this.password = password;
		this.userName = userName;
	}

	public String getID() {
		return this.ID;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.userName;
	}
}
