package Bullet;
import org.lwjgl.opengl.GL11;


public class BasicBullet implements Bullet {

	float x;
	float y;
	float rot;
	boolean penetrate;
	
	public BasicBullet(){
		this.x = 300;
		this.y = 300;
	}
	
	public BasicBullet(float x, float y){
		this.x = x;
		this.y = y;
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

	@Override
	public void move() {
		this.y = y+10;
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
			GL11.glVertex2f(x - 10, y - 10);
			GL11.glVertex2f(x + 10, y - 10);
			GL11.glVertex2f(x + 10, y + 10);
			GL11.glVertex2f(x - 10, y + 10);
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

}
