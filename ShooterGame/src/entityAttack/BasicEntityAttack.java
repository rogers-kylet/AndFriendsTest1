package entityAttack;

import java.util.List;

import entity.Entity;

public class BasicEntityAttack implements EntityAttack {

	boolean canAttack;
	int shootTimer;
	int defaultShootTimerValue;

	public BasicEntityAttack() {
		
	}
	
	@Override
	public boolean isCanAttack() { return canAttack; }

	@Override
	public void setCanAttack(boolean canAttack) { this.canAttack = canAttack; }
	
	@Override
	public List<Entity> attack(Entity target){
		return null;
	}
	
	@Override
	public List<Entity> attack(Entity target, Entity relatedEntity) {
		return null;
	}

	@Override
	public int getShootTimer() { return shootTimer; }

	@Override
	public void setShootTimer(int shootTimer) { this.shootTimer = shootTimer; }

	@Override
	public int getDefaultShootTimerValue() { return defaultShootTimerValue; }

	@Override
	public void setDefaultShootTimerValue(int defaultShootTimerValue) { this.defaultShootTimerValue = defaultShootTimerValue; }
	
	private boolean countdownShootTimer(){
		return countdownShootTimer(1);
	}
	
	private boolean countdownShootTimer(int tick){
		this.shootTimer -= tick;
		
		if(this.shootTimer == 0) {
			canAttack = true;
			return true;
		} else {
			return false;
		}
	}

}
