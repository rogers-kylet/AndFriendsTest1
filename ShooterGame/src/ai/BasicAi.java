package ai;

import entityMovement.EntityMovement;

public class BasicAi implements Ai {

	EntityMovement entityMovement;

	public BasicAi(){};
	
	public BasicAi(EntityMovement entityMovement) {
		this.entityMovement = entityMovement;
	}
	
	@Override
	public EntityMovement getEntityMovement() {
		return entityMovement;
	}

	@Override
	public void setEntityMovement(EntityMovement entityMovement) {
		this.entityMovement = entityMovement;
	}
	
}
