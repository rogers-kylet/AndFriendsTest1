package entity;

import java.io.IOException;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import room.Room;

/** 
 *  Base class for any mobile or living object on screen.
 */
public abstract class BasicEntity implements Entity {

	// Basic Entity required attributes
	// --------------------------------

	//Classes of Entity - Controls AI reaction
	public static enum entityClass {
		PLAYER, HOSTILE, PASSIVE, STATIONARY, PROJECTILE
	};
	
	public boolean displayed, invincible;

	//Current Entity class
	protected entityClass entityType;

	//Entity name
	protected String name;

	//Unique Entity ID and current invincibility timer
	protected int eid, invincibleTime = 0;

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
	protected float minimumXSpeed, xSpeed, maxXSpeed;
	protected float minimumYSpeed, ySpeed, maxYSpeed, gravity;

	protected Vector3f acceleration;
	protected Vector3f minAcceleration;
	
	protected String hitSfx;

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

	public void setAngle(float angle) { 
		if(angle >= 360) {
			angle -= 360;
		} else if (angle < 0) {
			angle += 360;
		}
		this.angle = angle; 
		
	}

	public Vector3f getAcceleration() { return acceleration; }

	public void setAcceleration(Vector3f acceleration) { this.acceleration = acceleration; }

	public void move(Entity target) {
		//TODO: Add movement processing

		//Call Entity specific targeting function
		processMovementTick(target);
	}

	public void move(Entity target, List<Room> entityList) {
		processMovementTick(target, entityList);
	}
	
	//Call move with self as target - Forces wander mode
	public void move() { move(this); }
	
	public boolean collisionDetection(Entity target) {
		//TODO: Add collision processing
		
		//Call Entity specific collision function (If required)
		return processCollisionTick(target);
		
	}
		
	@Override
	public void hurt(int damage) {
		// If health is -1, that means it's trying to avoid some health processing routine, let it
		if(this.health != -1) {
			this.health -= damage;
			// Set the health to 0 if it is less, that way it won't interfier with -1 based health calculations
			if(this.health < 0) {
				this.health = 0;
			}
		}
	}
	/**
	 * processCollisionTick(Entity) - Processes entity specific collision detection
	 * @param target - Target Entity AI should react to. Send self reference for no target
	 */
	protected abstract boolean processCollisionTick(Entity target);

	/**
	 * processMovementTick(Entity) - Processes entity specific targeting
	 * @param target - Target Entity AI should react to. Send self reference for no target
	 */
	protected abstract void processMovementTick(Entity target);

	public String getHitSfx() { return hitSfx; }

	public void setHitSfx(String hitSfx) { this.hitSfx = hitSfx; }

	public float getMaxHealth() { return maxHealth; }

	public void setMaxHealth(float maxHealth) { this.maxHealth = maxHealth; }
	
	public abstract List<Entity> attack(Entity target) throws IOException;

	public void processMovementTick(Entity target, List<Room> entityList) {
		// TODO Auto-generated method stub
		
	}

	public float getxSpeed() { return xSpeed; }

	public void setxSpeed(float xSpeed) { this.xSpeed = xSpeed; }

	public float getySpeed() { return ySpeed; }

	public void setySpeed(float ySpeed) { this.ySpeed = ySpeed; }

	public float getGravity() { return gravity; }

	public void setGravity(float gravity) { this.gravity = gravity; }

	public boolean isInvincible() { return invincible; }

	public void setInvincible(boolean invincible) { this.invincible = invincible; }
	
	
}