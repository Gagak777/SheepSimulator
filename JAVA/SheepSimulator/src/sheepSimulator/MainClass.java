package sheepSimulator;

public class MainClass {

	public static final int S_WIDTH = 1920;
	public static final int S_HEIGHT = 1080;

	public static final long SECOND = 1000000000;

	public static float simulationSpeed = 1;
	public static int BASE_SPEED = 3;
	public static int SHEEP_SPEED = 14;

	public static int simulateYear = 0;
	public static boolean pause = false;

	public static void main(String[] args) {
		StartMenu.getInstance();
	}
}