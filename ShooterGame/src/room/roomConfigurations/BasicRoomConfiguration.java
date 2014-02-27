package room.roomConfigurations;

import java.util.List;

import room.AnchorPoint;
import entity.Entity;

/**
 * The object used to hold various configurations for a room.
 * @author krogers
 *
 */
public class BasicRoomConfiguration {

	float rotation, width, height;
	
	String type;
	
	List<Entity> enemyList;
	List<AnchorPoint> anchorPoints;
	List<Entity> background;
	List<Entity> wallList;
	List<Entity> pickupList;

	/**
	 * The constructor that should set up the various types of objects that can be within the room.
	 */
	public BasicRoomConfiguration () {
		
	}
	
	/* start getters and setters */
	public float getRotation() { return rotation; }
	public void setRotation(float rotation) { this.rotation = rotation; }
	public float getWidth() { return width; }
	public void setWidth(float width) { this.width = width; }
	public float getHeight() { return height; }
	public void setHeight(float height) { this.height = height; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public List<Entity> getEnemyList() { return enemyList;	}
	public void setEnemyList(List<Entity> enemyList) { this.enemyList = enemyList; }
	public List<AnchorPoint> getAnchorPoints() { return anchorPoints; }
	public void setAnchorPoints(List<AnchorPoint> anchorPoints) { this.anchorPoints = anchorPoints; }
	public List<Entity> getBackground() { return background; }
	public void setBackground(List<Entity> background) { this.background = background; }
	public List<Entity> getWallList() { return wallList; }
	public void setWallList(List<Entity> wallList) { this.wallList = wallList; }
	public List<Entity> getPickupList() { return pickupList; }
	public void setPickupList(List<Entity> pickupList) { this.pickupList = pickupList; }
	
}
