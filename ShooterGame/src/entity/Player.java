package entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * Player
 * 
 * Base class for Player object.
 *
 */
public class Player extends BasicEntity {
	
	//Player-specific number of incincible frames
	private static final int INVINCIBILITY_TIMER_START_VALUE = 60;
	
	//Number of frames for flash alternation
	private static final int FLASH_TIMER_START_VALUE = 6;
	
	private int flashTimer = 0;

	public Player(float x, float y, float z, int eid) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.angle=0f;
		this.baseHealth=5f;
		this.health=5f;
		this.height=20f;
		this.width=20f;
		this.entityType=entityClass.PLAYER;
		this.maxHealth=10f;
		this.baseHealth=5f;
		this.maxSpeed=3f;
		this.speed=3f;
		this.minimumSpeed=3f;
		this.name="Player";
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
	}
	
	@Override
	public void render() {
		
		//Push player model to world coordinates for display
		if(displayed){
			GL11.glColor3f(0.0f, 1.0f, 0.0f);
	
			GL11.glPushMatrix();
				GL11.glTranslatef(x,y,0);
				GL11.glRotatef(rotation, 0f, 0f, 1f);
				GL11.glTranslatef(-x, -y, 0);
				
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2f(x - 25, y - 25);
					GL11.glVertex2f(x + 25, y - 25);
					GL11.glVertex2f(x + 25, y + 25);
					GL11.glVertex2f(x - 25, y + 25);
				GL11.glEnd();
			GL11.glPopMatrix();
		}
		
		//Check if player invincibility flash is toggled
		if(invincible) {
			
			//Process invincibility tick
			invincibleTime -= 1;
			flashTimer -= 1;
			
			//Check if time to switch display modes for flash has elapsed
			if(flashTimer<=0) {
				this.displayed^=true;
				this.flashTimer=Player.FLASH_TIMER_START_VALUE;
			}
			
			//Check if invincibility has run out
			//TODO: Bug: If not in frame invincibility countdown is not processed
			if(invincibleTime<=0){
				invincible = false;
				displayed = true;
			}
		}
		
	}

	@Override
	protected boolean processCollisionTick(Entity target) {
		if(
				( target.getX() - target.getWidth() / 2 < ( this.getX() + this.width / 2 ) ) && 
				( target.getX() + target.getWidth() / 2 > ( this.getX() - this.width / 2 ) ) && 
				( target.getY() + target.getHeight() / 2 > ( this.getY() - this.height / 2 ) ) && 
				( target.getY() - target.getHeight() / 2 < ( this.getY() + this.height / 2 ) ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void processMovementTick(Entity target) {
		this.x += this.speed * Math.cos(Math.toRadians(angle));
		this.y += this.speed * Math.sin(Math.toRadians(angle));		
	}

}
