package Level;

import java.util.ArrayList;
import java.util.List;

import room.Room;

import MenuItem.BasicMenuItem;
import MenuItem.MenuItem;

public class BasicMenu implements Level {

	String name;
	String type;
	List<MenuItem> menuItems;
	List<Room> roomList;
	String backgroundMusic;
	
	public BasicMenu(){
		name = "BasicMenu";
		type = "Menu";
		backgroundMusic = "intro";
		menuItems = new ArrayList<MenuItem>();
		menuItems.add(new BasicMenuItem("Start"));
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
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}
	@Override
	public List<Room> getRoomList() {
		return this.roomList;
	}
	@Override
	public String getBackgroundMusic() {
		return backgroundMusic;
	}
	@Override
	public void setBackgroundMusic(String backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}
	

}
