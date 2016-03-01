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
	public void render(Graphics g, double offset) {
		g.drawRect((int)x - (int) offset, Game.transformY(y, height) , width, height);
	}
	
	//Test
}