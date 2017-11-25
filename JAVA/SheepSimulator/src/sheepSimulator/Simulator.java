package sheepSimulator;

public class Simulator {
	
	private static Simulator Instance;
	
	static {
		Instance = null;
	}
	
	private Simulator() {}
	
	public static Simulator getInstance() {
		if(Instance == null)
			Instance = new Simulator();
		
		return Instance;
	}
	
	public void excute() {
		
	}
}
