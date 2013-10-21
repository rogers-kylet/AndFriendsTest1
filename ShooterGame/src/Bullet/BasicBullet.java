package Bullet;
import org.lwjgl.opengl.GL11;


public class BasicBullet implements Bullet {

	float x;
	float y;
	float rot;
	int width;
	int height;
	float speed;
	float angle;
	boolean penetrate;
	
	public BasicBullet(){
		this.x = 300;
		this.y = 300;
		this.width = 20;
		this.height = 20;
		this.speed = 10;
		this.angle = 270;
	}
	
	public BasicBullet(float x, float y){
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		this.speed = 10;
		this.angle = 270;
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public boolean penetrate() {
		return penetrate;
	}

	@Deprecated 
	@Override
	public void move() {
		this.move(this.angle);
	}
	
	public void move(float angle){
		this.x += speed * Math.cos(Math.toRadians(angle));
		this.y += speed * Math.sin(Math.toRadians(angle));
	}

	@Override
	public void move(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

		GL11.glColor3f(0.0f, 0.0f, 1.0f);

		
		GL11.glPushMatrix();
		GL11.glTranslatef(x,y,0);
		GL11.glRotatef(rot, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x - this.width/2, y - this.height/2);
			GL11.glVertex2f(x + this.width/2, y - this.height/2);
			GL11.glVertex2f(x + this.width/2, y + this.height/2);
			GL11.glVertex2f(x - this.width/2, y + this.height/2);
		GL11.glEnd();
	GL11.glPopMatrix();
	}
	
	@Override
	public boolean offScreen(){
		if(this.x < 0 || this.x > 800 || this.y < 0 || this.y > 600){
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
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	

}
