package sheepSimulator;

import java.util.Vector;

public class DataBase {
	private static DataBase Instance = null;
	private Vector<User> userList;
	private Vector<Simulator> simulList;
	// 이하 설정값 등등 추가
	private DataBase() {
		userList = new Vector<User>();
		simulList = new Vector<Simulator>();
	}

	public static DataBase getInstance() {
		if (Instance == null)
			Instance = new DataBase();
		return Instance;
	}

	public User identUser(String ID, String pw) {
		for (User user : userList) {
			if (user.getID() == ID && user.getPassword() == pw) {
				return user;
			}
		}
		return null;
	}

	public Simulator getSimulator(User loginedUser) {
		Simulator nowSimul = null;

		for (Simulator simul : this.simulList) {
			if (simul.getSimulID() == loginedUser.getID()) {
				nowSimul = simul;
				break;
			}
		}
		if (nowSimul == null) {
			nowSimul = new Simulator(loginedUser.getID());
		}

		return nowSimul;
	}

	public void saveSimul(Simulator simul) {
		boolean isSave = false;
		for (int i = 0; i < this.simulList.size(); i++) {
			if (simulList.get(i).getSimulID() == simul.getSimulID()) {
				simulList.remove(i);
				simulList.insertElementAt(simul, i);
				isSave = true;
			}
		}
		if(!isSave) {
			simulList.add(simul);
		}
	}

	public void readFile() {

	}

	public void saveFile() {

	}

}
