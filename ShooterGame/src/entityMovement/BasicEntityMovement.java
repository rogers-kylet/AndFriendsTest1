package entityMovement;

import entity.Entity;

public class BasicEntityMovement implements EntityMovement {

	boolean isMoving;
	
	public BasicEntityMovement() {
		this.isMoving = false;
	}
	
	/* (non-Javadoc)
	 * @see entityMovement.EntityMovement#move(entity.Entity)
	 */
	@Override
	public void move(Entity entity) {
		entity.setX((float) (entity.getX() + (entity.getSpeed() * Math.sin(Math.toRadians(entity.getAngle())))));
		entity.setY((float) (entity.getY() + (entity.getSpeed() * Math.sin(Math.toRadians(entity.getAngle())))));
		isMoving = true;
	}
	
	/* (non-Javadoc)
	 * @see entityMovement.EntityMovement#move(entity.Entity, entity.Entity)
	 */
	@Override
	public void move(Entity entity, Entity relativeEntity) {
		float deltaX = relativeEntity.getX() - entity.getX();
		float deltaY = relativeEntity.getY() - entity.getY();
		double newAngle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
		entity.setAngle((float) newAngle);
		
		entity.setX(entity.getX() + entity.getSpeed() * (float)Math.cos(Math.toRadians(entity.getAngle())));
		entity.setY(entity.getY() + entity.getSpeed() * (float)Math.sin(Math.toRadians(entity.getAngle())));
		
		isMoving = true;
	}

	@Override
	public boolean isMoving() {
		return isMoving;
	}

	@Override
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	
}
