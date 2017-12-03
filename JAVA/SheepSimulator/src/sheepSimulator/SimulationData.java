package sheepSimulator;

import java.util.ArrayList;

public class SimulationData {
	
	private ArrayList<Sheep> sheep;
	private ArrayList<GrassTile> GTile;
	private String simulID;
	private int year;
	
	public SimulationData(String simulID) {
		this.simulID = simulID;
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
