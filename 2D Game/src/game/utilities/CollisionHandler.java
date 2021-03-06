package game.utilities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import game.entities.Creature;
import game.entities.Entity;

public class CollisionHandler {
	private ArrayList<Entity> statics;
	private ArrayList<Creature> creatures;

	public CollisionHandler(ArrayList<Entity> statics, ArrayList<Creature> creatures) {
		this.statics = statics;
		this.creatures = creatures;
	}

	public void addStatic(Entity e) {
		statics.add(e);
	}

	public void removeStatic(Entity e){
		for(int i = 0; i < statics.size(); i++){
			if(statics.get(i) == e){
				statics.remove(i);
				return;
			}
		}
	}
	
	public void addStatics(Entity[] e) {
		statics.addAll(Arrays.asList(e));
	}

	public void addStatics(ArrayList<Entity> e) {
		statics.addAll(e);
	}

	public void addCreature(Creature c) {
		creatures.add(c);
	}
	
	public void removeCreature(Entity e){
		for(int i = 0; i < creatures.size(); i++){
			if(creatures.get(i) == e){
				creatures.remove(i);
				return;
			}
		}
	}

	public void addCreatures(Creature[] c) {
		creatures.addAll(Arrays.asList(c));
	}

	public void addCreatures(ArrayList<Creature> c) {
		statics.addAll(c);
	}

	public void tick() {
		boolean[] hasGround = new boolean[creatures.size()];
		
		for (int i = 0; i < creatures.size(); i++) {
			for (int j = 0; j < statics.size(); j++) {
				if (checkCollision(creatures.get(i), statics.get(j))) {
					hasGround[i] = true;
				}
			}
		}

		for (int i = 0; i < creatures.size(); i++) {
			for (int j = i + 1; j < creatures.size(); j++) {
				if (checkCollision(creatures.get(i), creatures.get(j))) {
					hasGround[i] = true;
					hasGround[j] = true;
				}
			}
		}
		
		for (int i = 0; i < hasGround.length; i++) {
			if (!hasGround[i]) {
				creatures.get(i).setHasGround(false);
			}
		}
	}

	private boolean checkCollision(Creature e1, Entity e2) {
		boolean hasGround = false;
		if (e1.getBounds().intersects(e2.getBounds())) {
			Rectangle r1 = e1.getBounds();
			Rectangle r2 = e2.getBounds();
			if (r1.getMinY() <= r2.getMaxY() && r1.getMinY() > r2.getMaxY() - 5) {
				e1.collision(e2, false, -1);
				hasGround = true;
			} else if (r1.getMaxY() >= r2.getMinY()
					&& r1.getMaxY() < r2.getMinY() + 5) {
				e1.collision(e2, false, 1);
			} else if (r1.getMinX() <= r2.getMaxX()
					&& r1.getMinX() > r2.getMaxX() - 5) {
				e1.collision(e2, false, -2);
			} else if (r1.getMaxX() >= r2.getMinX()
					&& r1.getMaxX() < r2.getMinX() + 5) {
				e1.collision(e2, false, 2);
			} else {
				e1.collision(e2, false, 0);
			}
		}
			return hasGround;
	}

}
