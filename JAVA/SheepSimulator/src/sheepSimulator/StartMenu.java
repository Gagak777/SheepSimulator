package sheepSimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartMenu extends JFrame {

	private Image simulatorImage;
	private Graphics simulatorGraphic;

	private Image backGround;

	private static StartMenu Instance = null;
	private User loginedUser;

	private Music backGroundMusic1;
	private Music backGroundMusic2;
	
	private JPanel titlePanel; // 양 시뮬레이터 제목 패널
	private ExitButton exitButton;
	private LoginButton loginButton;
	private LogOutButton logoutButton;
	private SettingButton settingButton;
	
	private Simulator simulator;
	
	private StartMenu() {
		
		this.backGroundMusic1 = new Music("BackGroundMusic.mp3", true);
		this.backGroundMusic2 = new Music("BackGroundMusic_Cry.mp3", true);
		this.setTitle("Sheep Simulator");
		this.setUndecorated(true);
		this.setSize(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setLayout(null);
		
		this.backGround = new ImageIcon(MainClass.class.getResource("../res/image/backGround.png")).getImage();
		
		this.loginButton = new LoginButton();
		this.exitButton = new ExitButton();

		this.add(loginButton);
		this.add(exitButton);

		this.backGroundMusic1.start();
		this.backGroundMusic2.start();
		
		this.simulator = null;
	}

	public static StartMenu getInstance() {
		if (Instance == null)
			Instance = new StartMenu();
		return Instance;
	}

	public void excute() {
		this.simulator.close();	
		//기본 배경화면 및 컴포넌트 켜기
		//기본음악 켜기
		//로그인상태 화면 띄워주기
	}

	public void paint(Graphics g) {
		simulatorImage = createImage(MainClass.S_WIDTH, MainClass.S_HEIGHT);
		simulatorGraphic = simulatorImage.getGraphics();
		imageDraw(simulatorGraphic);
		g.drawImage(simulatorImage, 0, 0, null);
	}

	public void imageDraw(Graphics g) {
		g.drawImage(backGround, 0, 0, null);
		this.paintComponents(g);
		this.repaint();
	}

	public void login() {
		
		// 유저 체크하기
		
		if(loginedUser != null) {
			//컴포넌트 교체
		}
		else {
			//로그인 실패
		}
	}

	public void logout() {
		loginedUser = null;
		//컴포넌트 교체
	}

	public void simulate() {
		this.backGroundMusic1.close();
		this.backGroundMusic2.close();
		//배경화면 및 컴포넌트 전부 교체

		this.simulator = DataBase.getInstance().getSimulator(loginedUser);
		this.simulator.excute();
	}

	public void setting() {

	}
}
