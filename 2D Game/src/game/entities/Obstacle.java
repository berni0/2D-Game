package game.entities;

import java.awt.Graphics;

import game.main.Game;

public class Obstacle extends Entity {

	public Obstacle(double x, double y, int width, int height) {
		super(x, y, width, height);
		isStatic = true;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g, int gameHeight, double offset) {
		g.drawRect((int) x - (int) offset, (int) (gameHeight - Game.topBarHeight - height - y), width, height);
	}

	@Override
	public void collision(Entity e, boolean invokedByCreature, int direction) {
		// TODO Auto-generated method stub
		if(!invokedByCreature){
			e.collision(this, true, -direction);
		}
	}

	// Test
}