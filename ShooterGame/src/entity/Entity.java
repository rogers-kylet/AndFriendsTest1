package entity;

/** 
 *  Base interface for any mobile or living object on screen.
 */
public interface Entity {

	// Begin Entity base methods
	// -------------------------
	
	// Draw the Entity
	/**
	 * render()
	 * Renders the Entity on screen if visible
	 */
	public void render();
	 
	/**
	  * getName() - Get name of Entity
	  * @return name - Name of Entity
	  */
	public String getName();

	/**
	 * setName(String) - Set name of Entity
	 * @param name - New name of Entity
	 */
	public void setName(String name);
	
	/**
	  * getEid() - Get unique ID of Entity
	  * @return eid - Unique ID of Entity
	  */
	public int getEid();
	
	/**
	 * getX() - Get world X coordinate of Entity
	 * @return x - X coordinate of Entity
	 */
	public float getX();

	/**
	 * setX(float) - Set world X coordinate of Entity
	 * @param x - World X coordinate of Entity
	 */
	public void setX(float x);

	/**
	 * getY() - Get world Y coordinate of Entity
	 * @return y - Y coordinate of Entity
	 */
	public float getY();

	/**
	 * setY(float) - Set world Y coordinate of Entity
	 * @param y - World Y coordinate of Entity
	 */
	public void setY(float y);
	
	/**
	 * getZ() - Get world Z coordinate of Entity
	 * @return z - Z coordinate of Entity
	 */
	public float getZ();

	/**
	 * setZ(float) - Set world Z coordinate of Entity
	 * @param z - World Z coordinate of Entity
	 */
	public void setZ(float z);
	
	/**
	 * getRotation() - Get rotation degree of Entity
	 * @return rotation - Current rotation degree of Entity
	 */
	public float getRotation();

	/**
	 * setRotation(float) - Set rotation degree of Entity
	 * @param rotation - New rotation degree of Entity
	 */
	public void setRotation(float rotation);
	
	/**
	 * getRotationSpeed() - Get rotation speed (in degrees/frame) of Entity
	 * @return rotationSpeed - Current rotation speed of Entity
	 */
	public float getRotationSpeed();

	/**
	 * setRotationSpeed(float) - Set rotation speed (in degrees/frame) of Entity
	 * @param rotationSpeed - New rotation speed of Entity
	 */
	public void setRotationSpeed(float rotationSpeed);
	
	/**
	 * getScale() - Get scale of Entity
	 * @return Current scaling of the Entity
	 */
	public float getScale();

	/**
	 * setScale(float) - Set scale of Entity
	 * @param scale - Current scaling of Entity
	 */
	public void setScale(float scale);
	
	/**
	 * getHealth() - Get current health of Entity
	 * @return Current health of the Entity
	 */
	public float getHealth();

	/**
	 * setHealth(float) - Set current health of Entity
	 * @param scale - Current health of Entity
	 */
	public void setHealth(float health);
	
	/**
	 * getSpeed() - Get current speed of Entity
	 * @return Current speed of the Entity
	 */
	public float getSpeed();

	/**
	 * setSpeed(float) - Set current speed of Entity
	 * @param speed - Current speed of Entity
	 */
	public void setSpeed(float speed);
	
	/**
	 * getWidth() - Get width of Entity
	 * @return Current width of the Entity
	 */
	public float getWidth();

	/**
	 * setWidth(float) - Set width of Entity
	 * @param width - Current width of Entity
	 */
	public void setWidth(float width);
	
	/**
	 * getHeight() - Get height of Entity
	 * @return Current height of the Entity
	 */
	public float getHeight();

	/**
	 * setHeight(float) - Set height of Entity
	 * @param height - Current height of Entity
	 */
	public void setHeight(float height);
	
	/**
	 * getAngle() - Get angle of Entity (Direction, in degrees, the Entity is facing)
	 * @return Current angle of the Entity
	 */
	public float getAngle();

	/**
	 * setAngle(float) - Set angle of Entity (Direction, in degrees, the Entity is facing)
	 * @param angle - Current angle of Entity
	 */
	public void setAngle(float angle);
	
	
	
	/**
	 * move(Entity) - Calculates Entity movement for this frame 
	 * @param target - Target Entity AI should react to. Send self reference for no target
	 *
	 */
	public void move(Entity target);
	
	/**
	 * move() - Calculates Entity movement for this frame with no visible target.
	 * 
	 * Simplified call to move(this) to disable targeting
	 */
	public void move();

}
