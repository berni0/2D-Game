package game.utilities;

public class RandomWorldCreator {
	
	//parameters
	private static boolean generateGaps = false;	
	private static boolean generateStages = true;	
	
	private static int gapProbability = 5; //between 0 and 100
	private static int stageProbability = 5; //between 0 and 100
	
	private static int minGapLength = 3;
	private static int maxGapLength = 7;
	private static int solidAfterGap = 2;
	
	private static int minStageLength = 2;
	private static int maxStageLength = 5;
	private static int airAfterStage = 5;
	
	//working variables
	private static int columnNo = 0;
	
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
	
	private static String createLine(int length, int lineNo) {
		String line = "";
		if(lineNo == 1) {
			line += "1 1 1 1 1 ";
			columnNo = 6;
			while(columnNo <= length) {
				line += (generateTile(true, length));
			}			
		} else {
			columnNo = 1;
			while(columnNo <= length) {
				line += (generateTile(false, length));
			}			
		}
		line += "\n";
		return line;
	}
	
	private static String generateTile(boolean isGround, int length) {
		String s = "";
		if(isGround) {
			if(generateGaps) {
				int p = gapProbability - (int) (Math.random() * 100);
				if(p > 0) {
					int gapLength = (int) Math.round((maxGapLength - minGapLength) * Math.random()) + minGapLength;
					for(int i = 1; i <= gapLength && columnNo <= length; i++) {
						s += "0 ";
						columnNo++;
					}
					for(int j = 1; j <= solidAfterGap && columnNo <= length; j++) {
						s += "1 ";
						columnNo++;
					}				
				} else {
					s = "1 ";
					columnNo++;
				}
			} else {
				s = "1 ";
				columnNo++;
			}
		} else {
			if(generateStages) {
				int p = stageProbability - (int) (Math.random() * 100);
				if(p > 0) {
					int stageLength = (int) Math.round((maxStageLength - minStageLength) * Math.random()) + minStageLength;
					for(int i = 1; i <= stageLength && columnNo <= length; i++) {
						s += "1 ";
						columnNo++;
					}
					for(int j = 1; j <= airAfterStage && columnNo <= length; j++) {
						s += "0 ";
						columnNo++;
					}				
				} else {
					s = "0 ";
					columnNo++;
				}
			} else {
				s = "0 ";
				columnNo++;
			}
		}
		return s;
	}
}
