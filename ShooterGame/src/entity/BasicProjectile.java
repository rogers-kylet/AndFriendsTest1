package entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import entity.BasicEntity.entityClass;

public class BasicProjectile extends BasicEntity {

	public BasicProjectile(float x, float y, float z, int eid) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.angle=0;
		this.baseHealth=5f;
		this.health=5f;
		this.height=20f;
		this.width=20f;
		this.entityType=entityClass.PROJECTILE;
		this.maxHealth=10f;
		this.baseHealth=5f;
		this.maxSpeed=5f;
		this.speed=5f;
		this.minimumSpeed=5f;
		this.name="Unnamed Projectile "+eid;
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
	}
	
	@Override
	public void render() {
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
	}

}
