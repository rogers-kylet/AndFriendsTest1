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

import weapon.BasicWeapon;
import weapon.CircleShot;
import weapon.DoubleShot;
import weapon.MirrorShot;
import weapon.QuadShot;
import weapon.TripleShot;
import weapon.TripleSideShot;
import weapon.Weapon;

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
	
	private Weapon weapon;
	private int weaponIndex;
	private List<Weapon> weaponList;
	
	private Timer frameTimer;
	
	private PlayerAnimation animation;

	public Player(float x, float y, float z, int eid) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
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
		
		this.frameTimer = new BasicTimer(15);
		this.frameTimer.reset();
		
		//TODO need to make a weapon builder that can add the various properties of these weapons
		Weapon weapon = new BasicWeapon();
		Weapon weapon2 = new TripleShot();
		Weapon weapon3 = new MirrorShot();
		Weapon weapon4 = new QuadShot();
		Weapon weapon5 = new CircleShot();
		Weapon weapon6 = new DoubleShot();
		Weapon weapon7 = new TripleSideShot();
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
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
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
		
		if(!canShoot) { countDownShooterTimer(); }
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
		this.x += this.speed * Math.cos(Math.toRadians(angle));
		this.y += this.speed * Math.sin(Math.toRadians(angle));		
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
	
	@Override
	public List<Entity> attack(float angle) throws IOException{
		return this.weapon.attack(angle, this);
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
	
	public boolean isCanShoot() { return canShoot; }

	public void setCanShoot(boolean canShoot) { this.canShoot = canShoot; }
	
	public void resetShooterTimer() { this.shooterTimer = SHOOTER_TIMER_START_VALUE; }

	// Sets the invincibility timer back to it's default start value
	public void resetInvincibilityTimer(){ this.invincibleTime = INVINCIBILITY_TIMER_START_VALUE; }
	
	public void resetFlashTimer(){ flashTimer = FLASH_TIMER_START_VALUE; }

	public Texture getTexture() { return texture; }

	public void setTexture(Texture texture) { this.texture = texture; }

	public Weapon getWeapon() { return weapon; }

	public void setWeapon(Weapon weapon) { this.weapon = weapon; }

	public List<Weapon> getWeaponList() { return weaponList; }

	public void setWeaponList(List<Weapon> weaponList) { this.weaponList = weaponList; }

	public int getWeaponIndex() { return weaponIndex; }

	public void setWeaponIndex(int weaponIndex) { this.weaponIndex = weaponIndex; }

	@Override
	public List<Entity> attack(Entity target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processMovementTick(Entity target, List<Room> entityList) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
