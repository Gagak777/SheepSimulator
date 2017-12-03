package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;

public class GrassTile extends Thread {

	private Image grassImage;
	private String GImg_url;
	private int grassCap;

	private int loc_x;
	private int loc_y;

	GrassTile(int loc_x, int loc_y) {
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.grassCap = 10;
		// 이미지 초기화
	}

	@Override
	public void run() {
		while (true) {
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
	
	public String get_imgURL() {
		return this.GImg_url;
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
