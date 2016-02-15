package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean key[] = new boolean[256]; 
	public boolean up, down, left, right;
	
	@Override
	public void keyPressed(KeyEvent e) {
		key[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		key[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void tick(){
		up 		= key[KeyEvent.VK_W] || key[KeyEvent.VK_SPACE];
		down  	= key[KeyEvent.VK_S];
		left 	= key[KeyEvent.VK_A];
		right 	= key[KeyEvent.VK_D];
	}
	
}


