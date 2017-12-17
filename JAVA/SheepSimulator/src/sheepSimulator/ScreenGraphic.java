package sheepSimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;

public class ScreenGraphic extends JFrame {
	private static ScreenGraphic Instance;
	protected boolean isSimulRun = false;

	private Image backGround;
	private Image simulatorImage;
	private Graphics2D simulatorGraphic;

	private ScreenGraphic() {
		this.setTitle("Sheep Simulator");
		this.setUndecorated(true);
		this.setSize(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setLayout(null);
	}

	public static ScreenGraphic getInstance() {
		if (Instance == null) {
			Instance = new ScreenGraphic();
		}
		return Instance;
	}

	public void setBackGround(Image backGround) {
		this.backGround = backGround;
	}

	@Override
	public void paint(Graphics g) {
		simulatorImage = createImage(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		simulatorGraphic = (Graphics2D) simulatorImage.getGraphics();
		imageDraw(simulatorGraphic);
		g.drawImage(simulatorImage, 0, 0, null);
	}

	public void imageDraw(Graphics2D g) {
		g.drawImage(this.backGround, 0, 0, null);
		this.paintComponents(g);
		if (this.isSimulRun) {
			for (GrassTile grass : Simulator.getInstance().getGrassList()) {
				grass.drawImage(g);
			}
			for (Sheep sheep : Simulator.getInstance().getSheepList()) {
				sheep.drawImage(g);
			}
		}
		this.repaint();
	}

}
