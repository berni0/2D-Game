package game.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.gfx.ImageLoader;


public abstract class Entity {
	
	protected double x, y;
	protected int width, height;
	protected BufferedImage bImg;
	
	public Entity(double x, double y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public BufferedImage getImg(){
		return bImg;
	}
	
	public void loadImage(String imagePath){
		bImg = ImageLoader.loadImage(imagePath);
	}
	
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

}
