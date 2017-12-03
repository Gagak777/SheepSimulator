package sheepSimulator;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class LogoutButton extends Button {
	public LogoutButton() {
		this.buttonImage = new ImageIcon(MainClass.class.getResource("../res/image/logoutButton.png"));
		this.buttonEnteredImage = new ImageIcon(MainClass.class.getResource("../res/image/logoutButton_entered.png"));
		
		this.setIcon(this.buttonImage);
		this.setBounds(555, 811, 840, 192);
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
				StartMenu.getInstance().logout();
			}
		});
	}
}
