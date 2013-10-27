package room;

import java.util.List;

import entity.Entity;

public abstract class BasicRoom implements Room {

	float x, y, z, rotation, width, height;
	
	List<Entity> enemyList;
	List<AnchorPoint> anchorPoints;

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
}
