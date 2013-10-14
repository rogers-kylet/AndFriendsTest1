package Level;

import java.util.List;

import MenuItem.MenuItem;

public class BasicLevel implements Level {

	String name = "Test Level 1";
	String type = "Gameplay";
	List<MenuItem> menuItems;
	
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
	
	
}
