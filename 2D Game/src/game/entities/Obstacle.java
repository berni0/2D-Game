package game.entities;

import java.awt.Graphics;

import game.main.Game;

public class Obstacle extends Entity {
	
	Game game;

	public Obstacle(Game g, double x, double y, int width, int height) {
		super(x, y, width, height);
		this.game = g;
		isStatic = true;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawRect((int)x, (int)(game.getHeight() - this.y - this.height - Game.topBarHeight) , width, height);
	}

}
