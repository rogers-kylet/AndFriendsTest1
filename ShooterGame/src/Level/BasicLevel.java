package Level;

import java.util.List;

import room.Room;

import MenuItem.MenuItem;

public class BasicLevel implements Level {

	String name = "Test Level 1";
	String type = "Gameplay";
	String backgroundMusic = "level";
	List<MenuItem> menuItems;
	List<Room> roomList;
	LevelGeneration generation;
	public BasicLevel(){
		
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getType() {
		return type;
	}
	@Override
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	@Override
	public List<Room> getRoomList() {
		return roomList;
	}
	@Override
	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	@Override
	public String getBackgroundMusic() {
		return this.backgroundMusic;
	}
	@Override
	public void setBackgroundMusic(String backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}
	
}
