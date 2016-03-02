package game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

	protected double x, y;
	protected int width, height;
	protected boolean isStatic;

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Entity(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void tick();

	public abstract void render(Graphics g, int gameHeight, double offset);


	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	public abstract void collision(Entity e, boolean invokedByCreature, int direction); //1 = top, -1 = bottom; 2 = right, -2 = left

}
