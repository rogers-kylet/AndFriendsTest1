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
		double distance = Math.sqrt(Math.pow(entity.getX() - relativeEntity.getX(), 2) + Math.pow(entity.getY() - relativeEntity.getY(), 2));
		if(distance > 100){
			float deltaX = relativeEntity.getX() - entity.getX();
			float deltaY = relativeEntity.getY() - entity.getY();
			double newAngle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
			entity.setAngle((float) newAngle);
			
			entity.setX(entity.getX() + entity.getSpeed() * (float)Math.cos(Math.toRadians(entity.getAngle())));
			entity.setY(entity.getY() + entity.getSpeed() * (float)Math.sin(Math.toRadians(entity.getAngle())));
			
			isMoving = true;
		} else if(distance < 100 - 10){
			float deltaX = relativeEntity.getX() - entity.getX();
			float deltaY = relativeEntity.getY() - entity.getY();
			double newAngle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
			entity.setAngle((float) newAngle + 180);
			
			entity.setX(entity.getX() + entity.getSpeed() * (float)Math.cos(Math.toRadians(entity.getAngle())));
			entity.setY(entity.getY() + entity.getSpeed() * (float)Math.sin(Math.toRadians(entity.getAngle())));
			
			isMoving = true;
		} else {
			isMoving = false;
		}
	}

	@Override
	public boolean isMoving() { return isMoving; }

	@Override
	public void setMoving(boolean isMoving) { this.isMoving = isMoving; }
	
	
}
