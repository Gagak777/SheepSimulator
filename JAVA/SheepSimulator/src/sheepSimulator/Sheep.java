package sheepSimulator;

import java.awt.Image;

public class Sheep extends Thread {

	private int loc_x;
	private int loc_y;

	private Image appearance; // �� �ܸ�
	private int satiety; // ������
	private int lifeLimit;
	private int birth;
	private int stamina;
	private boolean sex; // 0: ����, 1: ����

	private int t_count; // �� �ൿ���� ���� �ð�
	private boolean isExcute;

	private int sheepState; // ����ü�� �ڱ� �Ա� �ױ� ����
	private Music cry;

	public Sheep() { // dummy
		this(SheepSex.valueOf("RANDOM").ordinal());
	}

	public Sheep(int sex) {

		if (SheepSex.values()[sex] == SheepSex.valueOf("RANDOM")) {
			sex = (int) (Math.random() * 2 - 0.1);
		}

		switch (SheepSex.values()[sex]) {
		case MALE:
			this.sex = false;
			break;
		case FEMALE:
			this.sex = true;
			break;
		}
		this.satiety = 100;
		this.birth = MainClass.simulateYear;
		this.stamina = 30;
		this.lifeLimit = 10 + (int) (Math.random() * 6 - 3);
		this.isExcute = false;
	}

	@Override
	public void run() {
		while (!this.isDeath()) {
			// �� �ൿ ����
			if (MainClass.pause) { // ���� �Ͻ������� ���
				continue;
			}

			if (this.stamina == 0) {
				this.sheepState = SheepStatus.valueOf("SLEEP").ordinal();
			} else if (this.satiety < 50) {
				this.sheepState = SheepStatus.valueOf("EAT").ordinal();
				// count ����
			} else if (this.isExcute == false) {
				this.sheepState = (int) (Math.random() * 4 - 0.1);
				// count ����
			}

			switch (SheepStatus.values()[this.sheepState]) {
			case STAND:
				this.stand();
			case WALK:
				this.walk();
			case EAT:
				this.eat();
			case SLEEP:
				this.sleep();
			case LOVE:
				this.love();
			}

		}
		this.death();
	}

	private void cry() {
		int temp = (int) (Math.random() * 2 - 0.1);
		switch (temp) {
		case 0:
			cry = new Music("CrySheep1.mp3");
		case 1:
			cry = new Music("CrySheep2.mp3");
		}
		cry.start();
	}

	private void stand() {
		if (this.isExcute == false) {
			// �� ���� �ð�����
			this.isExcute = true;
		}
		
		// ��ǥ ����, ���ڸ��� �ִ� ���

		if (this.t_count == 0)
			this.isExcute = false;
	}

	private void walk() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		
		// �� �ȴ� ���
		
		
	}

	private void eat() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		// �� �Դ� ���
		
	}

	private void sleep() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		// �� �ڴ� ���
		
		if(this.stamina == 30)
			this.isExcute = false;
	}

	private void love() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		//����ϴ� ���
		

	}

	private void death() {
		// ���� �״� ��� �ֱ�
	}

	private boolean isDeath() {
		if ((MainClass.simulateYear - birth) < lifeLimit && this.satiety > 0)
			return false;
		else
			return true;
	}

}
