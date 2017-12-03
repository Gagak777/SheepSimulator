package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Sheep extends Thread {

	private int loc_x; // ����
	private int loc_y; // ����
	private int vector; // ����

	private Image appearance; // �� �ܸ�, ����
	private int satiety; // ������, ����
	private int lifeLimit; // ����
	private int birth; // ����
	private int stamina; // ����
	private boolean sex; // 0: ����, 1: ����, ����

	private int t_count; // �� �ൿ���� ���� �ð�, ����
	private long before_clock;
	private long now_clock;
	private boolean isExcute; // ����

	private int sheepState; // ����ü�� �ڱ� �Ա� �ױ� ����, ����
	private Music cry;

	public Sheep() {
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

		this.vector = 0;
		this.t_count = 0;
		this.sheepState = -1;
		
		this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep.png")).getImage();
	}

	@Override
	public void run() {
		while (!this.isDeath()) {
			if (MainClass.pause) { // ���� �Ͻ������� ���
				continue;
			}

			if (this.stamina == 0 && sheepState != SheepStatus.valueOf("SLEEP").ordinal()) {
				this.sheepState = SheepStatus.valueOf("SLEEP").ordinal();
				this.isExcute = false;
			} else if (this.satiety < 50 && sheepState != SheepStatus.valueOf("EAT").ordinal()) {
				this.sheepState = SheepStatus.valueOf("EAT").ordinal();
				this.isExcute = false;
			} else if (this.isExcute == false) {
				this.sheepState = (int) (Math.random() * 2 - 0.1);
			}

			if ((int) (Math.random()) * 100 < 3) {
				this.cry();
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

	public void close() {
		this.interrupt();
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
			this.t_count = (int) (Math.random() * 5 + 1) * MainClass.BASE_SPEED;
			this.isExcute = true;
			this.before_clock = System.nanoTime();
		}

		this.now_clock = System.nanoTime();

		if (this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {
			this.t_count--;
			// ��� �ٲٱ�
			this.before_clock = this.now_clock;
		}

		if (this.t_count == 0)
			this.isExcute = false;
	}

	private void walk() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.t_count = (int) (Math.random() * 2 + 4);
			this.vector = (int) (Math.random() * 360);
			this.before_clock = System.nanoTime();
		}
		
		this.now_clock = System.nanoTime();

		if (this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {

		while (!Map.getInstance().isValid(this.loc_x + (int) Math.cos(this.vector),
				this.loc_y + (int) Math.sin(this.vector))) {
			this.vector = (int) (Math.random() * 360);
		}

		this.loc_x += (int) Math.cos(this.vector);
		this.loc_y += (int) Math.sin(this.vector);

		
			this.t_count--;
			// ��� �ٲٱ�
			this.before_clock = this.now_clock;

			this.loc_x += (int) Math.cos(this.vector);
			this.loc_y += (int) Math.sin(this.vector);
		}
	}
	
	private void eat() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.before_clock = System.nanoTime();
		}
		// ������ġ�� Ǯ�� ������� Ǯ�� ã��

		// �� �Դ� ���

	}

	private void sleep() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.before_clock = System.nanoTime();
		}

		this.now_clock = System.nanoTime();

		if (this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {
			this.stamina += 6 / MainClass.BASE_SPEED;
			// ��� �ٲٱ�
			this.before_clock = this.now_clock;

		}

		if (this.stamina == 30)
			this.isExcute = false;
	}

	private void love() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.before_clock = System.nanoTime();
		}
		// ����ϴ� ���

	}

	private void death() { // �ణ �ٸ���
		// 3������ �״¸��
	}

	private boolean isDeath() {
		if ((MainClass.simulateYear - birth) < lifeLimit && this.satiety > 0)
			return false;
		else
			return true;
	}

	protected void drawImage(Graphics2D g) {
		g.drawImage(this.appearance, this.loc_x, this.loc_y, null);
	}

}
