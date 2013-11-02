package entity;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.BasicEntity.entityClass;

public class BasicProjectile extends BasicEntity {

	float maxDistance;
	float distance;
	
	Texture texture;
	
	public BasicProjectile(float x, float y, float z, int eid) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.angle=0;
		this.baseHealth=5f;
		this.health=1f;
		this.height=20f;
		this.width=20f;
		this.entityType=entityClass.PROJECTILE;
		this.maxHealth=10f;
		this.maxSpeed=5f;
		this.speed=5f;
		this.minimumSpeed=5f;
		this.name="Unnamed Projectile "+eid;
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
		this.distance = 0;
		this.maxDistance = 50;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Bullet" + ".png"));
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
		
		Color.white.bind();
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		//texture.bind(); // or GL11.glBind(texture.getTextureID());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glPushMatrix();
	
			// Replace glBegin with vertex buffer
			GL11.glTranslatef(this.x,this.y,0);
			GL11.glRotatef(this.rotation, 0f, 0f, 1f);
			GL11.glTranslatef(-this.x, -this.y, 0);
		
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

}
