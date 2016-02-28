package game.worlds;

import java.awt.Graphics;
import java.util.ArrayList;

import game.entities.Obstacle;
import game.entities.Player;
import game.tiles.Tile;
import game.utilities.worldStringReader;

public class World {
		
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tilePosition;
	private ArrayList<Obstacle> tileObstacles = new ArrayList<Obstacle>();
	private double offset;
	
	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public void addToOffset(double offset){
		this.offset += offset;
	}
	
	public World(String path){
		loadWorld(path);
		for(int i = 0; i < tilePosition.length; i++){
			for(int j = 0; j < tilePosition[i].length; j++){
				if (getTile(i,j).isSolid()){
					tileObstacles.add(new Obstacle(i*Tile.TILEWIDTH, j*Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT));
				}
			}
		}
		offset = 0;
	}
	
	public Obstacle[] getObstacles(){
		Obstacle o[] = new Obstacle[tileObstacles.size()];
		return tileObstacles.toArray(o);
	}
	
	public Tile getTile(int x, int y){
		Tile t = Tile.idList[tilePosition[x][y]];
		if(t == null)
			t = Tile.idList[0];
		
		return t;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		for (int y = 0; y < height; y++ ){
			for(int x = 0; x < width; x++){
				getTile(x, y).render(g, x*Tile.TILEWIDTH -(int) offset, y*Tile.TILEHEIGHT);
			}
		}
		/*
		for(int i = 0; i < tileObstacles.size(); i++){
			tileObstacles.get(i).render(g, offset);
		}*/
	}
	
	public void loadWorld(String path){
		String file = worldStringReader.loadFileAsString(path);
		String[] worldData = file.split("\\s+");
		width = worldStringReader.praseInt(worldData[0]);
		height = worldStringReader.praseInt(worldData[1]);
		setSpawnX(worldStringReader.praseInt(worldData[2]));
		setSpawnY(worldStringReader.praseInt(worldData[3]));
		
		tilePosition = new int[width][height];

			for (int y = 0; y < height; y++ ){
				for(int x = 0; x < width; x++){
					tilePosition[x][y] = worldStringReader.praseInt(worldData[x + y * width + 4]);
				}
			}
	}

	public int getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
}
