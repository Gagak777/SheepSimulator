package sheepSimulator;

import java.awt.Image;

import javax.swing.ImageIcon;

public class GrassTile extends Thread {
	
	private Image tileImage;
	private int grassCap;

	private int loc_x;
	private int loc_y;

	GrassTile(int loc_x, int loc_y) {
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.grassCap = 10;
		// �̹��� �ʱ�ȭ
		this.tileImage = new ImageIcon(MainClass.class.getResource("../res/image/grass_1.png")).getImage();
	}

	@Override
	public void run() {
		while (true) {
			if (MainClass.pause)
				continue;
			// ���η���
		}
	}

	public void close() {
		this.interrupt();
	}
}
