package ai;

import entityAttack.EntityAttack;
import entityMovement.EntityMovement;

public class BasicAi implements Ai {

	EntityMovement entityMovement;
	EntityAttack enemyAttack;

	public BasicAi(){};
	
	public BasicAi(EntityMovement entityMovement) { this.entityMovement = entityMovement; }
	
	@Override
	public EntityMovement getEntityMovement() { return entityMovement; }

	@Override
	public void setEntityMovement(EntityMovement entityMovement) { this.entityMovement = entityMovement; }

	@Override
	public EntityAttack getEnemyAttack() { return enemyAttack; }

	@Override
	public void setEnemyAttack(EntityAttack enemyAttack) { this.enemyAttack = enemyAttack; }
	
	
}
