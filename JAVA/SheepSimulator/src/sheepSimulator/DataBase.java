package sheepSimulator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataBase {
	private static DataBase Instance = null;
	private ArrayList<User> userList;
	private ArrayList<SimulationData> simulList;
	
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
			if (user.getID().equals(ID) && user.getPassword().equals(pw)) {
				return user;
			}
		}
		return null;
	}

	public SimulationData getSimulator(User loginedUser) {
		SimulationData nowSimul = null;

		for (SimulationData simul : this.simulList) { // 체크하자
			if (simul.getSimulID().equals(loginedUser.getID())) {
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
		try {
			String ID;
			String PW;
			String name;

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = f.newDocumentBuilder();

			Document xmlDoc = null;
			xmlDoc = parser.parse("./src/res/users.xml");

			Element root = xmlDoc.getDocumentElement();

			for (int i = 1; i <= Integer.parseInt(root.getAttribute("userNum")); i++) {
				NodeList node = root.getElementsByTagName("user" + i);
				Node userNode = node.item(0);

				NodeList userInfo = userNode.getChildNodes();
				ID = ((Element) userInfo.item(0)).getTextContent();
				PW = ((Element) userInfo.item(1)).getTextContent();
				name = ((Element) userInfo.item(2)).getTextContent();

				this.userList.add(new User(ID, PW, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readSimulationFile() {
		try {
			String simulID;
			int year;
			ArrayList<Sheep> sheep = new ArrayList<Sheep>();
			ArrayList<GrassTile> GTile = new ArrayList<GrassTile>();

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = f.newDocumentBuilder();

			Document xmlDoc = null;
			xmlDoc = parser.parse("./src/res/simulations.xml");

			Element root = xmlDoc.getDocumentElement();

			for (int i = 1; i <= Integer.parseInt(root.getAttribute("simulNum")); i++) {
				NodeList node = root.getElementsByTagName("SIMUL" + i);
				Node userNode = node.item(0);

				NodeList userInfo = userNode.getChildNodes();
				simulID = ((Element) userInfo.item(0)).getTextContent();
				year = Integer.parseInt(((Element) userInfo.item(1)).getTextContent());

				NodeList sheepList = userInfo.item(2).getChildNodes();

				// 양 개수 추가
				NodeList sheepInfo = null;
				for (int j = 0; j < Integer.parseInt(((Element) sheepList).getAttribute("SheepNum")); j++) {
					sheepInfo = sheepList.item(i).getChildNodes();

					sheep.add(new Sheep(((Element) sheepInfo.item(0)).getTextContent(),
							Integer.parseInt(((Element) sheepInfo.item(1)).getTextContent()),
							Integer.parseInt(((Element) sheepInfo.item(2)).getTextContent()),
							Integer.parseInt(((Element) sheepInfo.item(3)).getTextContent()),
							Boolean.parseBoolean((((Element) sheepInfo.item(4)).getTextContent())),
							Integer.parseInt(((Element) sheepInfo.item(5)).getTextContent()),
							Integer.parseInt(((Element) sheepInfo.item(6)).getTextContent()),
							Integer.parseInt(((Element) sheepInfo.item(7)).getTextContent()),
							Integer.parseInt(((Element) sheepInfo.item(8)).getTextContent()),
							Boolean.parseBoolean((((Element) sheepInfo.item(9)).getTextContent())),
							Integer.parseInt(((Element) sheepInfo.item(10)).getTextContent()),
							Integer.parseInt(((Element) sheepInfo.item(11)).getTextContent())));

				}
				NodeList grassList = userInfo.item(3).getChildNodes();
				NodeList grassInfo = null;
				for (int j = 0; j < Integer.parseInt(((Element) grassList).getAttribute("GrassNum")); j++) {
					grassInfo = grassList.item(i).getChildNodes();
					GTile.add(new GrassTile(Integer.parseInt(((Element) grassInfo.item(0)).getTextContent()),
							Integer.parseInt(((Element) grassInfo.item(1)).getTextContent()),
							Integer.parseInt(((Element) grassInfo.item(2)).getTextContent())));

					this.simulList.add(new SimulationData(simulID, year, sheep, GTile));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveUserFile() {
		try {
			int num = 1;
			File file = new File("./src/res/users.xml");
			OutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter ops = new OutputStreamWriter(outputStream);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlW = factory.createXMLStreamWriter(ops);

			xmlW.writeStartDocument("MS949", "1.0");
			xmlW.writeStartElement("userList");
			xmlW.writeAttribute("userNum", String.valueOf(this.userList.size()));

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
			xmlW.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSimulationFile() {
		try {
			int num, num2 = 1;
			File file = new File(("./src/res/simulations.xml"));
			OutputStream outputStream = new FileOutputStream(file);
			OutputStreamWriter ops = new OutputStreamWriter(outputStream);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlW = factory.createXMLStreamWriter(ops);

			xmlW.writeStartDocument("MS949", "1.0");
			xmlW.writeStartElement("simulationList");
			xmlW.writeAttribute("simulNum", String.valueOf(this.userList.size()));

			for (SimulationData simulData : this.simulList) {

				xmlW.writeStartElement("SIMUL" + num2);

				xmlW.writeStartElement("SIMUL_ID");
				xmlW.writeCharacters(simulData.getSimulID());
				xmlW.writeEndElement();

				xmlW.writeStartElement("YEAR");
				xmlW.writeCharacters(String.valueOf(simulData.getYear()));
				xmlW.writeEndElement();

				num = 1;
				xmlW.writeStartElement("SHEEPS");
				xmlW.writeAttribute("SheepNum", String.valueOf(simulData.getSheep().size()));
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
				xmlW.writeAttribute("GrassNum", String.valueOf(simulData.getGTile().size()));
				for (GrassTile grass : simulData.getGTile()) {
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

					xmlW.writeEndElement();
					num++;
				}
				xmlW.writeEndElement();

				xmlW.writeEndElement();

				num2++;
			}
			xmlW.writeEndElement();
			xmlW.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
