package entity;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import room.Room;

public class BasicBackground extends BasicEntity {

	protected Texture texture;
	int mask;
	
	public BasicBackground(float x, float y, float z, int eid, float width, float height) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.height=height;
		this.width=width;
		this.entityType=entityClass.STATIONARY;
		this.name="Unnamed Background "+eid;
		this.scale=1f;
		this.eid = eid;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Tile" + ".png"));
		this.mask = (int) Math.floor(Math.random() * 9);
	}
	
	@Override
	public void render() {
		
		/*
		GL11.glColor3f(0.2f, 0.4f, 0.6f);

		GL11.glPushMatrix();
			GL11.glTranslatef(this.x,this.y,0);
			GL11.glRotatef(this.rotation, 0f, 0f, 1f);
			GL11.glTranslatef(-this.x, -this.y, 0);
			
			//TODO fudged by 1 to show room seperation
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(this.x - this.width/2 + 1, this.y - this.height/2 + 1);
				GL11.glVertex2f(this.x + this.width/2 - 1, this.y - this.height/2 + 1);
				GL11.glVertex2f(this.x + this.width/2 - 1, this.y + this.height/2 - 1);
				GL11.glVertex2f(this.x - this.width/2 + 1, this.y + this.height/2 - 1);
			GL11.glEnd();
		GL11.glPopMatrix();
		*/
		switch(mask) {
		case 1:
			Color.white.bind();
			break;
		case 2:
			Color.blue.bind();
			break;
		case 3:
			Color.cyan.bind();
			break;
		case 4:
			Color.green.bind();
			break;
		case 5:
			Color.magenta.bind();
			break;
		case 6:
			Color.orange.bind();
			break;
		case 7:
			Color.pink.bind();
			break;
		case 8:
			Color.red.bind();
			break;
		case 9:
			Color.yellow.bind();
			break;
		default:
			Color.white.bind();
			break;
		}
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glPushMatrix();
	
			// Replace glBeing with vertex buffer
		
		
			GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0,0);
					GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
					GL11.glTexCoord2f(10,0);
					GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
					GL11.glTexCoord2f(10,10);
					GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
					GL11.glTexCoord2f(0,10);
					GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
			GL11.glEnd();
			
		GL11.glPopMatrix();

	}

	@Override
	protected boolean processCollisionTick(Entity target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processMovementTick(Entity target) {
		// TODO Auto-generated method stub

	}

	public Texture getTexture() { return texture; }

	public void setTexture(String textureName) throws IOException {
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + textureName + ".png"));;
	}

	@Override
	public List<Entity> attack(float angle) {
		return null;
	}

	@Override
	public List<Entity> attack(Entity target) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMovementTick(Entity target, List<Room> entityList) {
		// TODO Auto-generated method stub
		
	}
	
	

}
