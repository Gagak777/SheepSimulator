package sheepSimulator;

public class Map {
	private final static int tileSize = 60;

	private static Map Instance = null;

	private boolean[][] tileInfo;

	private Map() {
		tileInfo = new boolean[MainClass.S_WIDTH / tileSize][MainClass.S_WIDTH / tileSize];

		for (int i = 1; i < MainClass.S_WIDTH / tileSize - 1; i++)
			for (int j = 1; j < MainClass.S_WIDTH / tileSize - 1; j++)
				tileInfo[i][j] = true;
	}

	public static Map getInstance() {
		if (Instance == null)
			Instance = new Map();
		return Instance;
	}

	public boolean isValid(int x, int y) {
		return tileInfo[(int) (x / tileSize)][(int) (y / tileSize)];
	}
}