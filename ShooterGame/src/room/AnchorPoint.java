package room;

// The point of a room that another room can hook into
public class AnchorPoint {

	float x, y;
	
	String direction;
	// Determine if a room has been generated based off of this point
	boolean hooked;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isHooked() {
		return hooked;
	}

	public void setHooked(boolean hooked) {
		this.hooked = hooked;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
