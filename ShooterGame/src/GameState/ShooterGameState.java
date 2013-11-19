package GameState;

public class ShooterGameState implements GameState {

	private int money; 
	private String level;
	boolean cameraFollow;
	
	public ShooterGameState(){
		this.money = 0;
		this.level = "Start";
		this.cameraFollow = false;
	}
	
	// Returns the current game score
	@Override
	public int getMoney() { return this.money; }

	// Sets the current game score
	@Override
	public void setMoney(int newMoney) { this.money = newMoney; }

	// Returns the current Level Name
	@Override
	public String getLevel() { return this.level; }

	// Sets the current level name
	@Override
	public void setLevel(String levelName) { this.level = levelName; }

	// Adds the inputed int to the current score
	@Override
	public void addToMoney(int newMoney) { this.money += newMoney; }

	// Sets the score back to 0
	@Override
	public void resetMoney() { this.money = 0; }

	@Override
	public boolean isCameraFollow() { return cameraFollow; }

	@Override
	public void setCameraFollow(boolean cameraFollow) { this.cameraFollow = cameraFollow; }
}
