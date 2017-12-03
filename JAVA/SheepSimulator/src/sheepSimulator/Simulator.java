package sheepSimulator;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Simulator {

	private static Simulator Instance = null;

	private ArrayList<Sheep> sheep;
	private ArrayList<GrassTile> GTile;
	private String simulID;
	private int year;
	private boolean flag;

	private long before_time;
	private long now_time;

	private Simulator() {
	}

	public static Simulator getInstance() {
		if (Instance == null)
			Instance = new Simulator();
		return Instance;
	}

	public void setInfo(SimulationData simulData) {
		this.simulID = simulData.getSimulID();
		this.year = simulData.getYear();
		this.sheep = simulData.getSheep();
		this.GTile = simulData.getGTile();
	}

	public void excute() {
		ScreenGraphic.getInstance().isSimulRun = true;
		MainClass.simulateYear = this.year;
		this.flag = true;

		// 화면을 시뮬레이션 화면으로 전환
		// 메뉴 컴포넌트들 추가

		for (Sheep shp : this.sheep) {
			shp.start();
		}
		for (GrassTile gTile : this.GTile) {
			gTile.start();
		}

		this.before_time = System.nanoTime();
		while (this.flag) {
			this.now_time = System.nanoTime();

			if (this.now_time - this.before_time > MainClass.SECOND * 10) {
				MainClass.simulateYear++;
				this.before_time = this.now_time;
			}
		}
	}

	public void addSheep() {
		// 양 설정 컴포넌트 띄울것
	}

	public void close() {
		DataBase.getInstance()
				.saveSimul(new SimulationData(this.simulID, MainClass.simulateYear, this.sheep, this.GTile));
		this.flag = false;
		ScreenGraphic.getInstance().isSimulRun = false;
	}

	public String getSimulID() {
		return this.simulID;
	}

	public ArrayList<Sheep> getSheepList() {
		return this.sheep;
	}
}
