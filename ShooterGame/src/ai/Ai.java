package ai;

import entityAttack.EntityAttack;
import entityMovement.EntityMovement;

public interface Ai {

	EntityMovement getEntityMovement();

	void setEntityMovement(EntityMovement entityMovement);

	EntityAttack getEnemyAttack();

	void setEnemyAttack(EntityAttack enemyAttack);

}
