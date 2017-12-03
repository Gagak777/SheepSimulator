package sheepSimulator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

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
		if (!isSave) {
			simulList.add(simul);
		}
	}

	public void readUserFile() {
		
	}
	
	public void readSimulationFile() {
		
	}

	public void saveUserFile() {
		try {
			int num = 1;
			File file = new File(MainClass.class.getResource("../res/users.xml").getFile());
			OutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter ops = new OutputStreamWriter(outputStream);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlW = factory.createXMLStreamWriter(ops);

			xmlW.writeStartDocument("MS949", "1.0");
			xmlW.writeStartElement("userList");

			for (User user : this.userList) {

				xmlW.writeStartElement("user" + num);

				xmlW.writeStartElement("ID");
				xmlW.writeCharacters(user.getID());
				xmlW.writeEndElement();

				xmlW.writeStartElement("PW");
				xmlW.writeCharacters(user.getPassword());
				xmlW.writeEndElement();

				xmlW.writeStartElement("NAME");
				xmlW.writeCharacters(user.getName());
				xmlW.writeEndElement();

				xmlW.writeEndElement();

			}
			xmlW.writeEndElement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSimulationFile() {
		try {
			int num;
			File file = new File(MainClass.class.getResource("../res/simulations.xml").getFile());
			OutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter ops = new OutputStreamWriter(outputStream);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlW = factory.createXMLStreamWriter(ops);

			xmlW.writeStartDocument("MS949", "1.0");
			xmlW.writeStartElement("simulationList");

			for (SimulationData simulData : this.simulList) {

				xmlW.writeStartElement(simulData.getSimulID());

				xmlW.writeStartElement("YEAR");
				xmlW.writeCharacters(String.valueOf(simulData.getYear()));
				xmlW.writeEndElement();

				num = 1;
				xmlW.writeStartElement("SHEEPS");
				for (Sheep sheep : simulData.getSheep()) {
					xmlW.writeStartElement("SHEEP" + num);

					xmlW.writeStartElement("appearance");
					xmlW.writeCharacters(sheep.get_appURL());
					xmlW.writeEndElement();
					
					xmlW.writeStartElement("loc_x");
					xmlW.writeCharacters(String.valueOf(sheep.get_x()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("loc_y");
					xmlW.writeCharacters(String.valueOf(sheep.get_y()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("birth");
					xmlW.writeCharacters(String.valueOf(sheep.get_birth()));
					xmlW.writeEndElement();
				
					xmlW.writeStartElement("sex");
					xmlW.writeCharacters(String.valueOf(sheep.get_sex()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("lifeLimit");
					xmlW.writeCharacters(String.valueOf(sheep.get_lifeLimit()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("satiety");
					xmlW.writeCharacters(String.valueOf(sheep.get_satiety()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("stamina");
					xmlW.writeCharacters(String.valueOf(sheep.get_stamina()));
					xmlW.writeEndElement();
					
					xmlW.writeStartElement("state");
					xmlW.writeCharacters(String.valueOf(sheep.get_sheepState()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("isExcute");
					xmlW.writeCharacters(String.valueOf(sheep.get_isExcute()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("count");
					xmlW.writeCharacters(String.valueOf(sheep.get_count()));
					xmlW.writeEndElement();

					xmlW.writeStartElement("vector");
					xmlW.writeCharacters(String.valueOf(sheep.get_vector()));
					xmlW.writeEndElement();
					
					xmlW.writeEndElement();
					num++;
				}
				xmlW.writeEndElement();

				num = 1;
				xmlW.writeStartElement("GRASSES");				
				for(GrassTile grass : simulData.getGTile()) {
					xmlW.writeStartElement("GRASS" + num);
					
					xmlW.writeStartElement("loc_x");
					xmlW.writeCharacters(String.valueOf(grass.get_x()));
					xmlW.writeEndElement();
					
					xmlW.writeStartElement("loc_y");
					xmlW.writeCharacters(String.valueOf(grass.get_y()));
					xmlW.writeEndElement();
					
					xmlW.writeStartElement("grassCap");
					xmlW.writeCharacters(String.valueOf(grass.get_grassCap()));
					xmlW.writeEndElement();
					
					xmlW.writeStartElement("grassImg");
					xmlW.writeCharacters(grass.get_imgURL());
					xmlW.writeEndElement();
					
					xmlW.writeEndElement();
					num++;
				}			
				xmlW.writeEndElement();
				
				xmlW.writeEndElement();

			}
			xmlW.writeEndElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
