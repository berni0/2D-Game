package game.gfx;

import java.awt.image.BufferedImage;

public class Animation {
	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frame;
	
	public Animation(int speed, BufferedImage[] frame) {
		this.speed = speed;
		this.frame = frame;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void tick(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer >= speed){
			index++;
			timer=0;
			if(index >= frame.length){
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return frame[index];
	}
}
