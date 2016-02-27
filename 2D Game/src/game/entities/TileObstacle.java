package game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.main.Game;

public class TileObstacle extends Entity {

	private Game g;
	
	public TileObstacle(Game g, double x, double y, int width, int height) {
		super(x, y, width, height);
		this.g = g;
		isStatic = true;
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect((int)x, (int)(this.y) , width, height);
	}

	@Override
	public Rectangle getBounds(){
		return new Rectangle((int)this.x, (int)(g.getHeight() - this.y - this.height - Game.topBarHeight), width, height);
	}
}
