package entityMovement;

import entity.Entity;

public interface EntityMovement {

	/**
	 * Basic Movement Function, moves the given entity in the direction of its angle based on its speed
	 * 
	 * @param entity The entity to be moved
	 */
	public abstract void move(Entity entity);

	/**
	 * Basic Movement Function, moves the given entity in the direction on the given relative entity
	 * @param entity The entity to be moved
	 * @param relativeEntity The entity to be moved towards
	 */
	public abstract void move(Entity entity, Entity relativeEntity);

}