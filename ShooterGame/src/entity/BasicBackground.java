package entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import entity.BasicEntity.entityClass;

public class BasicBackground extends BasicEntity {

	public BasicBackground(float x, float y, float z, int eid, float width, float height) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.height=height;
		this.width=width;
		this.entityType=entityClass.STATIONARY;
		this.name="Unnamed Background "+eid;
		this.scale=1f;
		this.eid = eid;
	}
	
	@Override
	public void render() {
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

}
