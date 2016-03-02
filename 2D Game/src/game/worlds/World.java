package game.worlds;

import java.awt.Graphics;
import java.util.ArrayList;

import game.entities.Obstacle;
import game.tiles.Tile;
import game.utilities.RandomWorldCreator;
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
		loadRandomWorld();
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
	
	public void loadWorldFromFile(String path){
		loadWorld(worldStringReader.loadFileAsString(path));
	}
	
	public void loadRandomWorld() {
		loadWorld(RandomWorldCreator.createWorld(50, 20));
	}
	
	public void loadWorld(String worldString) {
		String file = worldString;
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
	
	/**
	 * creates obstacles out of the tiles, horizontally optimized
	 */
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
		optimizeObstaclesVertically();
	}
	
	/**
	 * optimizes obstacle list vertically
	 */
	public void optimizeObstaclesVertically() {
		boolean changed = false;
		if(!tileObstacles.isEmpty()) {
			Obstacle comparedObstacle1, comparedObstacle2;
			for(int i = 0; i < tileObstacles.size(); i++) {
				comparedObstacle1 = tileObstacles.get(i);
				for(int j = i + 1; j < tileObstacles.size(); j++) {
					comparedObstacle2 = tileObstacles.get(j);
					
					if(comparedObstacle1.getX() == comparedObstacle2.getX()) {
						if(comparedObstacle1.getWidth() == comparedObstacle2.getWidth()) {
							if(comparedObstacle1.getY() + comparedObstacle1.getHeight() == comparedObstacle2.getY()) {
								//obstacle1 above obstacle2
								tileObstacles.add(new Obstacle(comparedObstacle1.getX(), comparedObstacle1.getY(),
															   comparedObstacle1.getWidth(), comparedObstacle1.getHeight() + comparedObstacle2.getHeight()));
								tileObstacles.remove(comparedObstacle1);
								tileObstacles.remove(comparedObstacle2);
								changed = true;
							}
							if(comparedObstacle1.getY() - comparedObstacle2.getHeight() == comparedObstacle2.getY()) {
								//obstacle2 above obstacle1
								tileObstacles.add(new Obstacle(comparedObstacle1.getX(), comparedObstacle2.getY(),
										   comparedObstacle1.getWidth(), comparedObstacle1.getHeight() + comparedObstacle2.getHeight()));
								tileObstacles.remove(comparedObstacle1);
								tileObstacles.remove(comparedObstacle2);
								changed = true;
							}
						}
					}
				}
			}
		}
		//System.out.println(tileObstacles.size());
		if(changed) optimizeObstaclesVertically();
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
