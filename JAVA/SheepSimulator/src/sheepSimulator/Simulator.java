package sheepSimulator;

import java.util.Vector;

public class Simulator {

	private Vector<Sheep> sheep;
	private Vector<GrassTile> GTile;
	private String simulID;
	private int year;

	public Simulator(String simulID) {
		this.simulID = simulID;
		this.year = 0;
	}

	public Simulator(String simulID, Vector<Sheep> sheep, Vector<GrassTile> GTile, int year) {
		this.sheep = sheep;
		this.GTile = GTile;
		this.simulID = simulID;
		this.year = year;
	}

	public void excute() {
		MainClass.simulateYear = this.year;

		// 화면을 시뮬레이션 화면으로 전환
		// 메뉴 컴포넌트들 추가

		for (Sheep shp : this.sheep) {
			shp.start();
		}
		for (GrassTile gTile : this.GTile) {
			gTile.start();
		}
	}

	public void addSheep() {
		//양 설정 컴포넌트 띄울것
	}

	public void close() {
		DataBase.getInstance().saveSimul(this);
	}

	public String getSimulID() {
		return this.simulID;
	}
}
