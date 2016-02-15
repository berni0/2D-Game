package game.graphics;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

import game.entities.Entity;

public class ImageLabel extends JLabel{
	private static final long serialVersionUID = 7149237961137318688L;

	private Entity e;
	private Image objectImage;
	private int width, height;
	private GUI gui;
	
	public ImageLabel(Entity pE, GUI pGui) {
		e = pE;
		objectImage = e.getImg();
		width = e.getWidth();
		height = e.getHeight();
		gui = pGui;
	}
	
	public Entity getEntity() {
		return e;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(objectImage, 0, 0, width, height, this);
	}
	
	public void setLocation() {
		super.setLocation((int)e.getX(), transformY(e.getY()));
	}
	
	private int transformY(double pY){
		return (int) (gui.getFrame().getHeight() - pY - e.getHeight() - 60);

	}
}
