package game.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import game.entities.Entity;
import game.entities.Creature;
import game.entities.Goomba;
import game.entities.Obstacle;
import game.entities.Player;
import game.gfx.Assets;
import game.gfx.ImageLoader;
import game.gfx.UserInterface;
import game.graphics.GUI;
import game.input.KeyManager;
import game.utilities.CollisionHandler;
import game.utilities.Vector2D;
import game.worlds.World;

public class Game implements Runnable {

	public static Vector2D G = new Vector2D(2.5, 270, false);
	public static Vector2D F = new Vector2D(1.5, 180, false);
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
	public World world;
	private Obstacle leftBoundary;
	private Goomba goomba1;
	private Goomba goomba2;
	private UserInterface UI;

	private CollisionHandler cH;
	

	private int ticks, frames;

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
		UI.tick();
		player.tick();
		cH.tick();

		if (player.getX() > this.width / 2) {
			world.setOffset(player.getX() - this.width / 2);
		}
		else {
			world.setOffset(0);
		}
		goomba1.tick();
		goomba2.tick();
		
//		This needs optimization to remove killed entites from the game entirely - possibly with a List
		if(goomba1.isKilled()){
			cH.removeCreature(goomba1);
		}
		
		if(goomba2.isKilled()){
			cH.removeCreature(goomba2);
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

		world.render(g, this.height);
		UI.render(g);
		double offset = world.getOffset();
		player.render(g, this.height, offset);
		goomba1.render(g, this.height, offset);
		goomba2.render(g, this.height, offset);
//		leftBoundary.render(g, this.height, offset);
		// ground.render(g);

		bs.show();
		g.dispose();
	}

	private void init() {
		display = new GUI(this.width, this.height);
		key = new KeyManager();

		display.getFrame().addKeyListener(key);

		Assets.init();

		world = new World("res/world2.txt");
		player = new Player(this, world.getSpawnX(), world.getSpawnY(), startVec, 500);
		UI = new UserInterface(player, this);
		
		leftBoundary = new Obstacle(-20, 0, 20, 800);
		goomba1 = new Goomba(310, 100);
		goomba2 = new Goomba(300, 110);
		// ground = new Obstacle(this, -50, 0, 3000, 32);
		
		Entity[] worldObstacles = world.getObstacles();
		Creature[] creatures = {player, goomba1, goomba2};

		ArrayList<Entity> entityList = new ArrayList<Entity>();
		entityList.addAll(Arrays.asList(worldObstacles));
		entityList.add(leftBoundary);
		
		ArrayList<Creature> creatureList = new ArrayList<Creature>();
		creatureList.addAll(Arrays.asList(creatures));
		
		cH = new CollisionHandler(entityList, creatureList);
		
//		cH.removeCreature(goomba1);
		
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
