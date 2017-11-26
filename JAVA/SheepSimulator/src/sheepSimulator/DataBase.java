package sheepSimulator;

import java.util.Vector;

public class DataBase {
	private static DataBase Instance = null;
	private Vector<User> userList;
	private Vector simulList;
	// ���� ������ ��� �߰�
	
	private DataBase() {
		userList = new Vector<User>();
	}
	
	public static DataBase getInstance() {
		if(Instance == null)
			Instance = new DataBase();
		return Instance;
	}
	
	public User identUser(String ID, String pw) {
		for(User user : userList) {
			if(user.getID() == ID && user.getPassword() == pw) {
				return user;
			}
		}
		return null;
	}
	
	public void readFile() {
		
	}
	
	public void saveFile() {
		
	}
	
}
