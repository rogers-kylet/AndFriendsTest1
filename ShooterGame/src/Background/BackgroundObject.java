package Background;

public interface BackgroundObject {
	// Draw the object
	void render();
	// Returns the X coordinate of the object
	float getX();
	// Sets the X coordinate of the object
	void setX(float x);
	// Returns the X coordinate of the object
	float getY();
	// Sets the Y coordinate of the object
	void setY(float y);
	// Returns the rotation degree of the object
	float getRotation();
	// Sets the rotation degree of the object
	void setRotation(float rot);
	// Returns the health of the object
	int getHealth();
	// Sets the health of the object
	void setHealth(int health);
	// Updates the X and Y coordinates of the object relative to the player (or any other object)
	void move(float playerX, float playerY);
	// Updates the X and Y coordinates of the enemy without any additional information
	void move();
	
}
