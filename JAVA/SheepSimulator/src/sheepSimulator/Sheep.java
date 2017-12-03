package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Sheep extends Thread {

	private int loc_x; // 저장
	private int loc_y; // 저장
	private int vector; // 저장

	private Image appearance; // 양 외모, 저장
	private int satiety; // 포만감, 저장
	private int lifeLimit; // 저장
	private int birth; // 저장
	private int stamina; // 저장
	private boolean sex; // 0: 수컷, 1: 암컷, 저장

	private int t_count; // 양 행동지속 남은 시간, 저장
	private long before_clock;
	private long now_clock;
	private boolean isExcute; // 저장

	private int sheepState; // 열거체로 자기 먹기 죽기 구현, 저장
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
			if (MainClass.pause) { // 게임 일시정지일 경우
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
			// 모션 바꾸기
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
			// 모션 바꾸기
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
		// 현재위치에 풀이 없을경우 풀을 찾자

		// 양 먹는 모션

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
			// 모션 바꾸기
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
		// 사랑하는 모션

	}

	private void death() { // 약간 다르게
		// 3프레임 죽는모션
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
