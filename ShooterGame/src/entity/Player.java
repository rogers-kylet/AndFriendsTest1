package entity;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Player
 * 
 * Base class for Player object.
 *
 */
public class Player extends BasicEntity {
	
	//Player-specific number of invincible frames
	private static final int INVINCIBILITY_TIMER_START_VALUE = 60;
	
	//Number of frames for flash alternation
	private static final int FLASH_TIMER_START_VALUE = 6;
	
	// Default value for shooter timer
	private static final int SHOOTER_TIMER_START_VALUE = 30;
	
	private int flashTimer = 0;
	private int shooterTimer = 0;
	boolean canShoot = true;
	
	private Texture texture;

	public Player(float x, float y, float z, int eid) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.angle=0f;
		this.baseHealth=5f;
		this.health=5f;
		this.height=50f;
		this.width=50f;
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
		this.hitSfx = "playerhit";
		this.displayed = true;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Player" + ".png"));
	}
	
	@Override
	public void render() {
		
		//Push player model to world coordinates for display
		if(displayed){
			/*
			GL11.glColor3f(0.0f, 1.0f, 0.0f);
	
			GL11.glPushMatrix();
				GL11.glTranslatef(this.x,this.y,0);
				GL11.glRotatef(this.rotation, 0f, 0f, 1f);
				GL11.glTranslatef(-this.x, -this.y, 0);
				
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2f(this.x - 25, this.y - 25);
					GL11.glVertex2f(this.x + 25, this.y - 25);
					GL11.glVertex2f(this.x + 25, this.y + 25);
					GL11.glVertex2f(this.x - 25, this.y + 25);
				GL11.glEnd();
			GL11.glPopMatrix();
			*/
			if(!invincible){
			Color.white.bind();
			} else {
				Color.red.bind();
			}
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			texture.bind(); // or GL11.glBind(texture.getTextureID());
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
			
			GL11.glPushMatrix();
		
				// Replace glBegin with vertex buffer
				GL11.glTranslatef(this.x,this.y,0);
				GL11.glRotatef(this.rotation, 0f, 0f, 1f);
				GL11.glTranslatef(-this.x, -this.y, 0);
			
				GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0,0);
					GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
					GL11.glTexCoord2f(this.texture.getWidth(),0);
					GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
					GL11.glTexCoord2f(this.texture.getWidth(),this.texture.getHeight());
					GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
					GL11.glTexCoord2f(0,this.texture.getHeight());
					GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
				GL11.glEnd();
				
			GL11.glPopMatrix();
		}
		
		//Check if player invincibility flash is toggled
		if(this.invincible) {
			
			//Process invincibility tick
			this.invincibleTime -= 1;
			this.flashTimer -= 1;
			
			//Check if time to switch display modes for flash has elapsed
			if(this.flashTimer<=0) {
				this.displayed^=true;
				this.flashTimer=Player.FLASH_TIMER_START_VALUE;
			}
			
			//Check if invincibility has run out
			//TODO: Bug: If not in frame invincibility countdown is not processed
			if(this.invincibleTime<=0){
				this.invincible = false;
				this.displayed = true;
			}
		}
		
		if(!canShoot) {
			countDownShooterTimer();
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

	// Counts down the shooter timer by one, setting can shoot to true when it hits zero
	public void countDownShooterTimer(){
		shooterTimer -= 1;
		if(shooterTimer == 0){
			this.canShoot = true;
		}
	}
	
	public boolean isCanShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}
	
	public void resetShooterTimer() {
		this.shooterTimer = SHOOTER_TIMER_START_VALUE;
	}

	// Hurts the player by the given amount of damage
	public void hurtPlayer(int damage) {
		if(!invincible){
			this.health = health - damage;
			this.invincible = true;
			resetInvincibilityTimer();
			resetFlashTimer();
		}
	}
	
	// Sets the invincibility timer back to it's default start value
	public void resetInvincibilityTimer(){
		this.invincibleTime = INVINCIBILITY_TIMER_START_VALUE;
	}
	
	public void resetFlashTimer(){
		flashTimer = FLASH_TIMER_START_VALUE;
	}
}
