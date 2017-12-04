package sheepSimulator;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Simulator extends Thread {

	private static Simulator Instance = null;

	private ArrayList<Sheep> sheep;
	private ArrayList<GrassTile> GTile;
	private String simulID;
	private int year;
	private boolean flag;

	private long before_time;
	private long now_time;

	private Simulator() {
		this.sheep = new ArrayList<Sheep>();
		this.GTile = new ArrayList<GrassTile>();
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

	public void run() {
		ScreenGraphic.getInstance().isSimulRun = true;
		MainClass.simulateYear = this.year;
		this.flag = true;
		ScreenGraphic.getInstance()
				.setBackGround(new ImageIcon(MainClass.class.getResource("../res/image/map01.png")).getImage());
		
		this.sheep.add(SheepFactory.getInstance().makeSheep());/////////////////////////////////////test
		this.sheep.add(SheepFactory.getInstance().makeSheep());/////////////////////////////////////test
		this.sheep.add(SheepFactory.getInstance().makeSheep());/////////////////////////////////////test
		this.sheep.add(SheepFactory.getInstance().makeSheep());/////////////////////////////////////test
		
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

			try {
					this.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (this.now_time - this.before_time > MainClass.SECOND * 10 / MainClass.simulationSpeed) {
				MainClass.simulateYear++;
				this.before_time = this.now_time;
			}
		}
	}

	public void addSheep() {
		this.sheep.add(SheepFactory.getInstance().makeSheep());
	}

	public void close() {
		DataBase.getInstance()
				.saveSimul(new SimulationData(this.simulID, MainClass.simulateYear, this.sheep, this.GTile));
		this.flag = false;
		ScreenGraphic.getInstance().isSimulRun = false;
		
		this.interrupt();
	}

	public GrassTile nearGrass(int x, int y) {
		Point sheepLoc = new Point(x, y);
		GrassTile curGrass = null;
		double distance = -1;

		for (GrassTile grass : this.GTile) {
			Point temp = new Point(grass.get_x(), grass.get_y());

			if (grass.get_grassCap() > 0 && (temp.distance(sheepLoc) < distance || distance < 0)) {
				distance = temp.distance(sheepLoc);
				curGrass = grass;
			}
		}
		return curGrass;
	}

	public String getSimulID() {
		return this.simulID;
	}

	public ArrayList<Sheep> getSheepList() {
		return this.sheep;
	}

	public ArrayList<GrassTile> getGrassList() {
		return this.GTile;
	}

	public boolean isGrass(int x, int y) {
		for (GrassTile grass : this.GTile) {
			if (grass.isGrass(x, y))
				return true;
		}
		return false;
	}
}
