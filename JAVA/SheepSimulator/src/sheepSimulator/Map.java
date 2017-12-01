package sheepSimulator;

public class Map {
	private final static int tileSize = 60;

	private boolean[][] tileInfo;

	public Map() {
		tileInfo = new boolean[MainClass.S_WIDTH / tileSize][MainClass.S_WIDTH / tileSize];
	}

	public boolean promising(int x, int y) {
		return tileInfo[(int) (x / tileSize)][(int) (y / tileSize)];
	}
}