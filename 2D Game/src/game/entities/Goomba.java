package game.entities;

import java.awt.Graphics;

import game.gfx.Animation;
import game.gfx.Assets;
import game.main.Game;
import game.utilities.Vector2D;

public class Goomba extends Creature {

	private static int width = 32;
	private static int height = 32;

	private Animation animation = new Animation(100, Assets.goombaWalk);

	public Goomba(double x, double y) {
		super(x, y, new Vector2D(-15, 0, true), width, height, 0);
		isStatic = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animation.tick();
		if (!hasGround()) {
			vel = Vector2D.add(vel, Game.G);
		}

		move(vel.getX() / 100, vel.getY() / 100);
	}

	@Override
	public void render(Graphics g, int gameHeight, double offset) {
		// TODO Auto-generated method stub
		g.drawImage(animation.getCurrentFrame(), (int) this.x - (int) offset,
				(int) (gameHeight - Game.topBarHeight - height - y), width, height, null);
	}

	@Override
	public void collision(Creature c, boolean invokedByCreature) {
		// TODO Auto-generated method stub
		switch (c.getClass().getName()) {
		case "game.entities.Goomba":
			vel.setX(-vel.getX());
			break;
		case "game.entities.Player":
			System.out.println("I kill you");
			break;
		default:
			System.out.println("class unknown");
			break;

		}
		if (!invokedByCreature) {
			c.collision(this, true);
		}
	}

}
