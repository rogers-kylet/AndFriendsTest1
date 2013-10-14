package Enemy;

public interface Enemy {

	// Draw the enemy
	void render();
	// Returns the X coordinate of the enemy
	float getX();
	// Sets the X coordinate of the enemy
	void setX(float x);
	// Returns the X coordinate of the enemy
	float getY();
	// Sets the Y coordinate of the enemy
	void setY(float y);
	// Returns the rotation degree of the enemy
	float getRotation();
	// Sets the rotation degree of the enemy
	void setRotation(float rot);
	// Returns the health of the enemy
	int getHealth();
	// Sets the health of the enemy
	void setHealth(int health);
	// Updates the X and Y coordinates of the enemy relative to the player (or any other object)
	void move(float playerX, float playerY);
	// Updates the X and Y coordinates of the enemy without any additional information
	void move();
	// Checks if the enemy has collided with the player
	boolean collidWithPlayer(float playerX, float playerY);
	
}
