package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class GrassTile extends Thread {

	private Image grassImage;
	private int grassCap;

	private int loc_x;
	private int loc_y;

	GrassTile(int loc_x, int loc_y) {
		this.grassImage = new ImageIcon(MainClass.class.getResource("../res/image/grass_3.png")).getImage();
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.grassCap = 10;
	}
	
	GrassTile(int loc_x, int loc_y, int grassCap) {
		
		if(grassCap <= 0) 
			this.grassImage = new ImageIcon("").getImage();
		if(grassCap > 0 && grassCap <= 3)
			this.grassImage = new ImageIcon(MainClass.class.getResource("../res/image/grass_1.png")).getImage();
		else if (grassCap > 3 && grassCap <= 7)
			this.grassImage = new ImageIcon(MainClass.class.getResource("../res/image/grass_2.png")).getImage();
		else if (grassCap > 7 && grassCap <= 10)
			this.grassImage = new ImageIcon(MainClass.class.getResource("../res/image/grass_3.png")).getImage();
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.grassCap = grassCap;
	}

	@Override
	public void run() {
		while (true) {
			//이미지 세팅
			if (MainClass.pause)
				continue;

			if (this.grassCap < 10) {
				try {
					this.sleep(10000);
					this.grassCap++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public void close() {
		this.interrupt();
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
	@Override
	public Object clone() {
		return new GrassTile(this.loc_x, this.loc_y, this.grassCap);
	}
}
