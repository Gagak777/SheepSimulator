package sheepSimulator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StartMenu extends JFrame {
	
	private Image simulatorImage;
	private Graphics simulatorGraphic;
	
	private Image backGround;
	
	public StartMenu() {
		this.setTitle("Sheep Simulator");
		this.setSize(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		backGround = new ImageIcon(MainClass.class.getResource("../res/image/backGround.png")).getImage();		
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
}
