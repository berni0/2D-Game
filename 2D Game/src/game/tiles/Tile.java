package game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Tile {
		
		//STATIC STUFF HERE
		
		public static Tile[] idList = new Tile[256];
		public static Tile airTile = new AirTile(0);
		public static Tile grassTile = new GrassTile(1);
		
		//CLASS
		
		public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
		
		protected BufferedImage texture;
		protected final int id;
		
		public Tile(BufferedImage texture, int id){
			this.texture = texture;
			this.id = id;
			
			idList[id] = this;
		}
		
		public void tick(){
			
		}
		
		public void render(Graphics g, int x, int y){
			g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
		}
		
		public boolean isSolid(){
			return false;
		}
		
		public int getId(){
			return id;
		}
		
	}
