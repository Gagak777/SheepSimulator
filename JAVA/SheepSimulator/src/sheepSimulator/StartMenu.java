package sheepSimulator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class StartMenu implements ActionListener{

	private String inputId;
	private String inputPw;
	
	private Image backGround;

	private static StartMenu Instance = null;
	private User loginedUser;

	private Music backGroundMusic1;
	private Music backGroundMusic2;
	
	private Font textFont;
	
	private ImageIcon TextFieldImage;
	
	private JLabel idImageLabel;
	private ImageIcon idImage;
	private JTextField idTextField;
	private JLabel idTextFieldLabel;
	
	private JLabel pwImageLabel;
	private ImageIcon pwImage;
	private JPasswordField pwTextField;
	private JLabel pwTextFieldLabel;
	
	private ExitButton exitButton;
	private LoginButton loginButton;
	private LogoutButton logoutButton;
	private SettingButton settingButton;
	private SimulateButton simulateButton;
	
	private Simulator simulator;
	
	private StartMenu() {
		this.backGroundMusic1 = new Music("BackGroundMusic.mp3", true);
		this.backGroundMusic2 = new Music("BackGroundMusic_Cry.mp3", true);
		
		this.backGround = new ImageIcon(MainClass.class.getResource("../res/image/backGround.png")).getImage();
		
		this.loginButton = new LoginButton();
		this.exitButton = new ExitButton();
		
		this.logoutButton = new LogoutButton();
		this.logoutButton.setVisible(false);
		
		this.settingButton = new SettingButton();
		this.settingButton.setVisible(false);
		
		this.simulateButton = new SimulateButton();
		this.simulateButton.setVisible(false);
		
		setLoginLabel();
		setLoginTextField();
		
		ScreenGraphic.getInstance().add(loginButton);
		ScreenGraphic.getInstance().add(logoutButton);
		ScreenGraphic.getInstance().add(simulateButton);
		ScreenGraphic.getInstance().add(settingButton);
		ScreenGraphic.getInstance().add(exitButton);
		ScreenGraphic.getInstance().add(idTextFieldLabel);
		ScreenGraphic.getInstance().add(pwTextFieldLabel);
		ScreenGraphic.getInstance().add(idImageLabel);
		ScreenGraphic.getInstance().add(pwImageLabel);
		
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
		setLoginCompVisible(false);
		setMainCompVisible(true);
		/* 로그인 부분 (나중에 지우시오)
		loginedUser = DataBase.getInstance().identUser(inputId, inputPw);
		if(loginedUser != null) {
			//컴포넌트 교체
			setLoginCompVisible(false);
			setMainCompVisible(true);
		}
		else {
			inputId = "";
			inputPw = "";
			idTextField.setText("");
			pwTextField.setText("");
		}
		*/
	}

	public void logout() {
		loginedUser = null;
		//컴포넌트 교체
		setMainCompVisible(false);
		setLoginCompVisible(true);
	}
	
	public void simulate() {
		this.backGroundMusic1.close();
		this.backGroundMusic2.close();
		//배경화면 및 컴포넌트 전부 교체
		setMainCompVisible(false);
		Simulator.getInstance().setInfo(DataBase.getInstance().getSimulator(loginedUser));
		Simulator.getInstance().excute();
		
		this.excute();
	}

	public void setting() {
		
	}

	private void setLoginTextField() {

		this.textFont = new Font("default",Font.PLAIN,70);
		this.TextFieldImage = new ImageIcon(MainClass.class.getResource("../res/image/blank.png"));

		this.idTextField = new JTextField(){
			@Override public void setBorder(Border border) {}
			};
		this.idTextField.setOpaque(false);
		this.idTextField.setFont(textFont);
		this.idTextField.addActionListener(this);
		
		this.idTextFieldLabel = new JLabel(TextFieldImage);
		this.idTextFieldLabel.setIconTextGap(0);
		this.idTextFieldLabel.setText(null);
		this.idTextFieldLabel.setSize(TextFieldImage.getImage().getWidth(null), TextFieldImage.getImage().getHeight(null));
		this.idTextFieldLabel.setLocation(607,410);
		this.idTextFieldLabel.setLayout(new BorderLayout());
		this.idTextFieldLabel.add(idTextField);
		
		this.pwTextField = new JPasswordField(){
			@Override public void setBorder(Border border) {}
			};
		this.pwTextField.setOpaque(false);
		this.pwTextField.setFont(textFont);
		this.pwTextField.addActionListener(this);
		
		this.pwTextFieldLabel = new JLabel(TextFieldImage);
		this.pwTextFieldLabel.setIconTextGap(0);
		this.pwTextFieldLabel.setText(null);
		this.pwTextFieldLabel.setSize(TextFieldImage.getImage().getWidth(null), TextFieldImage.getImage().getHeight(null));
		this.pwTextFieldLabel.setLocation(607,542);
		this.pwTextFieldLabel.setLayout(new BorderLayout());
		this.pwTextFieldLabel.add(pwTextField);
	}
	
	private void setLoginLabel() {
		this.idImage = new ImageIcon(MainClass.class.getResource("../res/image/id.png"));
		this.idImageLabel = new JLabel(idImage);
		this.idImageLabel.setSize(idImage.getImage().getWidth(null), idImage.getImage().getHeight(null));
		this.idImageLabel.setLocation(309,410);
		
		this.pwImage = new ImageIcon(MainClass.class.getResource("../res/image/pw.png"));
		this.pwImageLabel = new JLabel(pwImage);
		this.pwImageLabel.setSize(pwImage.getImage().getWidth(null), pwImage.getImage().getHeight(null));
		this.pwImageLabel.setLocation(309,542);
	}
	
	private void setMainCompVisible(boolean flag) {
		simulateButton.setVisible(flag);
		settingButton.setVisible(flag);
		logoutButton.setVisible(flag);
	}
	
	private void setLoginCompVisible(boolean flag)
	{
		loginButton.setVisible(flag);
		exitButton.setVisible(flag);
		idTextFieldLabel.setVisible(flag);
		pwTextFieldLabel.setVisible(flag);
		idImageLabel.setVisible(flag);
		pwImageLabel.setVisible(flag);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object obj = event.getSource();
		
		if(obj == idTextField) {
			pwTextField.requestFocus();		
			}
		if(obj == pwTextField){
			loginButton.requestFocus();
		}
		if(obj == loginButton) {
			inputId = idTextField.getText();
			inputPw = String.valueOf(pwTextField.getPassword());
			login();
		}
	}
}
