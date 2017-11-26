package sheepSimulator;

public class Sheep extends Thread {

	private int loc_x;
	private int loc_y;
	
	private int satiety; //������
	private int lifeLimit;
	private int birth;
	private int stamina;
	private boolean sex; //0: ����, 1: ����
	
	private int sheepState; //����ü�� �ڱ� �Ա� �ױ� ����
	private Music cry;
	
	public Sheep(){ //dummy
		
	}
	
	public Sheep(boolean sex) {
		this.sex = sex;
		this.satiety = 100;
		this.birth = MainClass.simulateYear;
		this.stamina = 30;
		this.lifeLimit = 10 + (int)(Math.random() * 6 - 3);
	}

	@Override
	public void run() {
		while(!this.isDeath()) {
			//�� �ൿ ����
		}
		if(this.isDeath()) {
			this.death();
		}
	}

	private void cry() {
		int temp = (int)(Math.random()*2-0.1);
		switch(temp) {
		case 0:
			cry = new Music("CrySheep1.mp3");
		case 1:
			cry = new Music("CrySheep2.mp3");
		}
		cry.start();
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
