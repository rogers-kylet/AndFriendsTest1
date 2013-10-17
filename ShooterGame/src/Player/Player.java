package Player;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;


public class Player {

	/* Constants */
	
		// Default player x start position
		private static final int PLAYER_START_Y = 300;
		// Default player y start position
		private static final int PLAYER_START_X = 400;
		// TODO going to need to change this for the game later
		// Upper limit of playing area in Y direction
		private static final int SCREEN_UPPER_Y_POS = 600;
		// TODO going to need to change this for the game later
		// Lower limit of playing area in Y direction
		private static final int SCREEN_LOWER_Y_POS = 0;
		// TODO going to need to change this for the game later
		// Upper limit of playing area in X direction
		private static final int SCREEN_UPPER_X_POS = 800;
		// TODO Going to need to change this for the game later
		// Lower limit of playin area in Y direction
		private static final int SCREEN_LOWER_X_POS = 0;
		// Default value for shooter timer
		private static final int SHOOTER_TIMER_START_VALUE = 30;
		// Default value for invincibility timer
		private static final int INVINCIBILITY_TIMER_START_VALUE = 60;
		// Default value for flash timer
		private static final int FLASH_TIMER_START_VALUE = 6;
	
	/* Constants End */
	
	/* Numbers Start */
	
		// Player X coordinate
		float playerX;
		// Player Y coordinate
		float playerY;
		// Player rotation
		float playerRotation;
		// Player Width
		int width;
		// Player Height
		int height;
		// Fudge Factor to alter collision slightly
		int collisionFudgeFactor;
		// Player Health
		int health;
		// Ammo for current weapon
		int ammo;
		// Type of current weapon
		int weaponType;
		
	/* Numbers end */

	/* Switches */
		
		// Determines whether the player can shoot
		boolean canShoot;
		// Determines whether the player can be hurt
		boolean canBeHurt;
		// Determines if the player should be displayed
		boolean display;
		// Determines if display should flip
		boolean flashSwitch;
		
	/* Switches End */
	
	/* Timers */
		// Invincibility timer
		int invincibilityTimer;
		// Determines when the player can shoot again
		int shooterTimer;
		// Flash Timer
		int flashTimer;
	/* Timers End */
	
	/* Constructors */
		// Default constuctor that sets player to thge default constant. 
		public Player(){
			this.playerX = PLAYER_START_X;
			this.playerY = PLAYER_START_Y;
			this.health = 5;
			this.canShoot = true;
			this.canBeHurt = true;
			this.display = true;
			this.flashSwitch = false;
			this.width = 50;
			this.height = 50;
			this.collisionFudgeFactor = 2;
		}
		
		// Constructor to specify player start position
		public Player(float x, float y){
			this.playerX = x;
			this.playerY = y;
			this.health = 5;
			this.canShoot = true;
			this.canBeHurt = true;
			this.flashSwitch = false;
			this.display = true;
			this.width = 50;
			this.height = 50;
			this.collisionFudgeFactor = 2;
		}
	
	/* Constructors End */
		
	/* Render Start */
		// Code taken from texture example
		// TODO make the textures actually work
		public void render(Texture texture){
			//TODO figure out optimizations, is this going to be recalled everytime with no cacheing ruining efficiancy? 
			if (display) {
				GL11.glPushMatrix();
					GL11.glBegin(GL11.GL_QUADS);
						GL11.glTexCoord2f(0,0);
						GL11.glVertex2f(100, 100);
						GL11.glTexCoord2f(1,0);
						GL11.glVertex2f(100 + texture.getTextureWidth(), 100);
						GL11.glTexCoord2f(1,1);
						GL11.glVertex2f(100 + texture.getTextureWidth(), 100 + texture.getTextureHeight());
						GL11.glTexCoord2f(0,1);
						GL11.glVertex2f(100, 100+texture.getTextureHeight());
					GL11.glEnd();
				GL11.glPopMatrix();
			}
			if(!canBeHurt) {
				countDownInvincibilityTimer();
			}
			if(!canShoot) {
				countDownShooterTimer();
			}
		}
		
		// Draws the player to the screen
		public void render(){
			if(display){
				GL11.glColor3f(0.0f, 1.0f, 0.0f);
		
				GL11.glPushMatrix();
					GL11.glTranslatef(playerX,playerY,0);
					GL11.glRotatef(playerRotation, 0f, 0f, 1f);
					GL11.glTranslatef(-playerX, -playerY, 0);
					
					GL11.glBegin(GL11.GL_QUADS);
						GL11.glVertex2f(playerX - 25, playerY - 25);
						GL11.glVertex2f(playerX + 25, playerY - 25);
						GL11.glVertex2f(playerX + 25, playerY + 25);
						GL11.glVertex2f(playerX - 25, playerY + 25);
					GL11.glEnd();
				GL11.glPopMatrix();
			}
			if(!canBeHurt) {
				countDownInvincibilityTimer();
			}
			if(!canShoot) {
				countDownShooterTimer();
			}
		}
	
	/* Render End */
	
	/* Timers Start */
	
		// Sets the shooter timer back to it's default start value
		public void resetShooterTimer(){
			shooterTimer = SHOOTER_TIMER_START_VALUE;
		}
		
		// Sets the invincibility timer back to it's default start value
		public void resetInvincibilityTimer(){
			invincibilityTimer = INVINCIBILITY_TIMER_START_VALUE;
		}
		
		public void resetFlashTimer(){
			flashTimer = FLASH_TIMER_START_VALUE;
		}
		
		// Counts down the shooter timer by one, setting can shoot to true when it hits zero
		public void countDownShooterTimer(){
			shooterTimer -= 1;
			if(shooterTimer == 0){
				this.canShoot = true;
			}
		}
		
		// Counts down the invincibility timer by one, setting can shoot to true when it hits zero
		public void countDownInvincibilityTimer(){
			invincibilityTimer -= 1;
			// TODO make this flash less
			countDownFlashTimer();
			
			if(flashSwitch){
				display ^= true;
				flashSwitch = false;
				resetFlashTimer();
			}
			if(invincibilityTimer == 0){
				canBeHurt = true;
				display = true;
			}
		}
		
		// Counts down the flash timer by one, setting the flashSwitch to true
		public void countDownFlashTimer(){
			this.flashTimer -= 1;
			if(this.flashTimer == 0) {
				flashSwitch = true;
			}
		}
		
	/* Timers End */
	
	/* Misc Methods Start */	
		
		// Hurts the player by the given amount of damage
		public void hurtPlayer(int damage) {
			if(canBeHurt){
				this.health = health - damage;
				this.canBeHurt = false;
				resetInvincibilityTimer();
				resetFlashTimer();
			}
		}
	/* Misc Methods End */
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/* Getters and Setters Start */
	
		// Gets the fudge factor used to alter collision detection
		public int getCollisionFudgeFactor() {
			return collisionFudgeFactor;
		}
	
		// Sets the fudge factor used to alter collision detection
		public void setCollisionFudgeFactor(int collisionFudgeFactor) {
			this.collisionFudgeFactor = collisionFudgeFactor;
		}
		
		// Returns players x coordinate
		public float getPlayerX() {
			return playerX;
		}
		
		// Sets the player x coordinate, making sure that the player doesn't leave the screen
		// TODO update checks to be scaleable
		public void setPlayerX(float playerX) {
			if(playerX < SCREEN_LOWER_X_POS){
				this.playerX = SCREEN_LOWER_X_POS;
			}
			else if(playerX > SCREEN_UPPER_X_POS){
				this.playerX = SCREEN_UPPER_X_POS;
			}
			else{
				this.playerX = playerX;
			}
		}
		
		// Returns the player Y coordinate
		public float getPlayerY() {
			return playerY;
		}
		
		// Sets the player y coordinate, making sure that the player doesn't leave the screen
		public void setPlayerY(float playerY) {
			if(playerY < SCREEN_LOWER_Y_POS){
				this.playerY = SCREEN_LOWER_Y_POS;
			}
			else if(playerY > SCREEN_UPPER_Y_POS){
				this.playerY = SCREEN_UPPER_Y_POS;
			}
			else{
				this.playerY = playerY;
			}
		}
		
		// Gets the Width of the Player
		public int getWidth() {
			return width;
		}
	
		// Sets the width of the player
		public void setWidth(int width) {
			this.width = width;
		}
	
		// Gets the height of the player
		public int getHeight() {
			return height;
		}
	
		// Sets the height of the player
		public void setHeight(int height) {
			this.height = height;
		}
	
		// Returns the player health
		public int getHealth() {
			return health;
		}
		
		// Sets the player health
		public void setHealth(int health) {
			this.health = health;
		}
		// Returns the player ammo
		// TODO remove if not needed
		public int getAmmo() {
			return ammo;
		}
		
		// Sets the player ammo
		// TODO remove if not needed
		public void setAmmo(int ammo) {
			this.ammo = ammo;
		}
		
		public int getWeaponType() {
			return weaponType;
		}
		
		public void setWeaponType(int weaponType) {
			this.weaponType = weaponType;
		}
		
		// Checks if the player can shoot
		public boolean isCanShoot() {
			return canShoot;
		}
	
		// Sets whether the player can shoot
		public void setCanShoot(boolean canShoot) {
			this.canShoot = canShoot;
		}
		
		// Returns player rotation
		public float getPlayerRotation() {
			return playerRotation;
		}
	
		// Sets the player rotation
		// TODO should 360 and -360 be reset to 0?
		public void setPlayerRotation(float playerRotation) {
			this.playerRotation = playerRotation;
		}
	
	/* Getters and Setters End */
}
