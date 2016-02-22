package game.utilities;

import game.entities.Entity;
import game.main.Game;

public class CollisionHandler {
	private Game game;
	private Entity[] entities;

	public CollisionHandler(Game game, Entity[] entities) {
		this.game = game;
		this.entities = entities;
	}

	public void tick() {
		for (int i = 0; i < entities.length; i++) {
			if (!entities[i].isStatic()) {
				for (int j = i + 1; j < entities.length; j++) {
					entities[i].collision(entities[j]);
				}
			}
		}
	}

}
