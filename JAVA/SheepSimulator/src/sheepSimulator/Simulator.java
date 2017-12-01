package sheepSimulator;

import java.util.Vector;

public class Simulator {
	
	private Vector<Sheep> sheep;
	private String simulID;
	private int year;
		
	public Simulator(String simulID) {
		this.simulID = simulID;
	}
	
	public void excute() {
		
	}
	
	public void close() {
		//DB에 저장
	}
	
	public String getSimulID() {
		return this.simulID;
	}
	
	//paint 및 repaint 넣을것
	
}
