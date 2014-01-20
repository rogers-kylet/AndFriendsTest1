package entity.Projectile;

import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.Entity;

public class BasicMeleeAttack extends BasicProjectile implements Entity {

	public BasicMeleeAttack(float x, float y, float z, int eid) throws IOException {
		super(x, y, z, eid);
		this.acceleration = new Vector3f(0,0,0);
		this.angle=0;
		this.baseHealth=5f;
		this.health=-1f;
		this.height=30f;
		this.width=120f;
		this.entityType=entityClass.PROJECTILE;
		this.maxHealth=10f;
		this.maxSpeed=10f;
		this.speed=10f;
		this.minimumSpeed=5f;
		this.name="Unnamed Projectile "+eid;
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.distance = 0;
		this.maxDistance = 25;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "sword" + ".png"));
		this.mask = (int) Math.floor(Math.random() * 9);
	}
	
	@Override
	protected void processMovementTick(Entity target) {
		//TOD this conditional should probably be improved, I don't think it stays consistant when stopping moving, so maybe have a facing left/facing right flag?
		if(
				(target.getAngle() <= 360 && 
				target.getAngle() >=270 )
				|| 
				(target.getAngle() >= 0 &&
						target.getAngle() <= 90)) {
			this.x = target.getX() + target.getWidth()/2 + this.getWidth()/2;
		} else {
			this.x = target.getX() - target.getWidth()/2 - this.getWidth()/2;
		}
		this.y = target.getY();
		this.distance += 1;
		
		if(this.distance == this.maxDistance) {
			this.health = 0;
		}
	}
}
