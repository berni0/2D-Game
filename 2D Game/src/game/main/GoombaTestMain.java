package game.main;

import game.entities.Goomba;

public class GoombaTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Goomba g = new Goomba(1, 2);
		System.out.println(g.getClass().getName());
		g.collision(g, false);
	}

}
