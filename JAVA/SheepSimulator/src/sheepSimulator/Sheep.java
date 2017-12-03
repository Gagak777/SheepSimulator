package sheepSimulator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Queue;

import javax.swing.ImageIcon;

public class Sheep extends Thread {

	private int loc_x; // ����
	private int loc_y; // ����
	private int vector; // ����, Radian����
	private Queue<Point> route;

	private Image appearance; // �� �ܸ�, ����
	private String app_url; // �� �̹��� url
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
		this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_right.png")).getImage();////////////test
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
		this.loc_x = 1000;////////////////test
		this.loc_y = 500;/////////////////test
	}

	public Sheep(String appearance, int loc_x, int loc_y, int birth, boolean sex, int lifeLimit, int satiety,
			int stamina, int sheepState, boolean isExcute, int count, int vector) {
		this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/" + appearance)).getImage();
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

			if ((int) (Math.random() * 100) < 3) {
				this.cry();
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
				this.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_right.png")).getImage();/**/
			else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_left.png")).getImage();
			
			this.before_clock = this.now_clock;
		}

		if (this.t_count == 0)
			this.isExcute = false;
	}

	private void walk() {
		if (this.isExcute == false) {
			this.isExcute = true;
			this.t_count = (int) (Math.random() * 2 + 10);
			this.vector = (int)Math.toRadians(Math.random() * 360); /*���Ȱ����� ����*/
			this.before_clock = System.nanoTime();
		}

		this.now_clock = System.nanoTime();

		if (this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {

			while (!Map.getInstance().isValid(this.loc_x + (int) Math.cos(this.vector) * MainClass.BASE_SPEED, /**/
					this.loc_y + (int) Math.sin(this.vector) * MainClass.BASE_SPEED)) {
				this.vector = (int)Math.toRadians(Math.random() * 360); /*���Ȱ����� ����*/
			}

			this.loc_x += (int) (Math.cos(this.vector) * MainClass.SHEEP_SPEED); /**/
			this.loc_y += (int) (Math.sin(this.vector) * MainClass.SHEEP_SPEED);

			this.t_count--;
			
			// ��� �ٲٱ�
			if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_right.png")).getImage();/**/
			else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_left.png")).getImage();		
			
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
			this.searchRoute();
		}

		this.now_clock = System.nanoTime();
		if (Simulator.getInstance().isGrass(this.loc_x, this.loc_y) && this.now_clock
				- this.before_clock > MainClass.SECOND / (MainClass.BASE_SPEED * MainClass.simulationSpeed)) {
			
			// �� �Դ� ���
			if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat_right.png")).getImage();/**/
			else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_eat_left.png")).getImage();
			
		} else if (this.route.size() != 0 && this.now_clock - this.before_clock > MainClass.SECOND
				/ (MainClass.BASE_SPEED * MainClass.simulationSpeed))
			this.moveTo();
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
			if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep1_right.png")).getImage();/**/
			else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_sleep1_left.png")).getImage();
			
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
		if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
			this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_love_right.png")).getImage();/**/
		else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
			this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_love_left.png")).getImage();
	}

	private void death() { // �ణ �ٸ���
		// 3������ �״¸��
		if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
			this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_dead_right.png")).getImage();/**/
		else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
			this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_dead_left.png")).getImage();
	}

	private boolean isDeath() {
		if ((MainClass.simulateYear - birth) < lifeLimit && this.satiety > 0)
			return false;
		else
			return true;
	}

	private void moveTo() {
		if (route.poll().getX() == this.get_x() && route.poll().getY() == this.get_y()) {
			route.remove();
		} else {
			this.vector = (int) (Math.atan(this.loc_y - route.poll().getY() / this.loc_x - route.poll().getX()));

			this.loc_x += (int) Math.cos(this.vector);
			this.loc_y += (int) Math.sin(this.vector);

			// ��� �ٲٱ�
			if((Math.toDegrees(this.vector) >= 0 && Math.toDegrees(this.vector) < 90) || (Math.toDegrees(this.vector) >= 270 && Math.toDegrees(this.vector) < 360))
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_right.png")).getImage();/**/
			else if(Math.toDegrees(this.vector) >= 90  && Math.toDegrees(this.vector) < 270)
				this.appearance = new ImageIcon(MainClass.class.getResource("../res/image/sheep_stand_left.png")).getImage();
			
			this.before_clock = this.now_clock;
		}
	}

	private void searchRoute() {
		GrassTile nearGrass = Simulator.getInstance().nearGrass(this.loc_x, this.loc_y);
		Point grassLoc = new Point(nearGrass.get_x(), nearGrass.get_y());

		if (nearGrass != null) {
			this.recursiveSearch(new Point(this.loc_x, this.loc_y), grassLoc);
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

		if (start.getX() / MainClass.SHEEP_SPEED == mid.getX() / MainClass.SHEEP_SPEED
				&& start.getY() / MainClass.SHEEP_SPEED == mid.getY() / MainClass.SHEEP_SPEED)
			this.route.add(mid);
		else if (dest.getX() / MainClass.SHEEP_SPEED == mid.getX() / MainClass.SHEEP_SPEED
				&& dest.getY() / MainClass.SHEEP_SPEED == mid.getY() / MainClass.SHEEP_SPEED)
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
