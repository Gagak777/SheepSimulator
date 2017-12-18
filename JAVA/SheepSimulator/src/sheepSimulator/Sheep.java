package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;

public class Sheep implements Runnable {
	private static boolean isRun;

	private int loc_x; // 저장
	private int loc_y; // 저장
	private int vector; // 저장, Radian각도
	private Queue<Point> route;

	private int frame = 1;

	private Image appearance; // 양 외모, 저장

	private Image dead_left;
	private Image dead_right;
	private Image stand_left;
	private Image stand_right;
	private Image stand_left2;
	private Image stand_right2;
	private Image eat_left;
	private Image eat_right;
	private Image eat_left2;
	private Image eat_right2;
	private Image sleep_left1;
	private Image sleep_right1;
	private Image sleep_left2;
	private Image sleep_right2;

	private String app_url; // 양 이미지 url
	private int satiety; // 포만감, 저장
	private int lifeLimit; // 저장
	private int birth; // 저장
	private int stamina; // 저장
	private boolean sex; // 0: 수컷, 1: 암컷, 저장

	private int t_count; // 양 행동지속 남은 시간, 저장
	private int checkSecond;
	private long before_clock;
	private long now_clock;
	private boolean isExcute; // 저장

	private int sheepState; // 열거체로 자기 먹기 죽기 구현, 저장
	private Music cry;

	public Sheep() {
		this(SheepSex.valueOf("RANDOM").ordinal());
		this.appearance = this.stand_right;
		this.app_url = "sheep_stand_right.png";
	}

	public Sheep(int sex) {
		this.dead_left = new ImageIcon(MainClass.class.getResource("../res/image/sheep_dead_left.png")).getImage();
		this.dead_right = new ImageIcon(MainClass.class.getResource("../res/image/sheep_dead_right.png")).getImage();
		this.stand_right = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_right.png")).getImage();
		this.stand_left = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_left.png")).getImage();
		this.stand_right2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand2_right.png"))
				.getImage();
		this.stand_left2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand2_left.png")).getImage();

		this.eat_right = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat_right.png")).getImage();
		this.eat_left = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat_left.png")).getImage();
		this.eat_right2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat2_right.png")).getImage();
		this.eat_left2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat2_left.png")).getImage();

		this.sleep_right1 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep1_right.png"))
				.getImage();
		this.sleep_left1 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep1_left.png")).getImage();
		this.sleep_right2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep2_right.png"))
				.getImage();
		this.sleep_left2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep2_left.png")).getImage();

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
		this.satiety = 150;
		this.birth = MainClass.simulateYear;
		this.stamina = 30;
		this.lifeLimit = 10 + (int) (Math.random() * 6 - 3);
		this.isExcute = false;

		this.vector = 0;
		this.t_count = 0;
		this.sheepState = -1;

		this.loc_x = 1050;
		this.loc_y = 740;
	}

	public Sheep(String appearance, int loc_x, int loc_y, int birth, boolean sex, int lifeLimit, int satiety,
			int stamina, int sheepState, boolean isExcute, int count, int vector) {
		this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/" + appearance)).getImage();
		this.app_url = appearance;
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.birth = birth;
		this.sex = sex;
		this.lifeLimit = lifeLimit;
		this.satiety = satiety;
		this.stamina = stamina;
		this.sheepState = sheepState;
		this.isExcute = isExcute;
		this.t_count = count;
		this.vector = vector;

		this.dead_left = new ImageIcon(MainClass.class.getResource("../res/image/sheep_dead_left.png")).getImage();
		this.dead_right = new ImageIcon(MainClass.class.getResource("../res/image/sheep_dead_right.png")).getImage();
		this.stand_right = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_right.png")).getImage();
		this.stand_left = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_left.png")).getImage();
		this.stand_right2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand2_right.png"))
				.getImage();
		this.stand_left2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand2_left.png")).getImage();

		this.eat_right = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat_right.png")).getImage();
		this.eat_left = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat_left.png")).getImage();
		this.eat_right2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat2_right.png")).getImage();
		this.eat_left2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat2_left.png")).getImage();

		this.sleep_right1 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep1_right.png"))
				.getImage();
		this.sleep_left1 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep1_left.png")).getImage();
		this.sleep_right2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep2_right.png"))
				.getImage();
		this.sleep_left2 = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep2_left.png")).getImage();
	}

	@Override
	public void run() {
		isRun = true;

		this.route = new LinkedList<Point>();
		while (!this.isDeath() && isRun) {
			if (MainClass.pause) { // 게임 일시정지일 경우
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.yield();
				continue;
			}

			if (this.stamina == 0 && sheepState != SheepStatus.valueOf("SLEEP").ordinal()) {
				this.sheepState = SheepStatus.valueOf("SLEEP").ordinal();
				this.isExcute = false;
			} else if (this.satiety < 80 && sheepState != SheepStatus.valueOf("EAT").ordinal()
					&& sheepState != SheepStatus.valueOf("SLEEP").ordinal()) {
				this.sheepState = SheepStatus.valueOf("EAT").ordinal();
				this.isExcute = false;
			} else if (this.isExcute == false) {
				this.sheepState = (int) (Math.random() * 2 - 0.1);
			}

			switch (SheepStatus.values()[this.sheepState]) {
			case STAND:
				this.stand();
				break;
			case WALK:
				this.walk();
				break;
			case EAT:
				this.eat();
				break;
			case SLEEP:
				this.sleep();
				break;
			case LOVE:
				this.love();
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.yield();
		}

		if (this.isDeath())
			this.death();
	}

	static public void close() {
		isRun = false;
	}

	private void cry() {
		if ((int) (Math.random() * 100) < 1) {
			int temp = (int) (Math.random() * 2 - 0.1);
			switch (temp) {
			case 0:
				cry = new Music("CrySheep1.mp3");
			case 1:
				cry = new Music("CrySheep2.mp3");
			}
			cry.start();
		}
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
			this.cry();

			this.checkSecond++;
			if (this.checkSecond >= MainClass.BASE_SPEED * MainClass.simulationSpeed) {
				this.stamina--;
				this.t_count--;
				this.checkSecond = 0;
				this.satiety -= 1;
			}
			// 모션 바꾸기
			if ((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90)
					|| (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360)) {
				if (this.frame == 1) {
					this.appearance = this.stand_right;
					this.app_url = "sheep_stand_right.png";
				} else {
					this.appearance = this.stand_right2;
					this.app_url = "sheep_stand2_right.png";
				}
			} else if (Math.toDegrees(this.vector) >= 90 && Math.toDegrees(this.vector) < 270) {
				if (frame == 1) {
					this.appearance = this.stand_left;
					this.app_url = "sheep_stand_left.png";
				} else {
					this.appearance = this.stand_left2;
					this.app_url = "sheep_stand2_left.png";
				}
			}

			this.frame = this.frame % 2 + 1;
			this.before_clock = this.now_clock;
		}

		if (this.t_count == 0)
			this.isExcute = false;
	}

	private void walk() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.t_count = (int) (Math.random() * 5 + 1);
			this.vector = (int) Math.toRadians(Math.random() * 360); /* 라디안각으로 변경 */
			this.before_clock = System.nanoTime();
			this.checkSecond = 0;
		}

		this.now_clock = System.nanoTime();

		if (this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {
			this.cry();

			while (!Map.getInstance().isValid(this.loc_x + (int) (Math.cos(this.vector) * MainClass.SHEEP_SPEED), /**/
					this.loc_y + (int) (Math.sin(this.vector) * MainClass.SHEEP_SPEED))) {
				this.vector = (int) Math.toRadians(Math.random() * 360); /* 라디안각으로 변경 */
			}

			this.loc_x += (int) (Math.cos(this.vector) * MainClass.SHEEP_SPEED); /**/
			this.loc_y += (int) (Math.sin(this.vector) * MainClass.SHEEP_SPEED);

			this.checkSecond++;
			if (this.checkSecond >= MainClass.BASE_SPEED * MainClass.simulationSpeed) {
				this.stamina--;
				this.t_count--;
				this.checkSecond = 0;
				this.satiety -= 2;
			}

			if ((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90)
					|| (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360)) {
				if (this.frame == 1) {
					this.appearance = this.stand_right;
					this.app_url = "sheep_stand_right.png";
				} else {
					this.appearance = this.stand_right2;
					this.app_url = "sheep_stand2_right.png";
				}
			} else if (Math.toDegrees(this.vector) >= 90 && Math.toDegrees(this.vector) < 270) {
				if (frame == 1) {
					this.appearance = this.stand_left;
					this.app_url = "sheep_stand_left.png";
				} else {
					this.appearance = this.stand_left2;
					this.app_url = "sheep_stand2_left.png";
				}
			}

			this.frame = frame % 2 + 1;

			this.before_clock = this.now_clock;

			this.loc_x += (int) Math.cos(this.vector); /**/
			this.loc_y += (int) Math.sin(this.vector); /**/
		}
		if (this.t_count == 0)
			this.isExcute = false;
	}

	private void eat() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.before_clock = System.nanoTime();
			this.checkSecond = 0;
		}

		this.now_clock = System.nanoTime();

		if (this.satiety >= 150) {
			this.isExcute = false;
			return;
		} else if ((Simulator.getInstance().isGrass(this.loc_x + 60, this.loc_y)
				|| Simulator.getInstance().isGrass(this.loc_x - 60, this.loc_y))
				&& this.now_clock
						- this.before_clock > (MainClass.SECOND / (MainClass.BASE_SPEED * MainClass.simulationSpeed))) {
			this.before_clock = this.now_clock;

			this.checkSecond++;
			if (this.checkSecond >= MainClass.BASE_SPEED * MainClass.simulationSpeed) {
				this.satiety += 15;
				this.stamina--;
				this.checkSecond = 0;
				if ((Simulator.getInstance().isGrass(this.loc_x + 60, this.loc_y)))
					Simulator.getInstance().consumeGrass(this.loc_x + 60, this.loc_y);
				else
					Simulator.getInstance().consumeGrass(this.loc_x - 60, this.loc_y);
			}

			if ((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90)
					|| (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360)) {
				if (this.frame == 1) {
					this.appearance = this.eat_right;
					this.app_url = "sheep_eat_right.png";
				} else {
					this.appearance = this.eat_right2;
					this.app_url = "sheep_eat2_right.png";
				}
			} else if (Math.toDegrees(this.vector) >= 90 && Math.toDegrees(this.vector) < 270) {
				if (this.frame == 1) {
					this.appearance = this.eat_left;
					this.app_url = "sheep_eat_left.png";
				} else {
					this.appearance = this.eat_left2;
					this.app_url = "sheep_eat2_left.png";
				}
			}

			this.frame = this.frame % 2 + 1;

		} else if (this.route.size() != 0 && this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {
			this.cry();
			this.moveTo();
		} else if (this.route.size() == 0)
			this.searchRoute();
	}

	private void sleep() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.before_clock = System.nanoTime();
			this.checkSecond = 0;
			this.frame = 1;
		}

		this.now_clock = System.nanoTime();

		if (this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {

			this.checkSecond++;
			if (this.checkSecond >= MainClass.BASE_SPEED * MainClass.simulationSpeed) {
				this.stamina += 6;
				this.checkSecond = 0;
				this.satiety--;
			}

			if ((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90)
					|| (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360)) {
				if (frame == 1)
					this.appearance = this.sleep_right1;
				else
					this.appearance = this.sleep_right2;
				this.app_url = "sheep_sleep" + this.frame + "_right.png";
			} else if (Math.toDegrees(this.vector) >= 90 && Math.toDegrees(this.vector) < 270) {
				if (frame == 1)
					this.appearance = this.sleep_left1;
				else
					this.appearance = this.sleep_left2;
				this.app_url = "sheep_sleep" + this.frame + "_left.png";
			}

			this.before_clock = this.now_clock;
			this.frame = this.frame % 2 + 1;
		}

		if (this.stamina == 30) {
			this.isExcute = false;
			this.sheepState = SheepStatus.valueOf("STAND").ordinal();
		}
	}

	private void love() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.before_clock = System.nanoTime();
		}
		// 사랑하는 모션
		if ((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90)
				|| (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360)) {
			this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_love_right.png"))
					.getImage();/**/
			this.app_url = "sheep_love_right.png";
		} else if (Math.toDegrees(this.vector) >= 90 && Math.toDegrees(this.vector) < 270) {
			this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_love_left.png")).getImage();
			this.app_url = "sheep_love_right.png";
		}
	}

	private void death() {
		if ((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90)
				|| (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360)) {
			this.appearance = this.dead_right;
			this.app_url = "sheep_dead_right.png";
		} else if (Math.toDegrees(this.vector) >= 90 && Math.toDegrees(this.vector) < 270) {
			this.appearance = this.dead_left;
			this.app_url = "sheep_dead_left.png";
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.appearance = new ImageIcon("").getImage();
	}

	public boolean isDeath() {
		if (((MainClass.simulateYear - birth) < lifeLimit) && (this.satiety > 0))
			return false;
		else
			return true;
	}

	private void moveTo() {
		Point temp = route.poll();
		if (temp.getX() == this.get_x() && temp.getY() == this.get_y()) {
			route.remove();
		} else {
			this.checkSecond++;
			if (this.checkSecond >= MainClass.BASE_SPEED * MainClass.simulationSpeed) {
				this.satiety -= 2;
				this.stamina--;
				this.checkSecond = 0;
			}

			// 모션 바꾸기
			if (temp.getX() - this.loc_x > 0) {
				if (this.frame == 1) {
					this.appearance = this.stand_right;
					this.app_url = "sheep_stand_right.png";
				} else {
					this.appearance = this.stand_right2;
					this.app_url = "sheep_stand2_right.png";
				}
			} else if (temp.getX() - this.loc_x < 0) {
				if (this.frame == 1) {
					this.appearance = this.stand_left;
					this.app_url = "sheep_stand_left.png";
				} else {
					this.appearance = this.stand_left2;
					this.app_url = "sheep_stand2_left.png";
				}
			}
			this.frame = this.frame % 2 + 1;
			this.loc_x = (int) temp.getX();
			this.loc_y = (int) temp.getY();

			this.before_clock = this.now_clock;
		}
	}

	private void searchRoute() {
		GrassTile nearGrass = Simulator.getInstance().nearGrass(this.loc_x, this.loc_y);
		Point grassLoc = new Point();
		if (nearGrass != null) {
			if (nearGrass.get_x() < this.loc_x) {
				grassLoc = new Point(nearGrass.get_x() + 60, nearGrass.get_y());
				vector = (int) Math.toRadians(180);
			} else {
				grassLoc = new Point(nearGrass.get_x() - 60, nearGrass.get_y());
				vector = (int) Math.toRadians(0);
			}

			if (this.loc_x != grassLoc.getX() && this.loc_y != grassLoc.getY()) {
				this.recursiveSearch(new Point(this.loc_x, this.loc_y), grassLoc);
				this.route.add(grassLoc);
			}
		}
	}

	private void recursiveSearch(Point start, Point dest) {
		Point mid = new Point((int) ((start.getX() + dest.getX()) / 2), (int) ((start.getY() + dest.getY()) / 2));
		Point standardMid = mid;
		int level = 1;
		boolean sign = false;

		while (!Map.getInstance().isValid((int) mid.getX(), (int) mid.getY())) {
			if (sign) {
				mid.setLocation(
						standardMid.getX() + level * (start.getY() - dest.getY())
								/ (start.getX() - dest.getX() + start.getY() - dest.getY()),
						standardMid.getY() + level * (start.getX() - dest.getX())
								/ (start.getX() - dest.getX() + start.getY() - dest.getY()));
				level++;
			} else
				mid.setLocation(
						standardMid.getX() - level * (start.getY() - dest.getY())
								/ (start.getX() - dest.getX() + start.getY() - dest.getY()),
						standardMid.getY() - level * (start.getX() - dest.getX())
								/ (start.getX() - dest.getX() + start.getY() - dest.getY()));
			sign = !sign;
		}

		if (Math.sqrt(Math.pow(start.getX() - mid.getX(), 2)
				+ Math.pow(start.getY() - mid.getY(), 2)) < MainClass.SHEEP_SPEED)
			this.route.add(mid);
		else if (Math.sqrt(
				Math.pow(dest.getX() - mid.getX(), 2) + Math.pow(dest.getY() - mid.getY(), 2)) < MainClass.SHEEP_SPEED)
			this.route.add(mid);
		else {
			recursiveSearch(start, mid);
			this.route.add(mid);
			recursiveSearch(mid, dest);
		}
	}

	protected void drawImage(Graphics2D g) {
		g.drawImage(this.appearance, this.loc_x, this.loc_y, null);
	}

	public int get_x() {
		return this.loc_x;
	}

	public int get_y() {
		return this.loc_y;
	}

	public int get_vector() {
		return this.vector;
	}

	public String get_appURL() {
		return this.app_url;
	}

	public int get_satiety() {
		return this.satiety;
	}

	public int get_lifeLimit() {
		return this.lifeLimit;
	}

	public int get_birth() {
		return this.birth;
	}

	public int get_stamina() {
		return this.stamina;
	}

	public boolean get_sex() {
		return this.sex;
	}

	public int get_count() {
		return this.t_count;
	}

	public boolean get_isExcute() {
		return this.isExcute;
	}

	public int get_sheepState() {
		return this.sheepState;
	}
}
