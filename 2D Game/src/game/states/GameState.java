package game.states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import game.entities.Entity;
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
	private UserInterface UI;

	private CollisionHandler cH;
	
	public GameState(Game g) {
		super(g);
		world = new World("res/world2.txt");
		player = new Player(getGame(), world.getSpawnX(), world.getSpawnY(), new Vector2D(0, 0, false), 500);
		UI = new UserInterface(player, getGame());
		
		leftBoundary = new Obstacle(-20, 0, 20, 800);


		Entity[] test = world.getObstacles();
		Entity[] ent = {player, leftBoundary};
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		entities.addAll(Arrays.asList(ent));
		entities.addAll(Arrays.asList(test));
		
		cH = new CollisionHandler(entities, null);
	}

	@Override
	public void tick() {
		UI.tick();
		player.tick();
		cH.tick();

		if (player.getX() > getGame().getWidth() / 2) {
			world.setOffset(player.getX() - getGame().getWidth() / 2);
		}
		else {
			
		}
	}

	@Override
	public void render(Graphics g) {
		world.render(g, getGame().getHeight());
		UI.render(g);
		double offset = world.getOffset();
		player.render(g, getGame().getHeight(), offset);
		leftBoundary.render(g, getGame().getHeight(), offset);
	}

	public static World getWorld() {
		return world;
	}

}
