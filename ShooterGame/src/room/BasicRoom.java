package room;

import java.util.List;

import entity.Entity;

public class BasicRoom implements Room {

	float x, y, z, rotation, width, height;
	
	String type;
	boolean entered;
	
	List<Entity> enemyList;
	List<AnchorPoint> anchorPoints;
	List<Entity> background;
	
	Room parentRoom;

	@Override
	public boolean roomCollision(Room theRoom) {
		if(
				( theRoom.getX() - theRoom.getWidth() / 2 < ( this.getX() + this.width / 2 ) ) && 
				( theRoom.getX() + theRoom.getWidth() / 2 > ( this.getX() - this.width / 2 ) ) && 
				( theRoom.getY() + theRoom.getHeight() / 2 > ( this.getY() - this.height / 2 ) ) && 
				( theRoom.getY() - theRoom.getHeight() / 2 < ( this.getY() + this.height / 2 ) ) ) {
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getZ() {
		return z;
	}

	@Override
	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public List<Entity> getEnemyList() {
		return enemyList;
	}

	@Override
	public void setEnemyList(List<Entity> enemyList) {
		this.enemyList = enemyList;
	}

	@Override
	public List<AnchorPoint> getAnchorPoints() {
		return this.anchorPoints;
	}

	@Override
	public void setAnchorPoints(List<AnchorPoint> anchorPoints) {
		this.anchorPoints = anchorPoints;
	}

	@Override
	public boolean isEntered() {
		return entered;
	}

	@Override
	public void setEntered(boolean entered) {
		this.entered = entered;
	}

	@Override
	public List<Entity> getBackground() {
		return background;
	}

	@Override
	public void setBackground(List<Entity> background) {
		this.background = background;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public Room getParentRoom() {
		return parentRoom;
	}

	@Override
	public void setParentRoom(Room parentRoom) {
		this.parentRoom = parentRoom;
	}
}
