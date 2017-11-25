package sheepSimulator;


import javax.swing.ImageIcon;
import javax.swing.JButton;

abstract public class Button extends JButton {
	protected ImageIcon buttonImage;
	protected ImageIcon buttonEnteredImage;
	
	protected static Music buttonPressed;
	protected static Music buttonEntered;
	
	static {
		buttonPressed = new Music("PressedButton.mp3");
		buttonEntered = new Music("EnteredButton.mp3");
	}
}
