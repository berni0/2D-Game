package game.states;

import java.awt.Graphics;

import game.main.Game;

public abstract class State {

	public static State currentState = null;
	
	public static void setState(State s){
		currentState = s;
	}
	
	public static State getState(){
		return currentState;
	}
	
	protected Game game;
	
	public State(Game g) {
		this.game = g;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
