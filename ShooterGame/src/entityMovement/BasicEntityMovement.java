package entityMovement;

import java.util.List;

import room.Room;

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
		if(distance > 150){
			float deltaX = relativeEntity.getX() - entity.getX();
			float deltaY = relativeEntity.getY() - entity.getY();
			double newAngle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
			entity.setAngle((float) newAngle);
			
			entity.setX(entity.getX() + entity.getSpeed() * (float)Math.cos(Math.toRadians(entity.getAngle())));
			entity.setY(entity.getY() + entity.getSpeed() * (float)Math.sin(Math.toRadians(entity.getAngle())));
			
			isMoving = true;
		} else if(distance < 150 - 10){
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
	
	@Override public void move(Entity entity, Entity relativeEntity, List<Room> entityList) {
		double distance = Math.sqrt(Math.pow(entity.getX() - relativeEntity.getX(), 2) + Math.pow(entity.getY() - relativeEntity.getY(), 2));
		if(distance > 150){
			float deltaX = relativeEntity.getX() - entity.getX();
			float deltaY = relativeEntity.getY() - entity.getY();
			float newAngle = (float) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
			
			processAngle(entity, relativeEntity, entityList, newAngle);
			
			isMoving = true;
		} else if(distance < 150 - 10){
			float deltaX = relativeEntity.getX() - entity.getX();
			float deltaY = relativeEntity.getY() - entity.getY();
			float newAngle = (float) (Math.atan2(deltaY, deltaX) * 180 / Math.PI);
			
			processAngle(entity, relativeEntity, entityList, newAngle + 180);
			
			isMoving = true;
		} else {
			isMoving = false;
		}
	}

	@Override
	public boolean isMoving() { return isMoving; }

	@Override
	public void setMoving(boolean isMoving) { this.isMoving = isMoving; }
	
	public void processAngle(Entity entity, Entity relativeEntity, List<Room> entityList, float newAngle) {
		entity.setAngle(newAngle);
		
		float oldX = entity.getX(), oldY = entity.getY();
		
		entity.setX(entity.getX() + entity.getSpeed() * (float)Math.cos(Math.toRadians(entity.getAngle())));
		entity.setY(entity.getY() + entity.getSpeed() * (float)Math.sin(Math.toRadians(entity.getAngle())));
		
		newAngle = -1;
		boolean xChange = false, yChange = false;
		
		for(Room room: entityList) {
			for(Entity wall: room.getWallList()) {
				if(wall.collisionDetection(entity)) {
					
					if(entity.getAngle() == 0 || entity.getAngle() == 90 || entity.getAngle() == 180 || entity.getAngle() == 270) {
						entity.setX(oldX);
						entity.setY(oldY);

						//System.out.println(entity.getAngle());
					} else if(entity.getAngle() > 0 && entity.getAngle() < 90){
						//TODO refactor when non-squares come into play
						// TODO change from reseting to oldX oldY to reseting both and switching the angle to preserve momentum
						if(entity.getY() + entity.getHeight()/2 > wall.getY() - wall.getHeight()/2 
								&& oldY + entity.getHeight()/2 < wall.getY() - wall.getHeight()/2){
							//entity.setY(oldY);
							yChange = true;
							newAngle = 0;
						}
						if(entity.getX() + entity.getWidth()/2 > wall.getX() - wall.getWidth()/2 
								&& oldX + entity.getWidth()/2 < wall.getX() - wall.getWidth()/2){
							//entity.setX(oldX);
							xChange = true;
							newAngle = 90;
						}
					} else if(entity.getAngle() > 90  && entity.getAngle() < 180) {
						if(entity.getY() + entity.getHeight()/2 > wall.getY() - wall.getHeight()/2 
								&& oldY + entity.getHeight()/2 < wall.getY() - wall.getHeight()/2){
							//entity.setY(oldY);
							yChange = true;
							newAngle = 180;
						}
						if(entity.getX() - entity.getWidth()/2 < wall.getX() + wall.getWidth()/2 
								&& oldX - entity.getWidth()/2 > wall.getX() + wall.getWidth()/2){
							//entity.setX(oldX);
							xChange = true;
							newAngle = 90;
						}
					} else if(entity.getAngle() > 180 && entity.getAngle() < 270) {
						if(entity.getY() - entity.getHeight()/2 < wall.getY() + wall.getHeight()/2 
								&& oldY - entity.getHeight()/2 > wall.getY() + wall.getHeight()/2){
							//entity.setY(oldY);
							yChange = true;
							newAngle = 180;
						}
						if(entity.getX() - entity.getWidth()/2 < wall.getX() + wall.getWidth()/2 
								&& oldX - entity.getWidth()/2 > wall.getX() + wall.getWidth()/2){
							//entity.setX(oldX);
							xChange = true;
							newAngle = 270;
						}
					} else if (entity.getAngle() > 270 && entity.getAngle() < 360) {
						if(entity.getY() - entity.getHeight()/2 < wall.getY() + wall.getHeight()/2 
								&& oldY - entity.getHeight()/2 > wall.getY() + wall.getHeight()/2){
							//entity.setY(oldY);
							yChange = true;
							newAngle = 0;
						}
						if(entity.getX() + entity.getWidth()/2 > wall.getX() - wall.getWidth()/2 
								&& oldX + entity.getWidth()/2 < wall.getX() - wall.getWidth()/2){
							//entity.setX(oldX);
							xChange = true;
							newAngle = 270;
						}
					}
				}
			}
		}
		if(xChange && yChange) {
			entity.setX(oldX);
			entity.setY(oldY);
		} else if(newAngle != -1) {
			entity.setX(oldX);
			entity.setY(oldY);
			processAngle(entity, relativeEntity, entityList, newAngle);
		}
	}
	
}
