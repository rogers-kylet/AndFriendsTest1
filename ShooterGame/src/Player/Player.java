package Player;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;


public class Player {

	// Default player x start position
	private static final int PLAYER_START_Y = 300;
	// Default player y start position
	private static final int PLAYER_START_X = 400;
	// Upper limit of playing area in Y direction
	private static final int SCREEN_UPPER_Y_POS = 600;
	// Lower limit of playing area in Y direction
	private static final int SCREEN_LOWER_Y_POS = 0;
	// Upper limit of playing area in X direction
	private static final int SCREEN_UPPER_X_POS = 800;
	// Lower limit of playin area in Y direction
	private static final int SCREEN_LOWER_X_POS = 0;
	// Default value for shooter timer
	private static final int SHOOTER_TIMER_START_VALUE = 30;
	// Player X coordnate
	float playerX;
	// Player Y coordinate
	float playerY;
	// Player rotation
	float playerRotation;
	// Determines whether the player can shoot
	boolean canShoot;
	// Determines when the player can shoot again
	int shooterTimer;	
	// Player Health
	int health;
	// Ammo for current weapon
	int ammo;
	// Type of current weapon
	int weaponType;


	// Default constuctor that sets player to thge default constant. 
	public Player(){
		playerX = PLAYER_START_X;
		playerY = PLAYER_START_Y;
		canShoot = true;
	}
	
	// Constructor to specify player start position
	public Player(float x, float y){
		playerX = x;
		playerY = y;
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
	
	// Code taken from texture example
	// TODO make the textures actually work
	public void render(Texture texture){
		//TODO figure out optimizations, is this going to be recalled everytime with no cacheing ruining efficiancy? 

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
	
	// Draws the player to the screen
	public void render(){
		
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
	
	public void resetShooterTimer(){
		shooterTimer = SHOOTER_TIMER_START_VALUE;
	}
	
	public void countDownShooterTimer(){
		shooterTimer -= 1;
		if(shooterTimer == 0){
			this.canShoot = true;
		}
	}
}
