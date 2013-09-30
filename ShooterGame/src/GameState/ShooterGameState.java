package GameState;

public class ShooterGameState implements GameState {

	private int score; 
	private String level;
	
	public ShooterGameState(){
		this.score = 0;
		this.level = "Start";
	}
	
	// Returns the current game score
	@Override
	public int getScore() {
		return this.score;
	}

	// Sets the current game score
	@Override
	public void setScore(int newScore) {
		this.score = newScore;
	}

	// Returns the current Level Name
	@Override
	public String getLevel() {
		return this.level;
	}

	// Sets the current level name
	@Override
	public void setLevel(String levelName) {
		this.level = levelName;
	}

	// Adds the inputed int to the current score
	@Override
	public void addToScore(int newScore) {
		this.score += newScore;
	}

	// Sets the score back to 0
	@Override
	public void resetScore() {
		this.score = 0;
	}

}
