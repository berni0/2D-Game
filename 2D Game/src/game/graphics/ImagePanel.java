package game.graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 6079551757061420288L;

	private Image backgroundImage;

	public ImagePanel(Image pImage) {
		backgroundImage = pImage;
	}

	public void setImage(Image pImage) {
		backgroundImage = pImage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		g.drawImage(backgroundImage, 0, 0, x, y, this);
	}
}
