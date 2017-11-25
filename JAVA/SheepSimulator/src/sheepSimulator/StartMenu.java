package sheepSimulator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StartMenu extends JFrame {

	private Image simulatorImage;
	private Graphics simulatorGraphic;

	private Image backGround;

	private static StartMenu Instance = null;
	private User loginedUser;

	private Music backGroundMusic1;
	private Music backGroundMusic2;

	private StartMenu() {
	}

	public static StartMenu getInstance() {
		if (Instance == null)
			Instance = new StartMenu();
		return Instance;
	}

	public void excute() {
		this.backGroundMusic1 = new Music("BackGroundMusic.mp3", true);
		this.backGroundMusic2 = new Music("BackGroundMusic_Cry.mp3", true);
		this.setTitle("Sheep Simulator");
		this.setSize(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		backGround = new ImageIcon(MainClass.class.getResource("../res/image/backGround.png")).getImage();

		this.backGroundMusic1.start();
		this.backGroundMusic2.start();
	}

	public void paint(Graphics g) {
		simulatorImage = createImage(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		simulatorGraphic = simulatorImage.getGraphics();
		imageDraw(simulatorGraphic);
		g.drawImage(simulatorImage, 0, 0, null);
	}

	public void imageDraw(Graphics g) {
		g.drawImage(backGround, 0, 0, null);
		this.repaint();
	}

	public void login() {

	}

	public void logout() {
		loginedUser = null;
	}

	public void simulate() {
		this.backGroundMusic1.close();
		this.backGroundMusic2.close();
		Simulator.getInstance().excute();
	}

	public void setting() {

	}
}
