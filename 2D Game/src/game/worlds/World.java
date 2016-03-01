package game.worlds;

import java.awt.Graphics;
import java.util.ArrayList;

import game.entities.Obstacle;
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
		createObstacles();
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
	
	public void render(Graphics g, int gameHeight){
		for (int y = 0; y < height; y++ ){
			for(int x = 0; x < width; x++){
				getTile(x, y).render(g, gameHeight, x*Tile.TILEWIDTH -(int) offset, y*Tile.TILEHEIGHT);
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
	
	public void createObstacles() {
		boolean isObstacleInCreation = false;
		double oX = 0, oY = 0;
		int oWidth = 0;
		int oHeight = 0;
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){ //was: j < tilePosition.length;
				if (getTile(j,i).isSolid()){
					if(!isObstacleInCreation) {
						oX = j*Tile.TILEWIDTH;
						oY = i*Tile.TILEHEIGHT;
						oWidth = Tile.TILEWIDTH;
						oHeight = Tile.TILEHEIGHT;
						isObstacleInCreation = true;
					} else {
						oWidth += Tile.TILEWIDTH;						
					}
				} else {					
					if(isObstacleInCreation) {
						tileObstacles.add(new Obstacle(oX, oY, oWidth, oHeight));
						isObstacleInCreation = false;						
					}
				}
			} 
			if(isObstacleInCreation) {
				tileObstacles.add(new Obstacle(oX, oY, oWidth, oHeight));
				isObstacleInCreation = false;				
			}
		}
		//System.out.println(tileObstacles.size());
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
