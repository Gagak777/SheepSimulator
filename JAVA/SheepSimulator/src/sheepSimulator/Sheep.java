package sheepSimulator;

import java.awt.Image;

public class Sheep extends Thread {

	private int loc_x;
	private int loc_y;

	private Image appearance; // 양 외모
	private int satiety; // 포만감
	private int lifeLimit;
	private int birth;
	private int stamina;
	private boolean sex; // 0: 수컷, 1: 암컷

	private int t_count; // 양 행동지속 남은 시간
	private boolean isExcute;

	private int sheepState; // 열거체로 자기 먹기 죽기 구현
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
			// 양 행동 구현
			if (MainClass.pause) { // 게임 일시정지일 경우
				continue;
			}

			if (this.stamina == 0) {
				this.sheepState = SheepStatus.valueOf("SLEEP").ordinal();
			} else if (this.satiety < 50) {
				this.sheepState = SheepStatus.valueOf("EAT").ordinal();
				// count 설정
			} else if (this.isExcute == false) {
				this.sheepState = (int) (Math.random() * 4 - 0.1);
				// count 설정
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
			// 서 있을 시간설정
			this.isExcute = true;
		}
		
		// 좌표 고정, 제자리에 있는 모션

		if (this.t_count == 0)
			this.isExcute = false;
	}

	private void walk() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		
		// 양 걷는 모션
		
		
	}

	private void eat() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		// 양 먹는 모션
		
	}

	private void sleep() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		// 양 자는 모션
		
		if(this.stamina == 30)
			this.isExcute = false;
	}

	private void love() {
		if(this.isExcute == false) {
			this.isExcute = true;
		}
		//사랑하는 모션
		

	}

	private void death() {
		// 양이 죽는 모션 넣기
	}

	private boolean isDeath() {
		if ((MainClass.simulateYear - birth) < lifeLimit && this.satiety > 0)
			return false;
		else
			return true;
	}

}
