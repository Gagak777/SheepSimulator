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
		//DB�� ����
	}
	
	public String getSimulID() {
		return this.simulID;
	}
	
	//paint �� repaint ������
	
}
