package Level;

import java.util.List;

import MenuItem.MenuItem;

public interface Level {

	/**
	 * Gets the name of the level
	 * @return The name of the level.
	 */
	String getName();
	
	/**
	 * Gets the type of the level (menu screen, gameplay level etc)
	 * @return The type of the level
	 */
	String getType();
	
	/**
	 * For levels with menu items, gets the list of menu items
	 * @return The list of menu items for the level
	 */
	List<MenuItem> getMenuItems();
}
