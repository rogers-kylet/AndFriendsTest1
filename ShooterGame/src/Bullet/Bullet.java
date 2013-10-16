package Bullet;

public interface Bullet {

	float getX();
	void setX(float x);
	float getY();
	void setY(float y);
	int getWidth();
	void setWidth(int newWidth);
	int getHeight();
	void setHeight(int newHeight);
	boolean penetrate();
	void move();
	void move(float x, float y);
	void render();
	boolean offScreen();
}
