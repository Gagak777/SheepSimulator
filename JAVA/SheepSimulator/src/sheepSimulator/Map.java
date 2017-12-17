package sheepSimulator;

public class Map {
	private final static int tileSize = 80;

	private final static boolean t = true;
	private final static boolean f = false;
	
	private static Map Instance = null;

	private boolean[][] tileInfo;

	private Map() {
		tileInfo = new boolean[MainClass.G_WIDTH / tileSize][MainClass.G_HEIGHT / tileSize]; // 20 * 13
		
		boolean [][] tempMap;
		tempMap = new boolean[][]{
				{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f},
				{f, f, f, f, f, f, f, f, t, t, t, t, t, t, t, t, t, f, f, f},
				{f, f, f, f, f, f, f, f, t, t, t, t, t, t, t, t, t, f, f, f},
				{f, f, f, f, t, t, f, f, t, t, t, t, t, t, t, t, t, t, f, f},
				{f, f, t, t, t, t, f, f, t, t, t, t, t, t, t, t, t, t, t, f},
				{f, f, f, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, f},
				{f, f, f, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, f},
				{f, f, f, f, t, t, f, t, t, t, t, t, t, t, t, t, t, f, f, f},
				{f, f, f, f, f, t, t, t, t, t, t, t, t, t, t, t, f, f, f, f},
				{f, f, f, f, f, f, f, f, t, t, t, t, t, t, t, t, f, f, f, f},
				{f, f, f, f, f, f, f, f, f, t, t, t, t, t, t, t, f, f, f, f},
				{f, f, f, f, f, f, f, f, f, t, f, f, t, t, t, t, f, f, f, f},
				{f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f}
		};

		tileInfo = tempMap;
	}

	public static Map getInstance() {
		if (Instance == null)
			Instance = new Map();
		return Instance;
	}

	public boolean isValid(int x, int y) {
		return tileInfo[(int) (y / tileSize)][(int) (x / tileSize)];
	}
}