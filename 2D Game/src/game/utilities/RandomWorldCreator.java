package game.utilities;

public class RandomWorldCreator {
	
	public static String createWorld(int width, int height) {
		String worldString = "";
		
		worldString += (Integer.toString(width) + " " + Integer.toString(height) + "\n");
		worldString += (Integer.toString(50) + " " + Integer.toString(50) + "\n");
		
		worldString += createBasis(width, height);
		
		return worldString;
	}
	
	private static String createBasis(int width, int height) {
		String s = "";
		s += createLine(width, 1);
		for(int i = 2; i <= height; i++) {
			s += createLine(width, 0);
		}
		return s;
	}
	
	private static String createLine(int length, int type) {
		String line = "";
		for(int i = 1; i <= length; i++) {
			line += (Integer.toString(type) + " ");
		}
		line += "\n";
		return line;
	}
}
