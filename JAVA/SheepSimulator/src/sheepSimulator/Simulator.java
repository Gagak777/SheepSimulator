package sheepSimulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Simulator implements Runnable {

	private static Simulator Instance = null;

	private JLabel yearLabel;
	private Font yearFont;
	private JLabel speedLabel;
	private Font speedFont;
	
	private SimulationExitButton simulationExitButton;
	private SlowButton slowButton;
	private FastButton fastButton;
	private AddSheepButton addButton;
	private PauseButton pauseButton;

	private ArrayList<Sheep> sheep;
	private ArrayList<GrassTile> GTile;
	private String simulID;
	private int year;
	private boolean flag;

	private long before_time;
	private long now_time;

	private Simulator() {

		this.simulationExitButton = new SimulationExitButton();
		ScreenGraphic.getInstance().add(this.simulationExitButton);
		this.slowButton = new SlowButton();
		ScreenGraphic.getInstance().add(this.slowButton);
		this.fastButton = new FastButton();
		ScreenGraphic.getInstance().add(this.fastButton);
		this.addButton = new AddSheepButton();
		ScreenGraphic.getInstance().add(this.addButton);
		this.pauseButton = new PauseButton();
		ScreenGraphic.getInstance().add(this.pauseButton);
		
		this.yearFont = new Font("default", Font.PLAIN, 70);
		this.yearLabel = new JLabel();
		this.yearLabel.setForeground(new Color(99,68,40));
		this.yearLabel.setFont(this.yearFont);
		this.yearLabel.setBounds(1630, 294, 271, 105);
		this.yearLabel.setHorizontalAlignment(JLabel.CENTER);
		ScreenGraphic.getInstance().add(this.yearLabel);
		
		this.speedFont = new Font("default", Font.PLAIN, 50);
		this.speedLabel = new JLabel();
		this.speedLabel.setForeground(new Color(99,68,40));
		this.speedLabel.setFont(this.speedFont);
		this.speedLabel.setBounds(1698, 550, 136, 105);
		this.speedLabel.setHorizontalAlignment(JLabel.CENTER);
		ScreenGraphic.getInstance().add(this.speedLabel);
		
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
		this.pauseButton.setVisible(true);
		this.yearLabel.setVisible(true);
		this.speedLabel.setVisible(true);

		this.yearLabel.setText(String.valueOf(year));
		this.speedLabel.setText(String.valueOf(MainClass.simulationSpeed));
		
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
			if(MainClass.pause) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.yield();
				continue;
			}
			
			this.now_time = System.nanoTime();
			if (this.now_time - this.before_time > MainClass.SECOND * 10 / MainClass.simulationSpeed) {
				MainClass.simulateYear++;
				this.yearLabel.setText(String.valueOf(MainClass.simulateYear));
				this.before_time = this.now_time;
			}
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
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
		this.pauseButton.setVisible(false);
		this.yearLabel.setVisible(false);
		this.speedLabel.setVisible(false);
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
	
	public void consumeGrass(int x, int y) {
		for (GrassTile grass : this.GTile) {
			if (grass.isGrass(x, y)) {
				grass.consume();
				break;
			}
		}
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
	
	public void updateSpeed(){
		this.speedLabel.setText(String.valueOf(MainClass.simulationSpeed));
	}
}
