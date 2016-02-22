package game.entities;

import game.main.Game;
import game.utilities.Vector2D;

public abstract class Creature extends Entity{

	protected Game game;
	protected Vector2D vel;
	protected Vector2D directionChange = new Vector2D(0, 0, true);

	public Creature(Game g, double x, double y, Vector2D vel, int width, int height) {
		super(x, y, width, height);
		this.vel = vel;
		this.game = g;
	}
	

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void move(double dX, double dY) {	
		x += dX;
		y += dY;
	}

	public Vector2D getVelocity() {
		return vel;
	}

	public void setVelocity(Vector2D vel) {
		this.vel = vel;
	}
	
}
