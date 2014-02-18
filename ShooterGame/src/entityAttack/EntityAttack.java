package entityAttack;

import java.io.IOException;
import java.util.List;

import entity.Entity;

/**
 * The object used to determine and perform entities attacks. 
 * @author Kyle Rogers
 *
 */
public interface EntityAttack {

	boolean isCanAttack();

	void setCanAttack(boolean canAttack);

	List<Entity> attack(Entity target, Entity relatedEntity) throws IOException;

	int getShootTimer();

	void setShootTimer(int shootTimer);

	int getDefaultShootTimerValue();

	void setDefaultShootTimerValue(int defaultShootTimerValue);

	List<Entity> attack(Entity target);

	boolean countdownShootTimer();
	
	boolean countdownShootTimer(int tick);
}
