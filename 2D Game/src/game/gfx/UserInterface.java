package game.gfx;

import java.awt.Graphics;

import game.entities.Player;

public class UserInterface {

	private boolean active = false;
	private Player p;
	
	public UserInterface(Player p) {
		this.p = p;
	}
	
	public void toggleUI(){
		if (active){
			active =false;
		}else{
			active = true;
		}
	}
	
	public void tick(){}
	
	public void render(Graphics g){
		g.drawString("X: " + (int)p.getX() +" " + "Y: " + (int)p.getY() ,10 ,10 );
		g.drawString("Vel:" + (int) p.getVelocity().getMagnitude(), 10, 20);
	}

}
