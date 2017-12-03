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
		//�⺻ ���ȭ�� �� ������Ʈ �ѱ�
		//�⺻���� �ѱ�
		//�α��λ��� ȭ�� ����ֱ�
	}

	public void login() {

		// ���� üũ�ϱ�
		
		if(loginedUser != null) {
			//������Ʈ ��ü
		}
		else {
			//�α��� ����
		}
	}

	public void logout() {
		loginedUser = null;
		//������Ʈ ��ü
	}
	
	public void simulate() {
		this.backGroundMusic1.close();
		this.backGroundMusic2.close();
		//���ȭ�� �� ������Ʈ ���� ��ü

		Simulator.getInstance().setInfo(DataBase.getInstance().getSimulator(loginedUser));
		Simulator.getInstance().excute();
		
		this.excute();
	}

	public void setting() {

	}
}
