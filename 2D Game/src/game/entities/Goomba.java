package game.entities;

import java.awt.Graphics;

import game.main.Game;
import game.utilities.Vector2D;

public class Goomba extends Creature {

	private static int width = 16;
	private static int height = 16;

	public Goomba(double x, double y) {
		super(x, y, new Vector2D(0, 0, true), 0, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (!hasGround()) {
			vel = Vector2D.add(vel, Game.G);
		}
		
		move(vel.getX() / 100, vel.getY() / 100);
	}

	@Override
	public void render(Graphics g, int gameHeight, double offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(Creature c) {
		// TODO Auto-generated method stub
		switch (c.getClass().getName()) {
		case "game.entities.Goomba":
			vel.setX(-vel.getX());
			break;
		case "game.entities.Player":
			System.out.println("I kill you");
			break;
		default: System.out.println("class unknown");
			break;
			
		}
		c.collision(this);
	}

}
