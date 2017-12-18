package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class GrassTile implements Runnable {
	private static boolean isRun = true;

	private Image grassImage;
	private Image blank;
	private Image grass1;
	private Image grass2;
	private Image grass3;

	private int grassCap;

	private int loc_x;
	private int loc_y;

	private long now_time;
	private long before_time;

	GrassTile(int loc_x, int loc_y) {
		this.blank = new ImageIcon("").getImage();
		this.grass1 = new ImageIcon(MainClass.class.getResource("../res/image/grass_1.png")).getImage();
		this.grass2 = new ImageIcon(MainClass.class.getResource("../res/image/grass_2.png")).getImage();
		this.grass3 = new ImageIcon(MainClass.class.getResource("../res/image/grass_3.png")).getImage();
		this.grassImage = this.grass3;
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.grassCap = 10;
	}

	GrassTile(int loc_x, int loc_y, int grassCap) {
		this.blank = new ImageIcon("").getImage();
		this.grass1 = new ImageIcon(MainClass.class.getResource("../res/image/grass_1.png")).getImage();
		this.grass2 = new ImageIcon(MainClass.class.getResource("../res/image/grass_2.png")).getImage();
		this.grass3 = new ImageIcon(MainClass.class.getResource("../res/image/grass_3.png")).getImage();
		
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.grassCap = grassCap;
		
		if (grassCap <= 0)
			this.grassImage = this.blank;
		if (grassCap > 0 && grassCap <= 3)
			this.grassImage = this.grass1;
		else if (grassCap > 3 && grassCap <= 7)
			this.grassImage = this.grass2;
		else if (grassCap > 7 && grassCap <= 10)
			this.grassImage = this.grass3;
	}

	@Override
	public void run() {
		isRun = true;

		this.before_time = System.nanoTime();
		while (isRun) {
			if (MainClass.pause) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.yield();
				continue;
			}
			
			if (this.grassCap < 10) {
				this.now_time = System.nanoTime();


				if (grassCap <= 0)
					this.grassImage = this.blank;
				if (grassCap > 0 && grassCap <= 3)
					this.grassImage = this.grass1;
				else if (grassCap > 3 && grassCap <= 7)
					this.grassImage = this.grass2;
				else if (grassCap > 7 && grassCap <= 10)
					this.grassImage = this.grass3;

				if (this.now_time - this.before_time > MainClass.SECOND * 100 / (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {
					this.before_time = this.now_time;
					this.grassCap++;
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}

	public void consume() {
		this.grassCap--;
	}

	public static void close() {
		isRun = false;
	}

	public void drawImage(Graphics2D g) {
		g.drawImage(this.grassImage, this.loc_x, this.loc_y, null);
	}

	public int get_grassCap() {
		return this.grassCap;
	}

	public int get_x() {
		return this.loc_x;
	}

	public int get_y() {
		return this.loc_y;
	}

	public boolean isGrass(int x, int y) {
		if (this.loc_x == x && this.loc_y == y && this.grassCap > 0)
			return true;
		return false;
	}
}
