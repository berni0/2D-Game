package game.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	private static final int width = 32, height = 32;
	
	public static BufferedImage player,grass, air;
	public static BufferedImage playerRight[], playerLeft[], playerStand[];
	
	
	public static void init(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/SpriteSheet.png"));
		SpriteSheet terrain = new SpriteSheet(ImageLoader.loadImage("/terrain.png"));
		grass = terrain.crop(0, 0, width, height);
		player = sheet.crop(0, 0, width, height);
		playerStand = new BufferedImage[1];
		playerStand[0] = sheet.crop(32, 0, width, height);
		
		playerRight = new BufferedImage[4];
		for(int i=0; i<playerRight.length; i++){
			playerRight[i] = sheet.crop(8*32+i*32, 0, width, height);
		}
		
		playerLeft = new BufferedImage[4];
		for(int i=0; i<playerLeft.length; i++){
			playerLeft[i] = sheet.crop(15*32-i*32, 0, width, height);
		}
		
	}
	
}
