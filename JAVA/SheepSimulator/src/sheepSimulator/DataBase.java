package sheepSimulator;

import java.util.Vector;

public class DataBase {
	private static DataBase Instance = null;
	private Vector userList;
	private Vector simulList;
	// ���� ������ ��� �߰�
	
	private DataBase() {}
	
	public DataBase getInstance() {
		if(Instance == null)
			Instance = new DataBase();
		return Instance;
	}
	
	public void readFile() {
		
	}
	
	public void saveFile() {
		
	}
	
}
