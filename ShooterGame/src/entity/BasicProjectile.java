package entity;

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
		this.maxSpeed=3f;
		this.speed=3f;
		this.minimumSpeed=3f;
		this.name="Unnamed Projectile "+eid;
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
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
