package Level;

import java.util.ArrayList;
import java.util.List;

import MenuItem.BasicMenuItem;
import MenuItem.MenuItem;

public class BasicMenu implements Level {

	String name;
	String type;
	List<MenuItem> menuItems;
	
	public BasicMenu(){
		name = "BasicMenu";
		type = "Menu";
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

}
