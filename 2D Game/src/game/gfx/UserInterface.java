package game.gfx;

import java.awt.Graphics;

import game.entities.Player;
import game.main.Game;

public class UserInterface {

	private boolean active = false;
	private Player p;
	private Game game;
	
	public UserInterface(Player p, Game g) {
		this.p = p;
		this.game = g;
	}
	
	public void toggleUI(){
		active = !active;
	}
	
	public void tick(){
		if(game.getKey().toggleDebugUI){
			toggleUI();
		}
	}
	
	public void render(Graphics g){
		if(active){
		g.drawString("FPS: " + game.getFrames() + " TPS: " + game.getTicks(), 10 , 10);
		g.drawString("X: " + (int)p.getX() +" " + "Y: " + (int)p.getY() ,10 , 22 );
		g.drawString("Vel:" + (int) p.getVelocity().getMagnitude(), 10, 34);
		}
	}

}
