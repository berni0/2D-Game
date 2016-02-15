package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager_alt implements KeyListener {

	boolean key[] = new boolean[256];

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		key[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public boolean[] getBuffer(){
		return key;
	}
	
	public void clearBuffer(){
		key = new boolean[256];
	}

}
