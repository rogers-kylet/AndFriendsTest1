package GameState;

public interface GameState {

	// Gets the current game score
	int getMoney();
	// Sets the current game score
	void setMoney(int newMoney);
	// Adds value to current score
	void addToMoney(int newMoney);
	// Resets the score back to 0
	void resetMoney();
	// Gets the current level
	String getLevel();
	// Sets the current level
	void setLevel(String levelName);
	boolean isCameraFollow();
	void setCameraFollow(boolean cameraFollow);
}
