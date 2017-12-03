package sheepSimulator;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StartMenu {

	private Image simulatorImage;
	private Graphics simulatorGraphic;

	private Image backGround;

	private static StartMenu Instance = null;
	private User loginedUser;

	private Music backGroundMusic1;
	private Music backGroundMusic2;
	
	private ExitButton exitButton;
	private LoginButton loginButton;
	private LogOutButton logoutButton;
	private SettingButton settingButton;
	
	private Simulator simulator;
	
	private StartMenu() {
		this.backGroundMusic1 = new Music("BackGroundMusic.mp3", true);
		this.backGroundMusic2 = new Music("BackGroundMusic_Cry.mp3", true);
		
		this.backGround = new ImageIcon(MainClass.class.getResource("../res/image/backGround.png")).getImage();
		
		this.loginButton = new LoginButton();
		this.exitButton = new ExitButton();

		ScreenGraphic.getInstance().add(loginButton);
		ScreenGraphic.getInstance().add(exitButton);

		this.backGroundMusic1.start();
		this.backGroundMusic2.start();
		
		this.simulator = null;
		
		ScreenGraphic.getInstance().setBackGround(this.backGround);
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

		Simulator.getInstance().setInfo(DataBase.getInstance().getSimulator(loginedUser));
		Simulator.getInstance().excute();
		
		this.excute();
	}

	public void setting() {

	}
}
