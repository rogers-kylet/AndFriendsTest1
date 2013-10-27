package room;

import java.util.List;

import entity.Entity;

public interface Room {

	void setEnemyList(List<Entity> enemyList);

	List<Entity> getEnemyList();

	void setHeight(float height);

	float getHeight();

	void setWidth(float width);

	float getX();

	void setX(float x);

	float getY();

	void setY(float y);

	float getZ();

	void setZ(float z);

	float getRotation();

	void setRotation(float rotation);

	float getWidth();
	
	List<AnchorPoint> getAnchorPoints();
	
	void setAnchorPoints(List<AnchorPoint> anchorPoints);

}
