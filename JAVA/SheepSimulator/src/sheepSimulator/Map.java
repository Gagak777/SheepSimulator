package sheepSimulator;

public class Map {
	private final static int tileSize = 80;

	private static Map Instance = null;

	private boolean[][] tileInfo;

	private Map() {
		tileInfo = new boolean[MainClass.S_WIDTH / tileSize][MainClass.S_WIDTH / tileSize];

		for (int i = 1; i < MainClass.S_WIDTH / tileSize - 1; i++)
			for (int j = 1; j < 20; j++)
				tileInfo[i][j] = true;
/*
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
				tileInfo[i][j] = false;

		for (int j = 0; j < 3; j++)
			tileInfo[3][j] = false;

		for (int i = 5; i < MainClass.S_WIDTH / tileSize - 1; i++)
			for (int j = 1; j < 3; j++)
				tileInfo[i][j] = false;

		for (int i = 0; i < 5; i++)
			for (int j = 5; j < 8; j++)
				tileInfo[i][j] = false;

		for (int j = 12; j < 20; j++)
			tileInfo[0][j] = false;
		
		for(int i = 0;i<5;i++)
			for(int j = 17;j<20;j++) 
				tileInfo[i][j] = false;
		
		for(int i = 7; i < MainClass.S_WIDTH / tileSize - 1; i++)
			tileInfo[i][3] = false;
		
		for(int i = 9;i < MainClass.S_WIDTH / tileSize - 1; i++)
			for(int j = 4; j<9;j++)
				tileInfo[i][j] = false;

		for(int i = 12;i < MainClass.S_WIDTH / tileSize - 1; i++)
			for(int j = 18;j<20;j++)
				tileInfo[i][j] = false;*/
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