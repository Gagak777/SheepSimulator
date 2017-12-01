package sheepSimulator;

import java.awt.Image;

public class GrassTile extends Thread {
	private Image tileImage;
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
			// 메인루프
		}
	}

	public void close() {
		this.interrupt();
	}
}
