package game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.main.Game;


public class Tile {
		
		//STATIC STUFF HERE
		
		public static Tile[] idList = new Tile[256];
		public static Tile airTile = new AirTile(0);
		public static Tile grassTile = new GrassTile(1);
		public static Tile dirtTile = new DirtTile(2);
		public static Tile coinTile = new CoinTile(3);
		
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
		
		public void render(Graphics g, int gameHeight, int x, int y){
			g.drawImage(texture, x, (int) (gameHeight - Game.topBarHeight - TILEHEIGHT - y), TILEWIDTH, TILEHEIGHT, null);
		}
		
		public boolean isSolid(){
			return false;
		}
		
		public int getId(){
			return id;
		}
		
	}
