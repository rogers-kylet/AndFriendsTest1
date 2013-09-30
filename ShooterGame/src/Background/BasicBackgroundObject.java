package Background;
import org.lwjgl.opengl.GL11;


public class BasicBackgroundObject implements BackgroundObject {

	// Object X pos
	float x;
	// Object Y pos
	float y;
	// Object rotation degree
	float rot;
	// Object health
	// TODO do objects need health? Should non-enemy objects with health or other relations be a seperate class? 
	int health;
	
	@Override
	// Draws the object to the screen
	public void render() {
		
		// TODO Figure out why this doesn't work to color, I don't want this code in the engine if it doesn't need to be
		GL11.glColor3f(1.0f, 0.5f, 1.0f);

		GL11.glPushMatrix();
			GL11.glTranslatef(x,y,0);
			GL11.glRotatef(rot, 0f, 0f, 1f);
			GL11.glTranslatef(-x, -y, 0);
			
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x - 50, y - 50);
				GL11.glVertex2f(x + 50, y - 50);
				GL11.glVertex2f(x + 50, y + 50);
				GL11.glVertex2f(x - 50, y + 50);
			GL11.glEnd();
		GL11.glPopMatrix();
	}

	@Override
	// Returns the X pos of the object
	public float getX() {
		return x;
	}

	@Override
	// Sets the X pos of the object
	public void setX(float x) {
		this.x = x;
	}

	@Override
	// Returns the Y pos of the object
	public float getY() {
		return y;
	}

	@Override
	// Sets the Y Pos of the object
	public void setY(float y) {
		this.y = y;
	}

	@Override
	// Returns the rotation degree of the object
	public float getRotation() {
		return rot;
	}

	@Override
	// Sets the rotation degree of the object
	public void setRotation(float rot) {
		this.rot = rot;
	}

	@Override
	// Returns the health of the object
	public int getHealth() {
		return health;
	}

	@Override
	// Sets the health of the object
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	// Moves or updates the objects position based on some other objects position, not sure yet 
	// TODO make work
	public void move(float playerX, float playerY) {
		// TODO still need to figure out whether move is just going to update or also call render
		// this.render();

	}

	@Override
	// Moves or updates te objects position, not sure how yet
	public void move() {
		// TODO still need to figure out whether move is just going to update or also call render
		// this.render();

	}

}
