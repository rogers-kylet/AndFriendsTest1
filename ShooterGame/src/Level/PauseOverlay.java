package Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicBackground;
import entity.MenuItem;
import entity.Player;

public class PauseOverlay{

	List<MenuItem> menuItems;
	List<BasicBackground> backgroundItems;
	
	public PauseOverlay(Player player) throws IOException {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		List<BasicBackground> backgroundItems = new ArrayList<BasicBackground>();
		
		menuItems.add(new MenuItem(player.getX(), player.getY() - 100, "FullScreen"));
		menuItems.add(new MenuItem(player.getX(),player.getY(),"MainMenu"));
		menuItems.add(new MenuItem(player.getX(),player.getY() + 100,"ExitButton"));
		backgroundItems.add(new BasicBackground(player.getX(),player.getY(),0,0, 300, 400));
		this.menuItems = menuItems;
		this.backgroundItems = backgroundItems;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public List<BasicBackground> getBackgroundItems() {
		return backgroundItems;
	}

	public void setBackgroundItems(List<BasicBackground> backgroundItems) {
		this.backgroundItems = backgroundItems;
	}

	
}
