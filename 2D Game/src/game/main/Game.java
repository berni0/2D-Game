package game.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import game.gfx.Assets;
import game.gfx.ImageLoader;
import game.graphics.GUI;
import game.input.KeyManager;
import game.utilities.Vector2D;
import game.entities.Player;

public class Game implements Runnable{
	
	public static Vector2D G = new Vector2D(0.5, 270, false);
	public static Vector2D F = new Vector2D(1.0, 180, false);
	public static int topBarHeight = 25;
	public static Vector2D startVec = new Vector2D(0, 0, false);
	
	int x = 0;
	
	private int width, height;
	private boolean running = false;
	private int tps, fps;
	
	
	private GUI display;
	private BufferStrategy bs;
	private Graphics g;
	private Player player;
	
	private Thread thread;
	
	private KeyManager key;
	
	private BufferedImage backgroungImg;
	
	public Game(int width, int height, int fps, int tps){
		this.height = height;
		this.width = width;
		this.fps = fps;
		this.tps = tps;
	}
	
	@Override
	public void run() {
		init();
		 
		double timePerRender = 1000000000 / fps;
		double timePerTick = 1000000000 / tps;
		double deltaR = 0;
		double deltaT = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while(running){
			now = System.nanoTime();
			deltaR += (now - lastTime) / timePerRender;
			deltaT += (now - lastTime) / timePerTick;
			lastTime = now;
			
			if(deltaR >= 1){
				render();
				deltaR--;
			}
			
			if(deltaT >= 1){
				tick();
				deltaT --;
			}
			
		}
		
		stop();
	}

	private void tick() {
		key.tick();
		player.tick();
		}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0,0, this.width, this.height);
		g.drawImage(backgroungImg, 0, 0, width, height, null);
		
		player.render(g);
		
		bs.show();
		g.dispose();
	}
	
	private void init(){
		display = new GUI(this.width, this.height);
		key = new KeyManager();
		display.getFrame().addKeyListener(key);
		Assets.init();
		player = new Player(this,50,50, startVec , 100);
		backgroungImg = ImageLoader.loadImage("/Landschaft_1.jpg");
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public KeyManager getKey() {
		return key;
	}

	public void setKey(KeyManager key) {
		this.key = key;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
