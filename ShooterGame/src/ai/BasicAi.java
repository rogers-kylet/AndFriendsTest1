package ai;

import java.io.IOException;
import java.util.List;

import entity.Entity;
import entityAttack.EntityAttack;
import entityMovement.EntityMovement;

import room.Room;

/**
 * Basic Implementation for the Artificial Intellegence.
 * @author Kyle Rogers
 *
 */
public class BasicAi implements Ai {

	EntityMovement entityMovement;
	EntityAttack entityAttack;

	public BasicAi(){};
	
	public BasicAi(EntityMovement entityMovement) { 
		this.entityMovement = entityMovement; 
	}
	
	public BasicAi(EntityMovement entityMovement, EntityAttack entityAttack) {
		this.entityMovement = entityMovement;
		this.entityAttack = entityAttack;
	}
	
	@Override
	public void move(Entity target, Entity relatedEntity){
		this.entityMovement.move(target, relatedEntity);
	}
	
	@Override
	public void move(Entity target, Entity relatedEntity, List<Room> entityList) {
		this.entityMovement.move(target, relatedEntity, entityList);
	}
	
	
	@Override
	public List<Entity> attack(Entity target, Entity relatedEntity) throws IOException {
		return this.entityAttack.attack(target, relatedEntity);
	}
	
	@Override
	public List<Entity> processTick(Entity target, Entity relatedEntity, List<Entity> enemyBulletList) {
		return null;
	}
	
	/* Start Getters/Setters */
	
	@Override
	public EntityMovement getEntityMovement() { return entityMovement; }

	@Override
	public void setEntityMovement(EntityMovement entityMovement) { this.entityMovement = entityMovement; }

	@Override
	public EntityAttack getEnemyAttack() { return entityAttack; }

	@Override
	public void setEnemyAttack(EntityAttack enemyAttack) { this.entityAttack = enemyAttack; }

	/* End Getters/Setters */
	
	
}
