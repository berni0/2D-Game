package game.main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import game.entities.Entity;
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
	private UserInterface UI;

	private CollisionHandler cH;

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

		while (running) {
			now = System.nanoTime();
			deltaR += (now - lastTime) / timePerRender;
			deltaT += (now - lastTime) / timePerTick;
			lastTime = now;

			if (deltaR >= 1) {
				render();
				deltaR--;
			}

			if (deltaT >= 1) {
				tick();
				deltaT--;
			}

		}

		stop();
	}

	private void tick() {
		key.tick();
		player.tick();
		cH.tick();
		if (player.getX() > this.width / 2) {
			world.setOffset(player.getX() - this.width / 2);
		}
		else {
			
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
		leftBoundary.render(g, this.height, offset);
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
		UI = new UserInterface(player);
		
		leftBoundary = new Obstacle(-20, 0, 20, 800);
		// ground = new Obstacle(this, -50, 0, 3000, 32);

		Entity[] test = world.getObstacles();
		Entity[] ent = {player, leftBoundary};

		ArrayList<Entity> entities = new ArrayList<Entity>();
		entities.addAll(Arrays.asList(ent));
		entities.addAll(Arrays.asList(test));

		cH = new CollisionHandler(entities);

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

}
