package Enemy;
import org.lwjgl.opengl.GL11;

import Bullet.Bullet;
import Player.Player;


public class BasicEnemy implements Enemy {
	
	// Enemy X coordinate 
	float x;
	// Enemy Y coordinate
	float y;
	// Enemy rotation degree
	float rot;
	// Enemy Health (if needed)
	int health;
	// The width of the enemy
	int width;
	// The height of the enemy
	int height;
	
	public BasicEnemy(){
		this.x = 400;
		this.y = 650;
		this.width = 60;
		this.height = 60;
	}
	
	public BasicEnemy(float x, float y){
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 60;
	}
	@Override
	// Draws the enemy on the screen
	// TODO add texture support
	public void render() {
		
		GL11.glColor3f(1.0f, 0.0f, 0.0f);

		GL11.glPushMatrix();
			GL11.glTranslatef(this.x,this.y,0);
			GL11.glRotatef(rot, 0f, 0f, 1f);
			GL11.glTranslatef(-this.x, -this.y, 0);
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
				GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
				GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
				GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
			GL11.glEnd();
		GL11.glPopMatrix();
	}

	@Override
	// Returns X coordinate
	public float getX() {
		return this.x;
	}

	@Override
	// Changes X coordinate
	public void setX(float x) {
		this.x = x;
	}

	@Override
	// Returns Y coordinate
	public float getY() {
		return this.y;
	}

	@Override
	// Changes Y coordinate
	public void setY(float y) {
		this.y = y;
	}

	@Override
	// Returns rotation degree
	public float getRotation() {
		return this.rot;
	}

	@Override
	// Sets rotation degree
	public void setRotation(float rot) {
		this.rot = rot;
	}

	@Override
	// Returns health
	public int getHealth() {
		return this.health;
	}

	@Override
	// Sets health
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	//Moves related to player (or any other objects) position
	public void move(float playerX, float playerY) {
		// TODO should move call render?
		//this.render();
		
	}

	@Override
	//Move when relation to other objects isn't important
	public void move() {
		//TODO should move call render?
		//this.render();
		this.y += 1;
	}

	// TODO add player width/height to the detection
	@Override
	public boolean collidWithPlayer(Player player) {
		if(
				( player.getPlayerX() + player.getCollisionFudgeFactor() - player.getWidth() / 2 < ( this.x + this.width / 2 ) ) && 
				( player.getPlayerX() - player.getCollisionFudgeFactor() + player.getWidth() / 2 > ( this.x - this.width / 2 ) ) && 
				( player.getPlayerY() - player.getCollisionFudgeFactor() + player.getHeight() / 2 > ( this.y - this.height / 2 ) ) && 
				( player.getPlayerY() + player.getCollisionFudgeFactor() - player.getHeight() / 2 < ( this.y + this.height / 2 ) ) ){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}

	@Override
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}

	@Override
	public boolean collidWithBullet(Bullet bullet) {
		if(
				( bullet.getX() - bullet.getWidth() / 2 < ( this.getX() + this.width / 2 ) ) && 
				( bullet.getX() + bullet.getWidth() / 2 > ( this.getX() - this.width / 2 ) ) && 
				( bullet.getY() + bullet.getHeight() / 2 > ( this.getY() - this.height / 2 ) ) && 
				( bullet.getY() - bullet.getHeight() / 2 < ( this.getY() + this.height / 2 ) ) ) {
			return true;
		}
		else {
			return false;
		}
	}

}
