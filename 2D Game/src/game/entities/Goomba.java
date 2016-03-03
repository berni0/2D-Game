package game.entities;

import java.awt.Graphics;

import game.gfx.Animation;
import game.gfx.Assets;
import game.main.Game;
import game.utilities.Vector2D;

public class Goomba extends Creature {

	private Animation animation = new Animation(100, Assets.goombaWalk);

	private boolean killed;
	public boolean isKilled() {
		return killed;
	}

	private int ticksSinceDeath = 0;
	
	public Goomba(double x, double y) {
		super(x, y, new Vector2D(-25, 0, true), 32, 32, 0);
		isStatic = false;
		killed = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(killed){
			if(ticksSinceDeath <= 2*32){
				if(ticksSinceDeath % 2  == 0){
					height--;
				}
				if(ticksSinceDeath % 3 == 0){
					width++;
					setX(getX()-0.5);
				}
				ticksSinceDeath++;
			} else if (ticksSinceDeath <= 3*32){
				height = 3;
				ticksSinceDeath++;
			} else {
				height = 0;
			}
			return;
		}
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
	public void collision(Entity e, boolean invokedByCreature, int direction) {
		// TODO Auto-generated method stub
		switch (e.getClass().getName()) {
		case "game.entities.Obstacle":
			switch (direction) {
			case -1:
				if (vel.getY() < 0) {
					vel.setY(0);
					setHasGround(true);
					setJumping(false);
				}
				moveTo(getX(), e.getBounds().getMaxY() - 0.1);
				break;
			case 1:
				moveTo(getX(), e.getBounds().getMinY() - getHeight());
				if (vel.getY() > 0)
					vel.setY(0);
				break;
			case -2:
				vel.setX(-vel.getX());
				moveTo(e.getBounds().getMaxX(), getY());
				break;
			case 2:
				vel.setX(-vel.getX());
				moveTo(e.getBounds().getMinX() - getWidth(), getY());
				break;
			default:
				System.out.println("Wie bist du hier hin gekommen?");
			}
			break;
		case "game.entities.Goomba":
			switch(direction){
			case -2: case 2:
				vel.setX(-vel.getX());
				break;
			case -1:
				if (vel.getY() < 0) {
					vel.setY(0);
					setHasGround(true);
					setJumping(false);
				}
				moveTo(getX(), e.getBounds().getMaxY() - 0.1);
				break;
			}
			break;
		case "game.entities.Player":
			if (direction == 1) {
				System.out.println("Player killed goomba!");
				killed = true;
			} else {
				System.out.println("I kill you");
			}
			break;
		default:
			System.out.println("class unknown");
			break;

		}
		if (!invokedByCreature && e.getClass().getName() != "game.entities.Obstacle") {
			((Creature) e).collision(this, true, -direction);
		}
	}

}
