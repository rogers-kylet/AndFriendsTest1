package entityMovement;

import entity.Entity;

public abstract class BasicEntityMovement implements EntityMovement {

	/* (non-Javadoc)
	 * @see entityMovement.EntityMovement#move(entity.Entity)
	 */
	@Override
	public void move(Entity entity) {
		entity.setX((float) (entity.getX() + (entity.getSpeed() * Math.sin(Math.toRadians(entity.getAngle())))));
		entity.setY((float) (entity.getY() + (entity.getSpeed() * Math.sin(Math.toRadians(entity.getAngle())))));
	}
	
	/* (non-Javadoc)
	 * @see entityMovement.EntityMovement#move(entity.Entity, entity.Entity)
	 */
	@Override
	public void move(Entity entity, Entity relativeEntity) {
		float deltaX = relativeEntity.getX() - entity.getX();
		float deltaY = relativeEntity.getY() - entity.getY();
		double newAngle = Math.atan2(deltaY, deltaX);
		entity.setAngle((float) Math.toRadians(newAngle));
		this.move(entity);
	}
}
