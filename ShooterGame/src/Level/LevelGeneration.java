package Level;

public class LevelGeneration {

	public Level generateLevel(String levelName, int levelNumber) {
		Level theLevel = new BasicLevel();
		theLevel.setBackgroundMusic("level");
		theLevel.setName(levelName);
		theLevel.setType("level");
		return theLevel;
	}
}
