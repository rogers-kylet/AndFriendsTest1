package entity.Projectile;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.BasicEntity;
import entity.Entity;

import room.Room;


public class BasicProjectile extends BasicEntity {

	float maxDistance;
	float distance;
	int mask;
	
	Texture texture;
	
	public BasicProjectile(float x, float y, float z, int eid) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.angle=0;
		this.baseHealth=5f;
		this.health=1f;
		this.height=30f;
		this.width=30f;
		this.entityType=entityClass.PROJECTILE;
		this.maxHealth=10f;
		this.maxSpeed=12f;
		this.speed=12f;
		this.minimumSpeed=5f;
		this.name="Unnamed Projectile "+eid;
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
		this.distance = 0;
		this.maxDistance = 75;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Bullet" + ".png"));
		this.mask = (int) Math.floor(Math.random() * 9);
	}
	
	@Override
	public void render() {
		/*
		GL11.glColor3f(0.0f, 0.0f, 1.0f);

		GL11.glPushMatrix();
			GL11.glTranslatef(this.x,this.y,0);
			GL11.glRotatef(this.rotation, 0f, 0f, 1f);
			GL11.glTranslatef(-this.x, -this.y, 0);
		
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
				GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
				GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
				GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
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
		

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		//GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		//GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		texture.bind(); // or GL11.glBind(texture.getTextureID());

		float width = this.width;
		if((this.angle >= 0 && this.angle <= 90) || this.angle <= 360 && this.angle >= 270) {
			
		} else {
			width = -width;
		}
		
		GL11.glPushMatrix();
	
			// Replace glBegin with vertex buffer
			GL11.glTranslatef(this.x,this.y,0);
			GL11.glRotatef(this.rotation, 0f, 0f, 1f);
			GL11.glTranslatef(-this.x, -this.y, 0);
		
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(this.x - width/2, this.y - this.height/2);
				GL11.glTexCoord2f(this.texture.getWidth(),0);
				GL11.glVertex2f(this.x + width/2, this.y - this.height/2);
				GL11.glTexCoord2f(this.texture.getWidth(),this.texture.getHeight());
				GL11.glVertex2f(this.x + width/2, this.y + this.height/2);
				GL11.glTexCoord2f(0,this.texture.getHeight());
				GL11.glVertex2f(this.x - width/2, this.y + this.height/2);
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_BLEND);

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
		else {
			return false;
		}
	}

	@Override
	protected void processMovementTick(Entity target) {
		this.x += speed * Math.cos(Math.toRadians(angle));
		this.y += speed * Math.sin(Math.toRadians(angle));
		this.distance += 1;
		
		if(this.distance == this.maxDistance) {
			this.health = 0;
		}
	}

	@Override
	public List<Entity> attack(float angle) {
		return null;
	}

	@Override
	public List<Entity> attack(Entity target) throws IOException { return null; }

	public Texture getTexture() { return texture; }

	@Override
	public void setTexture(Texture texture) { this.texture = texture; }

	@Override
	public void processMovementTick(Entity target, List<Room> entityList) {
		// TODO Auto-generated method stub
		
	}

	
}