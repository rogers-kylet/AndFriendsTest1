package GameState;

public interface GameState {

	// Gets the current game score
	int getScore();
	// Sets the current game score
	void setScore(int newScore);
	// Adds value to current score
	void addToScore(int newScore);
	// Resets the score back to 0
	void resetScore();
	// Gets the current level
	String getLevel();
	// Sets the current level
	void setLevel(String levelName);
	boolean isCameraFollow();
	void setCameraFollow(boolean cameraFollow);
}
