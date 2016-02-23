package game.utilities;

import java.awt.Point;
import java.awt.Rectangle;

import game.entities.Creature;
import game.entities.Entity;
import game.main.Game;

public class CollisionHandler {
	private Game game;
	private Entity[] entities;

	public CollisionHandler(Game game, Entity[] entities) {
		this.game = game;
		this.entities = entities;
	}

	public void tick() {
		for (int i = 0; i < entities.length; i++) {
			for (int j = i + 1; j < entities.length; j++) {
				if (!entities[i].isStatic() || !entities[j].isStatic()) {
					checkCollision(entities[i], entities[j]);
				}
			}
		}
	}

	private void checkCollision(Entity e1, Entity e2) {
		// TODO Auto-generated method stub
		Rectangle staticRect;
		Rectangle mobileRect;
		Creature c;
		if (e1.isStatic()) {
			staticRect = e1.getBounds();
			mobileRect = e2.getBounds();
			c = (Creature) e2;
		} else {
			staticRect = e2.getBounds();
			mobileRect = e1.getBounds();
			c = (Creature) e1;
		}
		if (e1.getBounds().intersects(e2.getBounds())) {
			if (mobileRect.getMinY() <= staticRect.getMaxY() && mobileRect.getMinY() > staticRect.getMaxY() - 5) {
				if (c.getVelocity().getY() < 0)
					c.getVelocity().setY(0);
				c.moveTo(c.getX(), staticRect.getMaxY());
				c.setHasGround(true);
				c.setJumping(false);
				if (c.isScheduledJump()) {
					c.setScheduledJump(false);
					c.getVelocity().setY(0);
					c.jump();
				}
			} else
				if (mobileRect.getMaxY() >= staticRect.getMinY() && mobileRect.getMaxY() < staticRect.getMinY() + 5) {
				c.getVelocity().setY(0);
				c.moveTo(c.getX(), staticRect.getMinY() - c.getHeight());
				if (c.getVelocity().getY() > 0)
					c.getVelocity().setY(0);
			} else if (mobileRect.getMinX() <= staticRect.getMaxX()
					&& mobileRect.getMinX() > staticRect.getMaxX() - 5) {
				c.getVelocity().setX(0);
				c.moveTo(staticRect.getMaxX() - 5, c.getY());
			} else
				if (mobileRect.getMaxX() >= staticRect.getMinX() && mobileRect.getMaxX() < staticRect.getMinX() + 5) {
				c.getVelocity().setX(0);
				c.moveTo(staticRect.getMinX() + 5 - c.getWidth(), c.getY());
			}
		} else {
			c.setHasGround(false);
		}
	}

}
