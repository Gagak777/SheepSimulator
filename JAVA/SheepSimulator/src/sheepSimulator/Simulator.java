package sheepSimulator;

import java.util.Vector;

public class Simulator {
	
	private static Simulator Instance = null;
	
	private Vector<Sheep> sheep;
	private String simulID;
		
	private Simulator() {}
	
	public static Simulator getInstance() {
		if(Instance == null)
			Instance = new Simulator();
		
		return Instance;
	}
	
	public void excute() {
		
	}
}
