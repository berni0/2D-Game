package game.tiles;

import game.gfx.Assets;

public class DirtTile extends Tile {

	public DirtTile(int id) {
		super(Assets.dirt, id);
	}
	
	public boolean isSolid(){
		return true;
	}
}
