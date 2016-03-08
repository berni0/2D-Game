package game.states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import game.entities.Creature;
import game.entities.Entity;
import game.entities.Goomba;
import game.entities.Obstacle;
import game.entities.Player;
import game.gfx.UserInterface;
import game.main.Game;
import game.utilities.CollisionHandler;
import game.utilities.Vector2D;
import game.worlds.World;

public class GameState extends State {

	private Player player;
	public static World world;
	private Obstacle leftBoundary;
	private Goomba goomba1;
	private Goomba goomba2;
	private UserInterface UI;

	private CollisionHandler cH;

	public GameState(Game g) {
		super(g);
		world = new World("res/world2.txt");
		player = new Player(getGame(), world.getSpawnX(), world.getSpawnY(), new Vector2D(0, 0, false), 500);
		UI = new UserInterface(player, getGame());

		leftBoundary = new Obstacle(-20, 0, 20, 800);
		goomba1 = new Goomba(310, 100);
		goomba2 = new Goomba(300, 110);
		// ground = new Obstacle(this, -50, 0, 3000, 32);

		Entity[] worldObstacles = world.getObstacles();
		Creature[] creatures = { player, goomba1, goomba2 };

		ArrayList<Entity> entityList = new ArrayList<Entity>();
		entityList.addAll(Arrays.asList(worldObstacles));
		entityList.add(leftBoundary);

		ArrayList<Creature> creatureList = new ArrayList<Creature>();
		creatureList.addAll(Arrays.asList(creatures));

		cH = new CollisionHandler(entityList, creatureList);
	}

	@Override
	public void tick() {
		UI.tick();
		player.tick();
		cH.tick();

		if (player.getX() > getGame().getWidth() / 2) {
			world.setOffset(player.getX() - getGame().getWidth() / 2);
		} else {

		}
	}

	@Override
	public void render(Graphics g) {
		world.render(g, getGame().getHeight());
		UI.render(g);
		double offset = world.getOffset();
		player.render(g, getGame().getHeight(), offset);
		leftBoundary.render(g, getGame().getHeight(), offset);
		goomba1.render(g, getGame().getHeight(), offset);
		goomba2.render(g, getGame().getHeight(), offset);
		}

	public static World getWorld() {
		return world;
	}

}
