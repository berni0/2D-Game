package game.graphics;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;


public class GUI {
	private JFrame frame;
	private Canvas canvas;
	private int width, height;
	private Dimension d;
	
	public GUI(int width, int height) {
		this.width = width;
		this.height = height;

		initFrame();
	}

	private void initFrame() {
		frame = new JFrame("Game Test");
		canvas = new Canvas();
		d = new Dimension(width, height);
		canvas.setPreferredSize(d);
		canvas.setMaximumSize(d);
		canvas.setMinimumSize(d);

		frame.add(canvas);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(d);
		frame.setMaximumSize(d);
		frame.setMinimumSize(d);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);

//		iPanel.setLayout(null);
//		frame.setContentPane(iPanel);

		frame.setVisible(true);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}


}
