package entityAttack;

import java.util.List;

import entity.Entity;

public interface EntityAttack {

	boolean isCanAttack();

	void setCanAttack(boolean canAttack);

	List<Entity> attack(Entity target, Entity relatedEntity);

	int getShootTimer();

	void setShootTimer(int shootTimer);

	int getDefaultShootTimerValue();

	void setDefaultShootTimerValue(int defaultShootTimerValue);

	List<Entity> attack(Entity target);

	boolean countdownShootTimer();
	
	boolean countdownShootTimer(int tick);
}
