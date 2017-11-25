package sheepSimulator;

public class Sheep extends Thread {

	private int loc_x;
	private int loc_y;
	
	private int satiety; //포만감
	private int lifeLimit;
	private int birth;
	private int stamina;
	
	private int sheepState; //열거체로 자기 먹기 죽기 구현

	public Sheep() {
		this.satiety = 100;
		this.birth = MainClass.simulateYear;
		this.stamina = 30;
		this.lifeLimit = 10 + (int)(Math.random() * 6 - 3);
		
	}

	@Override
	public void run() {
		while(!this.isDeath()) {
			//양 행동 구현
		}
		if(this.isDeath()) {
			this.death();
		}
	}

	private void cry() {
		
	}
	
	private void stand() {
		
	}
	
	private void walk() {
		
	}
	
	private void eat() {

	}

	private void sleep() {

	}
	
	private void love() {
		
	}
	
	private void death() {
		
	}

	private boolean isDeath() {
		if ((MainClass.simulateYear - birth) < lifeLimit)
			return false;
		else
			return true;
	}

}
