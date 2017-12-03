package sheepSimulator;

import java.util.ArrayList;

public class DataBase {
	private static DataBase Instance = null;
	private ArrayList<User> userList;
	private ArrayList<SimulationData> simulList;
	// 이하 설정값 등등 추가
	private DataBase() {
		userList = new ArrayList<User>();
		simulList = new ArrayList<SimulationData>();
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

	public SimulationData getSimulator(User loginedUser) {
		SimulationData nowSimul = null;

		for (SimulationData simul : this.simulList) {
			if (simul.getSimulID() == loginedUser.getID()) {
				nowSimul = simul;
				break;
			}
		}
		if (nowSimul == null) {
			nowSimul = new SimulationData(loginedUser.getID());
		}

		return nowSimul;
	}

	public void saveSimul(SimulationData simul) {
		boolean isSave = false;
		for (int i = 0; i < this.simulList.size(); i++) {
			if (simulList.get(i).getSimulID() == simul.getSimulID()) {
				simulList.remove(i);
				simulList.add(i, simul);
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
