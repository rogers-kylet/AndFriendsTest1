package room;

import java.io.IOException;
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

	boolean isEntered();

	void setEntered(boolean entered);

	List<Entity> getBackground();

	void setBackground(List<Entity> background);

	void setType(String type);

	String getType();

	Room getParentRoom();

	void setParentRoom(Room parentRoom);

	boolean roomCollision(Room theRoom);

	void generateWalls() throws IOException;

	void setWallList(List<Entity> wallList);

	List<Entity> getWallList();

	List<Entity> getPickupList();

	void setPickupList(List<Entity> pickupList);

}
