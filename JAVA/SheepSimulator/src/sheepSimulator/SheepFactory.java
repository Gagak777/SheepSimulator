package sheepSimulator;

public class SheepFactory {
	
	private static SheepFactory Instance;
	
	static {
		Instance = null;
	}
	
	private SheepFactory() {}
	
	public SheepFactory getInstance() {
		if(Instance == null)
			this.Instance = new SheepFactory();
		
		return Instance;
	}
}
