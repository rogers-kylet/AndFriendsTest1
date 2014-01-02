package Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.MenuItem;

import room.Room;


public class BasicMenu implements Level {

	String name;
	String type;
	List<MenuItem> menuItems;
	List<Room> roomList;
	String backgroundMusic;
	
	public BasicMenu() throws IOException{
		name = "BasicMenu";
		type = "Menu";
		backgroundMusic = "intro";
		menuItems = new ArrayList<MenuItem>();
		menuItems.add(new MenuItem(400,400,"StartButton"));
		menuItems.add(new MenuItem(400,500,"ExitButton"));
	}
	
	@Override
	public String getName() { return name; }

	@Override
	public String getType() { return type; }
	
	@Override
	public List<MenuItem> getMenuItems() { return menuItems; }
	
	@Override
	public void setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }
	
	@Override
	public void setType(String type) { this.type = type; }
	
	@Override
	public void setName(String name) { this.name = name; }
	
	@Override
	public void setRoomList(List<Room> roomList) { this.roomList = roomList; }
	
	@Override
	public List<Room> getRoomList() { return this.roomList; }
	
	@Override
	public String getBackgroundMusic() { return backgroundMusic; }
	
	@Override
	public void setBackgroundMusic(String backgroundMusic) { this.backgroundMusic = backgroundMusic; }

	@Override
	public float getxMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setxMin(float xMin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getxMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setxMax(float xMax) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getyMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setyMin(float yMin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getyMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setyMax(float yMax) {
		// TODO Auto-generated method stub
		
	}
	

}
