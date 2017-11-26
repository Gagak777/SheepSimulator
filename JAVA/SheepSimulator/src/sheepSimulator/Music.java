package sheepSimulator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;


public class Music extends Thread {
	
	private Player player;
	private boolean loop;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	Music(String name){
		this(name, false);
	}
	
	Music(String name, boolean loop){
			this.loop = loop;
			try {
			this.file = new File(MainClass.class.getResource("../res/music/" + name).toURI());
			this.fis = new FileInputStream(file);
			this.bis = new BufferedInputStream(fis);
			this.player = new Player(bis);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		this.loop = false;
		this.player.close();
		this.interrupt();
	}
	
	@Override
	public void run() {
		try {
			do {
				this.player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while(loop);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
