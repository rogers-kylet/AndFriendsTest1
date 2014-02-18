package ai;

import java.io.IOException;
import java.util.List;

import room.Room;

import entity.Entity;
import entityAttack.EntityAttack;
import entityMovement.EntityMovement;

/**
 * The interface for the plug and play artificial intellegence
 * @author Kyle Rogers
 *
 */
public interface Ai {

	EntityMovement getEntityMovement();

	void setEntityMovement(EntityMovement entityMovement);

	EntityAttack getEnemyAttack();

	void setEnemyAttack(EntityAttack enemyAttack);

	void move(Entity target, Entity relatedEntity);
	
	List<Entity> attack(Entity target, Entity relatedEntity) throws IOException;
	
	List<Entity> processTick(Entity target, Entity relatedEntity,
			List<Entity> enemyBulletLIst);

	void move(Entity target, Entity relatedEntity, List<Room> entityList);


}
