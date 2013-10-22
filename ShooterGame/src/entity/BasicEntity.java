package entity;

/** 
 *  Base class for any mobile or living object on screen.
 */
public abstract class BasicEntity implements Entity {
	
	// Basic Entity required attributes
	// --------------------------------
	
	//Classes of Entity - Controls AI reaction
	public static enum entityClass {
		PLAYER, HOSTILE, PASSIVE, STATIONARY
	};
	
	//Current Entity class
	protected entityClass entityType;
	
	//Entity name
	protected String name;
	
	//Unique Entity ID
	protected int eid;
	
	// Entity X, Y, Z, and current rotation degree coordinates 
	protected float x, y, z;
	
	// Entity rotation degree, speed, and entity scale
	protected float rotation, scale, rotationSpeed;
	
	// Entity health constraints (if needed - Negative value for infinite health)
	protected float baseHealth, health, maxHealth;
	
	// The width and height of the Entity
	protected float width, height;
	
	// The speed constraints and angular direction of the Entity
	protected float minimumSpeed, speed, maxSpeed, angle;

	// Begin Entity base methods
	// -------------------------
	public String getName() { return this.name; }
	
	public void setName(String name) { this.name = name; }
	
	public int getEid() { return this.eid; }
	
	public float getX() { return this.x; }

	public void setX(float x) { this.x = x; }

	public float getY() { return this.y; }

	public void setY(float y) { this.y = y; }
	
	public float getZ() { return this.z; }

	public void setZ(float z) { this.z = z; }
	
	public float getRotation() { return this.rotation; }

	public void setRotation(float rotation) { this.rotation = rotation; }
	
	public float getRotationSpeed() { return this.rotationSpeed; }

	public void setRotationSpeed(float rotationSpeed) { this.rotationSpeed = rotationSpeed; }
	
	public float getScale() { return this.scale; }

	public void setScale(float scale) { this.scale = scale; }
	
	public float getHealth() { return this.health; }
	
	public void setHealth(float health) { this.health = (health>maxHealth)?maxHealth:health; }
	
	public float getSpeed() { return this.speed; }
	
	public void setSpeed(float speed) { this.speed = (speed>maxSpeed)?maxSpeed:(speed<minimumSpeed)?minimumSpeed:speed; }
	
	public float getWidth() { return this.width; }
	
	public void setWidth(float width) { this.width = width; }
	
	public float getHeight() { return this.height; }

	public void setHeight(float height) { this.height = height; }
	
	public float getAngle() { return this.angle; }

	public void setAngle(float angle) { this.angle = angle; }
	
	public void move(Entity target) {
		//TODO: Add movement processing
		
		//Call Entity specific targeting function
		processMovementTick(target);
	}
	
	public void move() {
		//Call move with self as target - Forces wander mode
		move(this);
	}
	
	/**
	 * processMovementTick(Entity) - Processes entity specific targeting
	 * @param target - Target Entity AI should react to. Send self reference for no target
	 */
	protected abstract void processMovementTick(Entity target);
}