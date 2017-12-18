package sheepSimulator;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class PauseButton extends Button {
	public PauseButton() {
		this.buttonImage = new ImageIcon(MainClass.class.getResource("../res/image/pause.png"));
		this.buttonEnteredImage = new ImageIcon(MainClass.class.getResource("../res/image/pause_entered.png"));

		this.setIcon(this.buttonImage);
		this.setBounds(1630, 805, 271, 105);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				buttonEntered = new Music("ButtonEntered.mp3");
				buttonEntered.start();
				setIcon(buttonEnteredImage);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setIcon(buttonImage);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				buttonPressed = new Music("ButtonPressed.mp3");
				buttonPressed.start();
				if(!MainClass.pause)
				{
					buttonImage = new ImageIcon(MainClass.class.getResource("../res/image/restart.png"));
					buttonEnteredImage = new ImageIcon(MainClass.class.getResource("../res/image/restart_entered.png"));
				}
				else
				{
					buttonImage = new ImageIcon(MainClass.class.getResource("../res/image/pause.png"));
					buttonEnteredImage = new ImageIcon(MainClass.class.getResource("../res/image/pause_entered.png"));
				}
				MainClass.pause = !MainClass.pause;
			}
		});
	}
}
