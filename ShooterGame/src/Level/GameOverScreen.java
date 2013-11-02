package Level;

import java.io.IOException;
import java.util.ArrayList;

import entity.MenuItem;

public class GameOverScreen extends BasicMenu implements Level {

	public GameOverScreen() throws IOException{
		this.name = "GameOverScreen";
		this.type = "Menu";
		this.backgroundMusic = "gameover";
		
		menuItems = new ArrayList<MenuItem>();
		menuItems.add(new MenuItem(400,400,"StartButton"));
		menuItems.add(new MenuItem(400,500,"ExitButton"));
		menuItems.add(new MenuItem(400,300,"MainMenu"));
	}
}
