package ai;

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

	List<Entity> attack(Entity target, Entity relatedEntity);

}
