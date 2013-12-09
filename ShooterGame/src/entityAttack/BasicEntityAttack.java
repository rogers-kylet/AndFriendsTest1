package entityAttack;

import java.io.IOException;
import java.util.List;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import timer.BasicTimer;
import timer.Timer;
import weapon.BasicWeapon;
import weapon.Weapon;

import entity.BasicProjectile;
import entity.Entity;

public class BasicEntityAttack implements EntityAttack {

	boolean canAttack;
	int shootTimer;
	int defaultShootTimerValue;
	Weapon weapon;
	Timer canShootTimer;

	public BasicEntityAttack() {
		weapon = new BasicWeapon();
		canShootTimer = new BasicTimer((int) (Math.random() * 40 + 60));
		canShootTimer.reset();
		canAttack = false;
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
	public List<Entity> attack(Entity target, Entity relatedEntity) throws IOException {
		if(canAttack) {
			float deltaX = relatedEntity.getX() - target.getX();
			float deltaY = relatedEntity.getY() - target.getY();
			float newAngle = (float) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
			
			canShootTimer.setStartValue((int) (Math.random() * 40 + 60));
			this.canShootTimer.reset();
			canAttack = false;
			
			List<Entity> bulletList = this.weapon.attack(newAngle, target);
			
			for(Entity bullet: bulletList){
				bullet.setTexture(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "enemyBullet" + ".png")));
			}
			return bulletList;
			
		} else {
			canShootTimer.countDown();
			if(canShootTimer.isStopped()) {
				canAttack = true;
			} else {
				canAttack = false;
			}
			return null;
		}
	}

	@Override
	public int getShootTimer() { return shootTimer; }

	@Override
	public void setShootTimer(int shootTimer) { this.shootTimer = shootTimer; }

	@Override
	public int getDefaultShootTimerValue() { return defaultShootTimerValue; }

	@Override
	public void setDefaultShootTimerValue(int defaultShootTimerValue) { this.defaultShootTimerValue = defaultShootTimerValue; }
	
	@Override
	public boolean countdownShootTimer(){ return countdownShootTimer(1); }
	
	@Override
	public boolean countdownShootTimer(int tick){
		this.shootTimer -= tick;
		
		if(this.shootTimer == 0) {
			canAttack = true;
			return true;
		} else { return false; }
	}

}
