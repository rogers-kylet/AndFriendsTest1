package entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import animation.BasicFrameData;
import animation.FrameData;
import animation.PlayerAnimation;

import room.Room;
import timer.BasicTimer;
import timer.Timer;

import weapon.BasicMeleeWeapon;
import weapon.BasicWeapon;
import weapon.CircleShot;
import weapon.DoubleShot;
import weapon.MirrorShot;
import weapon.QuadShot;
import weapon.TripleShot;
import weapon.TripleSideShot;
import weapon.Weapon;

/**
 * The main player object. 
 * Needs more customizing than most entities so it has significantly more methods than BasicEntities provides. 
 * @author Kyle Rogers
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
	private boolean canShoot = true;
	private boolean canMeleeAttack = true;
	private boolean canJump = true;
	private boolean inAir = false;
	private boolean canWallJump = false;
	private boolean onWall = false;
	private boolean hasWallJumped = false;

	private Texture texture;
	
	private Weapon weapon;
	private Weapon meleeWeapon;
	
	private int weaponIndex;
	private List<Weapon> weaponList;
	
	private Timer frameTimer;
	private Timer meleeAttackTimer;
	
	private PlayerAnimation animation;

	public Player(float x, float y, float z, int eid) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.minAcceleration = new Vector3f(0, -6, 0);
		this.angle=0f;
		this.baseHealth=5f;
		this.health=5f;
		this.height=100f;
		this.width=100f;
		this.entityType=entityClass.PLAYER;
		this.maxHealth=10f;
		this.baseHealth=5f;
		this.maxSpeed=5f;
		this.speed=5f;
		this.minimumSpeed=3f;
		this.name="Player";
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
		this.hitSfx = "playerhit";
		this.displayed = true;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Player-Sprite-Sheet-2" + ".png"));
		
		this.animation = new PlayerAnimation();
		
		this.frameTimer = new BasicTimer(25);
		this.frameTimer.reset();
		
		// Needs to be the same as how long the attack lasts at min
		this.meleeAttackTimer = new BasicTimer(35);
		this.meleeAttackTimer.reset();
		
		this.xSpeed = 10f;
		this.ySpeed = 0f;
		this.gravity = -.75f;
		this.standardGravity =  -.75f;
		this.minimumYSpeed = -15;
		this.defaultMinimumYSpeed = -15;
		this.maxYSpeed = 15f;
		
		//TODO need to make a weapon builder that can add the various properties of these weapons
		Weapon weapon = new BasicWeapon();
		Weapon weapon2 = new TripleShot();
		Weapon weapon3 = new MirrorShot();
		Weapon weapon4 = new QuadShot();
		Weapon weapon5 = new CircleShot();
		Weapon weapon6 = new DoubleShot();
		Weapon weapon7 = new TripleSideShot();
		
		//TODO stupid
		Weapon meleeWeapon = new BasicMeleeWeapon();
		this.meleeWeapon = meleeWeapon;
		
		this.weapon = weapon;
		this.weaponList = new ArrayList<Weapon>();
		this.weaponList.add(weapon);
		this.weaponList.add(weapon2);
		this.weaponList.add(weapon3);
		this.weaponList.add(weapon4);
		this.weaponList.add(weapon5);
		this.weaponList.add(weapon6);
		this.weaponList.add(weapon7);
		this.weaponIndex = 0;
	}
	
	@Override
	public void render() {
		
		//Push player model to world coordinates for display
		if(displayed){
			
			if(!invincible){
				Color.white.bind();
			} else {
				Color.red.bind();
			}
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			//GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			//GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			texture.bind(); // or GL11.glBind(texture.getTextureID());
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
			
			GL11.glPushMatrix();
		
				// Replace glBegin with vertex buffer
				GL11.glTranslatef(this.x,this.y,0);
				GL11.glRotatef(this.rotation, 0f, 0f, 1f);
				GL11.glTranslatef(-this.x, -this.y, 0);
			
				FrameData frameData = animation.getCurrentFrame();
				float texX = frameData.getTexX();
				float texY = frameData.getTexY();
				float texWidth = frameData.getTexWidth();
				float texHeight = frameData.getTexHeight();
				float imgWidthScale = this.texture.getTextureWidth();
				float imgHeightScale = this.texture.getTextureHeight();
				
				if((this.angle >= 0 && this.angle <= 90) || this.angle <= 360 && this.angle >= 270) {
					
				} else {
					texWidth = -texWidth;
				}
				
				//System.out.println(imgWidthScale + " " + this.texture.getTextureWidth() + " " + this.texture.getWidth());
				//System.out.println( (texX - texWidth/2f));
				//System.out.println( (texX - texWidth/2f)/imgWidthScale);
				GL11.glBegin(GL11.GL_QUADS);
					//GL11.glTexCoord2f(texX - texWidth/2f,texY - texHeight/2f);
					//GL11.glTexCoord2f(0f, 0f);
					//GL11.glTexCoord2f((texX - texWidth/2f)/imgWidthScale,texY - texHeight/2f);
					GL11.glTexCoord2f((texX - texWidth/2f)/imgWidthScale,(texY - texHeight/2f)/imgHeightScale);
					GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
					
					//GL11.glTexCoord2f(texX + texWidth/2f,texY - texHeight/2f);
					//GL11.glTexCoord2f(1f, 0f);
					//GL11.glTexCoord2f((texX + texWidth/2f)/imgWidthScale,texY - texHeight/2f);
					GL11.glTexCoord2f((texX + texWidth/2f)/imgWidthScale,(texY - texHeight/2f)/imgHeightScale);
					GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
					
					//GL11.glTexCoord2f(texX + texWidth/2f,texY + texHeight/2f);
					//GL11.glTexCoord2f(1f, 1f);
					//GL11.glTexCoord2f((texX + texWidth/2f)/imgWidthScale,texY + texHeight/2f);
					GL11.glTexCoord2f((texX + texWidth/2f)/imgWidthScale,(texY + texHeight/2f)/imgHeightScale);
					GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
					
					//GL11.glTexCoord2f(texX - texWidth/2f,texY + texHeight/2f);
					//GL11.glTexCoord2f(0f, 1f);
					//GL11.glTexCoord2f((texX - texWidth/2f)/imgWidthScale,texY + texHeight/2f);
					GL11.glTexCoord2f((texX - texWidth/2f)/imgWidthScale,(texY + texHeight/2f)/imgHeightScale);
					GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
				GL11.glEnd();
				
			GL11.glPopMatrix();
			
			GL11.glDisable(GL11.GL_BLEND);
			if(this.frameTimer.isStopped()) {
				this.frameTimer.reset();
			} else {
				this.frameTimer.countDown();
			}
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
		
		//TODO convert to timer 
		if(!canShoot) { countDownShooterTimer(); }
		
		// Process Melee Attack Timer 
		if(this.meleeAttackTimer.isStopped()) {
			if(!this.canMeleeAttack) {
				this.canMeleeAttack = true;
			}
		} else {
			meleeAttackTimer.countDown();
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
		else { return false; }
	}

	@Override
	protected void processMovementTick(Entity target) {
		if(this.acceleration.y > this.minAcceleration.y) {
			//TODO need a conditional here to be able to turn off gravity for variable jump logic/other situations where we might want graivty off
			this.acceleration.y += gravity;
		} else {
			this.acceleration.y = this.minAcceleration.y;
		}
		
		float movementAngle;
		if(target != null) {
			if( (angle > 0 && angle < 90 )
				|| (angle < 360 && angle > 270 ) ) {
				movementAngle = 0;
			} else if ( angle < 270 && angle > 90) {
				movementAngle = 180;
			} else {
				movementAngle = angle;
			}
			this.x += this.xSpeed * Math.cos(Math.toRadians(movementAngle));
		}
		//this.y += this.speed * Math.sin(Math.toRadians(angle));	
		//TODO make this better
		this.ySpeed += this.acceleration.y;

		if(this.ySpeed < this.minimumYSpeed) {
			this.ySpeed = this.minimumYSpeed;
		}
		if(this.ySpeed > this.maxYSpeed) {
			this.ySpeed = this.maxYSpeed;
		}
		this.y -= this.ySpeed;
	}

	// Hurts the player by the given amount of damage
	public void hurtPlayer(int damage) {
		if(!invincible){
			//TODO uncomment to hurt player
			this.health = health - damage;
			this.invincible = true;
			resetInvincibilityTimer();
			resetFlashTimer();
		}
	}
	
	@Override
	public List<Entity> attack(float angle) throws IOException{
		return this.weapon.attack(angle, this);
	}
	
	//TODO this will need to be combined with shooting probably, maybe two buttons so maybe not 
	public List<Entity> meleeAttack(float angle) throws IOException {
		
		this.canMeleeAttack = false;
		this.meleeAttackTimer.reset();
		
		return this.meleeWeapon.attack(angle, this);

	}
	
	// Counts down the shooter timer by one, setting can shoot to true when it hits zero
	public void countDownShooterTimer(){
		shooterTimer -= 1;
		if(shooterTimer == 0){ this.canShoot = true; }
	}
	
	public void addWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.weaponList.add(weapon);
		this.weaponIndex = this.weaponList.size()-1;
	}
	
	public void changeWeapon(int newWeaponIndex) {
		if(newWeaponIndex <= -1) {
			newWeaponIndex = this.weaponList.size() - 1;
		} else if(newWeaponIndex >= this.weaponList.size()) {
			newWeaponIndex = 0;
		}
		
		this.weaponIndex = newWeaponIndex;
		this.weapon = weaponList.get(newWeaponIndex);
	}
	
	public void scrollWeaponUp(){
		this.changeWeapon(this.weaponIndex + 1);
	}
	
	public void scrollWeaponDown(){
		this.changeWeapon(this.weaponIndex - 1);
	}
	
	public void setAnimation(String animation) {
		this.animation.setCurrentAnimation(animation);
	}
	
	public void resetShooterTimer() { this.shooterTimer = SHOOTER_TIMER_START_VALUE; }

	// Sets the invincibility timer back to it's default start value
	public void resetInvincibilityTimer(){ this.invincibleTime = INVINCIBILITY_TIMER_START_VALUE; }
	
	public void resetFlashTimer(){ flashTimer = FLASH_TIMER_START_VALUE; }

	@Override
	public List<Entity> attack(Entity target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processMovementTick(Entity target, List<Room> entityList) {
		// TODO Auto-generated method stub
	}
	
	// TODO need to make the jump uniorm, right now it's slower on the down than up
	// TOOD need to change this somehow to account for holding, probably need two methods
	public synchronized void jump() {
		if(this.canJump) {
			this.acceleration.y = 20f;
			this.canJump = false;
			this.canWallJump = false;
			if(this.inAir && this.onWall) {
				this.hasWallJumped  = true;
			}
		}
	}

	
	/**
	 * Actions to be performed when the player is on the ground
	 */
	public void onGround() {
		this.canJump = true;
		this.inAir = false;
		
		this.acceleration.y = 0;
		this.ySpeed = this.minimumYSpeed;
	}
	
	/**
	 * Actions to be performed when the player is in the air
	 */
	public void notOnGround() {
		this.canJump = false;
		this.inAir = true;
	}
	
	/**
	 * Actions to be performed when the player is touching a wall
	 */
	public void onWall() {
		onWall = true;
		if(this.inAir) {
			// TODO need to have a timer for wall jumps
			if(canWallJump) {
				this.canJump = true;
			}
			
			this.minimumYSpeed = this.defaultMinimumYSpeed/2;
		}
	}
	
	/**
	 * Actions to be performed when the player is not touching a wall
	 */
	public void notOnWall() {
		onWall = false;
		if(this.inAir) {
			this.canJump = false;
			this.canWallJump = true;
		}
		this.minimumYSpeed = this.defaultMinimumYSpeed;
	}
	
	/**
	 * Actions to be performed when the player hits a hard ceiling
	 */
	public void hitCeiling() {
		this.ySpeed = 0;
	}

	/* Start Getters/Setters */
	// ----------------------------------------------------------------------------------------------------------//
	
	public Weapon getMeleeWeapon() { return meleeWeapon; }

	public void setMeleeWeapon(Weapon meleeWeapon) { this.meleeWeapon = meleeWeapon; }

	public boolean isCanMeleeAttack() {	return canMeleeAttack; }

	public void setCanMeleeAttack(boolean canMeleeAttack) {	this.canMeleeAttack = canMeleeAttack; }

	public boolean isCanJump() { return canJump; }

	public void setCanJump(boolean canJump) { this.canJump = canJump; }	

	public Texture getTexture() { return texture; }

	public void setTexture(Texture texture) { this.texture = texture; }

	public Weapon getWeapon() { return weapon; }

	public void setWeapon(Weapon weapon) { this.weapon = weapon; }

	public List<Weapon> getWeaponList() { return weaponList; }

	public void setWeaponList(List<Weapon> weaponList) { this.weaponList = weaponList; }

	public int getWeaponIndex() { return weaponIndex; }

	public void setWeaponIndex(int weaponIndex) { this.weaponIndex = weaponIndex; }
	
	public boolean isCanShoot() { return canShoot; }

	public void setCanShoot(boolean canShoot) { this.canShoot = canShoot; }

	public boolean isInAir() { return inAir; }

	public void setInAir(boolean inAir) { this.inAir = inAir; }

	public boolean isOnWall() { return onWall; }

	public void setOnWall(boolean onWall) { this.onWall = onWall; }

	public boolean isHasWallJumped() { return hasWallJumped; }

	public void setHasWallJumped(boolean hasWallJumped) { this.hasWallJumped = hasWallJumped; }
	
	
	
	// ----------------------------------------------------------------------------------------------------------//
	/* End Getters/Setters */
}
