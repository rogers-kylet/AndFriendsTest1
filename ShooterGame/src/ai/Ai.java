package ai;

import java.io.IOException;
import java.util.List;

import entity.Entity;
import entityAttack.EntityAttack;
import entityMovement.EntityMovement;

public interface Ai {

	EntityMovement getEntityMovement();

	void setEntityMovement(EntityMovement entityMovement);

	EntityAttack getEnemyAttack();

	void setEnemyAttack(EntityAttack enemyAttack);

	void move(Entity target, Entity relatedEntity);

	List<Entity> attack(Entity target, Entity relatedEntity) throws IOException;
	
	List<Entity> processTick(Entity target, Entity relatedEntity,
			List<Entity> enemyBulletLIst);

}
