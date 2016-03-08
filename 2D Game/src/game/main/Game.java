package game.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import game.gfx.Assets;
import game.gfx.ImageLoader;
import game.graphics.GUI;
import game.input.KeyManager;
import game.states.GameState;
import game.states.State;
import game.utilities.Vector2D;

public class Game implements Runnable {

	public static Vector2D G = new Vector2D(2.5, 270, false);
	public static Vector2D F = new Vector2D(1.5, 180, false);
	public static int topBarHeight = 25;


	private int width, height;
	private boolean running = false;
	private int tps, fps;
	private int ticks, frames;

	private GUI display;
	private BufferStrategy bs;
	private Graphics g;

	//States
	private GameState gameState;
	

	

	private Thread thread;

	private KeyManager key;

	private BufferedImage backgroungImg;

	public Game(int width, int height, int fps, int tps) {
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
		
		int currentF=0, currentT=0;
		long timer=0;
		
		

		while (running) {
			now = System.nanoTime();
			deltaR += (now - lastTime) / timePerRender;
			deltaT += (now - lastTime) / timePerTick;
			timer += now-lastTime;
			lastTime = now;

			if (deltaR >= 1) {
				render();
				currentF++;
				deltaR--;
			}

			if (deltaT >= 1) {
				tick();
				currentT++;
				deltaT--;
			}
			
			if(timer >= 1000000000){
				frames = currentF;
				ticks = currentT;
				currentF = 0;
				currentT = 0;
				timer = 0;
			}
		}

		stop();
	}

	private void tick() {
		key.tick();
		if(State.getState()!=null){
			State.getState().tick();
		}

	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();
		g.clearRect(0, 0, this.width, this.height);
		g.drawImage(backgroungImg, 0, 0, width, height, null);


		
		if(State.getState()!=null){
			State.getState().render(g);
		}
		

		bs.show();
		g.dispose();
	}

	private void init() {
		Assets.init();
		display = new GUI(this.width, this.height);
		key = new KeyManager();

		gameState = new GameState(this);
		State.setState(gameState);
		
		display.getFrame().addKeyListener(key);







		backgroungImg = ImageLoader.loadImage("/Background.png");

	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
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

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}
	
}
