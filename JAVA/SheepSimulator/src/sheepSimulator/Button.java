package sheepSimulator;

import javax.swing.ImageIcon;
import javax.swing.JButton;

abstract public class Button extends JButton {
	protected ImageIcon buttonImage;
	protected ImageIcon buttonEnteredImage;

	protected Music buttonPressed;
	protected Music buttonEntered;
}