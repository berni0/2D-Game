package game.utilities;

public class RandomWorldCreator {
	
	//parameters
	private static int basicProbability = 95; //between 0 and 100
	
	private static double factorAfterGap = 0.1;
	private static double factorGrowthAfterGap = 2;
	
	//working variables
	private static int latestProbability = basicProbability;
	
	public static String createWorld(int width, int height) {
		String worldString = "";
		
		worldString += (Integer.toString(width) + " " + Integer.toString(height) + "\n");
		worldString += (Integer.toString(50) + " " + Integer.toString(50) + "\n");
		
		worldString += createBasis(width, height);
		
		return worldString;
	}
	
	private static String createBasis(int width, int height) {
		String s = "";		
		for(int i = 1; i <= height; i++) {
			s += createLine(width, i);
		}
		return s;
	}
	
	private static String createLine(int length, int nr) {
		String line = "";
		if(nr == 1) {
			line += "1 1 1 1 1 ";
			for(int i = 6; i <= length; i++) {
				line += (generateTile(latestProbability) + " ");
			}			
		} else {
			for(int i = 1; i <= length; i++) {
				line += (generateTile(0) + " ");
			}			
		}
		line += "\n";
		return line;
	}
	
	private static String generateTile(int probability) {
		String s;
		int p = probability - (int) (Math.random() * 100);
		if(p > 0) {
			s = "1";
			latestProbability = basicProbability;
		}
		else {
			s = "0";
			if(latestProbability < basicProbability) {
				latestProbability = (int) (latestProbability * factorGrowthAfterGap);
			} else {
				if(latestProbability == basicProbability) {
					latestProbability = (int) (basicProbability * factorAfterGap);
				} else {
					latestProbability = basicProbability;
				}
			}
		}
		//System.out.println(s);
		//System.out.println(latestProbability);
		return s;
	}
}
