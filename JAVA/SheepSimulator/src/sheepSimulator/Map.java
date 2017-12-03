package sheepSimulator;

public class Map {
	private final static int tileSize = 80;
	
	private static Map Instance = null;

	private boolean[][] tileInfo;

	private Map() {
		tileInfo = new boolean[MainClass.S_WIDTH / tileSize][MainClass.S_WIDTH / tileSize];
	}
	
	public static Map getInstance() {
		if(Instance == null)
			Instance = new Map();
		return Instance;
	}

	public boolean isValid(int x, int y) {
		return tileInfo[(int) (x / tileSize)][(int) (y / tileSize)];
	}
}