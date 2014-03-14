package room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import room.roomConfigurations.BasicRoomConfiguration;
import entity.BasicWall;
import entity.Entity;

/**
 * The object used to create a standard room 
 * @author Kyle Rogers
 *
 */
public class BasicRoom implements Room {

	float x, y, z, rotation, width, height;
	
	String type;
	boolean entered;
	
	List<Entity> enemyList;
	List<AnchorPoint> anchorPoints;
	List<Entity> background;
	List<Entity> wallList;
	List<Entity> pickupList;
	
	Room parentRoom;

	// Default Constructor
	public BasicRoom() {
		
	}
	
	// TODO constructor that creates the room based on a given roomConfiguration
	// TODO this needs to be a builder based on other templates in the room config, but for now the straight conversion works
	// Will need to figure out the work flow of moving from config to placement to parsing locations relative to the room itself. 
	// Probably would be best if it could be created with the x/y coordinates already, but the level generator might need the width and height to properly determine x/y coordinates.
	public BasicRoom(BasicRoomConfiguration roomConfig) {
		this.x = roomConfig.getX();
		this.y = roomConfig.getY();
		this.width = roomConfig.getWidth();
		this.height = roomConfig.getHeight();
		this.type = roomConfig.getType();
		this.anchorPoints = roomConfig.getAnchorPoints();
		this.enemyList = roomConfig.getEnemyList();
		this.background = roomConfig.getBackground();
		this.wallList = roomConfig.getWallList();
		this.pickupList = roomConfig.getPickupList();
	}
	
	// Theoretical constructor to take the roomConfig, the x and y location of the anchor point that will be hooked, and the direction that it should move in (up, down, left, right)
	public BasicRoom(BasicRoomConfiguration roomConfig, float x, float y, String direction) {
		
	}
	
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
	
	// Loop through anchor points and place a wall either along the entire expanse or two breaking where the door would be
	// At some point also need o place wall connectors/determine whether it is an inwardly or outwardly facing wall
	@Override
	public void generateWalls() throws IOException{
		// TODO add seperate process/render for walls
		wallList = this.getWallList();
		if(wallList == null) {
			wallList = new ArrayList<Entity>();
		}
		
		for(AnchorPoint anchorPoint: anchorPoints) {
			
			if(anchorPoint.isHooked()) {
				BasicWall background1 = null;
				BasicWall background2 = null;
				if(anchorPoint.getDirection().equals("left")) {
					//background1 = new BasicWall(anchorPoint.getX(),anchorPoint.getY() - this.height/3,0,0, 40, this.height/3);
					//background2 = new BasicWall(anchorPoint.getX(), anchorPoint.getY() + this.height/3, 0, 0, 40, this.height/3);
				} else if(anchorPoint.getDirection().equals("right")) {
					//background1 = new BasicWall(anchorPoint.getX(),anchorPoint.getY() - this.height/3,0,0, 40, this.height/3);
					//background2 = new BasicWall(anchorPoint.getX(), anchorPoint.getY() + this.height/3, 0, 0, 40, this.height/3);
				} else if(anchorPoint.getDirection().equals("up")) {
					background1 = new BasicWall(anchorPoint.getX() - this.width/3,anchorPoint.getY(),0,0, this.width/3, 40);
					background2 = new BasicWall(anchorPoint.getX() + this.width/3, anchorPoint.getY(), 0, 0, this.width/3, 40);
				} else if(anchorPoint.getDirection().equals("down")) {
					background1 = new BasicWall(anchorPoint.getX() - this.width/3,anchorPoint.getY(),0,0, this.width/3, 40);
					background2 = new BasicWall(anchorPoint.getX() + this.width/3, anchorPoint.getY(), 0, 0, this.width/3,40);
				}
				if(background1 != null) {
					background1.setTexture("wall");
					wallList.add(background1);
				}
				if ( background2 != null) {
					background2.setTexture("wall");
					wallList.add(background2);
				}
			} else {
				BasicWall background1 = null;
				if(anchorPoint.getDirection().equals("left")) {
					background1 = new BasicWall(anchorPoint.getX(),anchorPoint.getY(),0,0, 40, this.height);
				} else if(anchorPoint.getDirection().equals("right")) {
					background1 = new BasicWall(anchorPoint.getX(),anchorPoint.getY(),0,0, 40, this.height);
				} else if(anchorPoint.getDirection().equals("up")) {
					background1 = new BasicWall(anchorPoint.getX(),anchorPoint.getY(),0,0, this.width, 40);
				} else if(anchorPoint.getDirection().equals("down")) {
					background1 = new BasicWall(anchorPoint.getX(),anchorPoint.getY(),0,0, this.width, 40);
				}
				background1.setTexture("wall");
				wallList.add(background1);
			}
		}
	}
	
	@Override
	public float getX() { return x; }

	@Override
	public void setX(float x) { this.x = x; }

	@Override
	public float getY() { return y; }

	@Override
	public void setY(float y) { this.y = y; }

	@Override
	public float getZ() { return z; }

	@Override
	public void setZ(float z) { this.z = z; }

	@Override
	public float getRotation() { return rotation; }

	@Override
	public void setRotation(float rotation) { this.rotation = rotation; }

	@Override
	public float getWidth() { return width; }

	@Override
	public void setWidth(float width) { this.width = width; }

	@Override
	public float getHeight() { return height; }

	@Override
	public void setHeight(float height) { this.height = height; }

	@Override
	public List<Entity> getEnemyList() { return enemyList; }

	@Override
	public void setEnemyList(List<Entity> enemyList) { this.enemyList = enemyList; }

	@Override
	public List<AnchorPoint> getAnchorPoints() { return this.anchorPoints; }

	@Override
	public void setAnchorPoints(List<AnchorPoint> anchorPoints) { this.anchorPoints = anchorPoints; }

	@Override
	public boolean isEntered() { return entered; }

	@Override
	public void setEntered(boolean entered) { this.entered = entered; }

	@Override
	public List<Entity> getBackground() { return background; }

	@Override
	public void setBackground(List<Entity> background) { this.background = background; }

	@Override
	public String getType() { return type; }

	@Override
	public void setType(String type) { this.type = type; }

	@Override
	public Room getParentRoom() { return parentRoom; }

	@Override
	public void setParentRoom(Room parentRoom) { this.parentRoom = parentRoom; }

	@Override
	public List<Entity> getWallList() { return wallList; }

	@Override
	public void setWallList(List<Entity> wallList) { this.wallList = wallList; }

	@Override
	public List<Entity> getPickupList() { return pickupList; }

	@Override
	public void setPickupList(List<Entity> pickupList) { this.pickupList = pickupList; }
	
	
}
