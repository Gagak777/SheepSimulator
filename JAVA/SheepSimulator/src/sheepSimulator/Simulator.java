package sheepSimulator;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Simulator implements Runnable {

	private static Simulator Instance = null;

	private SimulationExitButton simulationExitButton;
	private SlowButton slowButton;
	private FastButton fastButton;
	private AddSheepButton addButton;

	private ArrayList<Sheep> sheep;
	private ArrayList<GrassTile> GTile;
	private String simulID;
	private int year;
	private boolean flag;

	private long before_time;
	private long now_time;

	private Simulator() {

		this.simulationExitButton = new SimulationExitButton();
		ScreenGraphic.getInstance().add(simulationExitButton);
		this.slowButton = new SlowButton();
		ScreenGraphic.getInstance().add(slowButton);
		this.fastButton = new FastButton();
		ScreenGraphic.getInstance().add(fastButton);
		this.addButton = new AddSheepButton();
		ScreenGraphic.getInstance().add(this.addButton);

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
	
	public void init() {
		this.slowButton.setVisible(true);
		this.fastButton.setVisible(true);
		this.simulationExitButton.setVisible(true);
		this.addButton.setVisible(true);
		
		ScreenGraphic.getInstance().isSimulRun = true;
		MainClass.simulateYear = this.year;
		this.flag = true;
		ScreenGraphic.getInstance()
				.setBackGround(new ImageIcon(MainClass.class.getResource("../res/image/map02.png")).getImage());	
		
		for (Sheep shp : this.sheep) {
			Thread t = new Thread(shp);
			t.start();
		}

		for (GrassTile gTile : this.GTile) {
			Thread t = new Thread(gTile);
			t.start();
		}
	}

	public void run() {	
		if(ScreenGraphic.getInstance().isSimulRun == false)
			this.init();
		this.before_time = System.nanoTime();
		
		while (this.flag) {
			this.now_time = System.nanoTime();
			if (this.now_time - this.before_time > MainClass.SECOND * 10 / MainClass.simulationSpeed) {
				MainClass.simulateYear++;
				this.before_time = this.now_time;
			}
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.yield();
			}
		}
	}

	public void addSheep() {
		Sheep newSheep = SheepFactory.getInstance().makeSheep();
		this.sheep.add(newSheep);
		Thread t = new Thread(newSheep);
		t.start();
	}

	public void close() {
		
		this.simulationExitButton.setVisible(false);
		this.slowButton.setVisible(false);
		this.fastButton.setVisible(false);
		this.addButton.setVisible(false);
		Sheep.close();
		GrassTile.close();
		
		DataBase.getInstance()
				.saveSimul(new SimulationData(this.simulID, MainClass.simulateYear, this.sheep, this.GTile));
		this.flag = false;
		ScreenGraphic.getInstance().isSimulRun = false;
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
