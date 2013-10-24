package Level;

import java.util.List;

import room.Room;

import MenuItem.MenuItem;

public class BasicLevel implements Level {

	String name = "Test Level 1";
	String type = "Gameplay";
	List<MenuItem> menuItems;
	List<Room> roomList;
	
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
		// TODO Auto-generated method stub
		return null;
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
	
}
