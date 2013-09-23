
public interface Bullet {

	float getX();
	void setX(float x);
	float getY();
	void setY(float y);
	boolean penetrate();
	void move();
	void move(float x, float y);
	void render();
	boolean offScreen();
}
