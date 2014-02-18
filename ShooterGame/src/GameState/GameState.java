package GameState;

// TODO should some of this (money) be extracted into a seperate object, used for items related to the game, and on related to logic/engine functions (isCameraFollow)
/**
 * The object that stores information related to the game, including the current level and certain behavioral booleans.
 * @author Kyle Rogers
 *
 */
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
