package sheepSimulator;

import java.util.ArrayList;

public class SimulationData {

	private ArrayList<Sheep> sheep;
	private ArrayList<GrassTile> GTile;
	private String simulID;
	private int year;

	public SimulationData(String simulID) {
		this.simulID = simulID;
		this.sheep = new ArrayList<Sheep>();
		this.GTile = new ArrayList<GrassTile>();
		this.GTile.add(new GrassTile(970, 180));
		this.GTile.add(new GrassTile(1050, 180));
		this.GTile.add(new GrassTile(1130, 180));
		this.GTile.add(new GrassTile(970, 260));
	}

	public SimulationData(String simulID, int year, ArrayList<Sheep> sheep, ArrayList<GrassTile> GTile) {
		this.simulID = simulID;
		this.year = year;
		this.sheep = sheep;
		this.GTile = GTile;
	}

	public ArrayList<Sheep> getSheep() {
		return sheep;
	}

	public ArrayList<GrassTile> getGTile() {
		return GTile;
	}

	public String getSimulID() {
		return simulID;
	}

	public int getYear() {
		return year;
	}

}
