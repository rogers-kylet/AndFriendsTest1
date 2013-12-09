package entity;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.BasicEntity.entityClass;

public class BasicPickup extends BasicEntity {

	String pickupType;
	float pickupValue;
	private Texture texture;
	private int mask;
	
	public BasicPickup(float x, float y, float z, int eid, float width,
			float height) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.height=height;
		this.width=width;
		this.entityType=entityClass.STATIONARY;
		this.name="Unnamed Pickup "+eid;
		this.scale=1f;
		this.eid = eid;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Tile" + ".png"));
		this.mask = (int) Math.floor(Math.random() * 9);
	}
	
	@Override
	public void render() {
		
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
		this.texture.bind(); // or GL11.glBind(texture.getTextureID());
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glPushMatrix();
	
			// Replace glBeing with vertex buffer
		
		
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

	@Override
	public List<Entity> attack(float angle) throws IOException {
		// TODO Auto-generated method stub
		return null;
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

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + pickupType + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Texture getTexture() { return texture; }

	public void setTexture(Texture texture) { this.texture = texture; }

	public int getMask() { return mask; }

	public void setMask(int mask) { this.mask = mask; }
	
	public String getPickupType() { return pickupType; }

	public float getPickupValue() { 	return pickupValue; }

	public void setPickupValue(float pickupValue) { this.pickupValue = pickupValue; }

	@Override
	public List<Entity> attack(Entity target) throws IOException { return null; }

}
