package Level;

import java.util.List;

import room.Room;

import entity.MenuItem;

public class BasicLevel implements Level {

	String name = "Test Level 1";
	String type = "Gameplay";
	String backgroundMusic = "level";
	List<MenuItem> menuItems;
	List<Room> roomList;
	LevelGeneration generation;
	float xMin, xMax, yMin, yMax;
	
	public BasicLevel(){
		
	}
	
	@Override
	public String getName() { return name; }
	
	@Override
	public String getType() { return type; }
	
	@Override
	public List<MenuItem> getMenuItems() { return menuItems; }
	
	@Override
	public List<Room> getRoomList() { return roomList; }
	
	@Override
	public void setRoomList(List<Room> roomList) { this.roomList = roomList; }
	
	@Override
	public void setName(String name) { this.name = name; }
	
	@Override
	public void setType(String type) { this.type = type; }
	
	@Override
	public void setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }
	
	@Override
	public String getBackgroundMusic() { return this.backgroundMusic; }
	
	@Override
	public void setBackgroundMusic(String backgroundMusic) { this.backgroundMusic = backgroundMusic; }

	@Override
	public float getxMin() { return xMin; }

	@Override
	public void setxMin(float xMin) { this.xMin = xMin; }

	@Override
	public float getxMax() { return xMax; }

	@Override
	public void setxMax(float xMax) { this.xMax = xMax; }

	@Override
	public float getyMin() { return yMin; }

	@Override
	public void setyMin(float yMin) { this.yMin = yMin; }

	@Override
	public float getyMax() { return yMax; }

	@Override
	public void setyMax(float yMax) { this.yMax = yMax; }
	
	
	
}
