package Level;

import java.io.IOException;

public class GameOverScreen extends BasicMenu implements Level {

	public GameOverScreen() throws IOException{
		this.name = "GameOverScreen";
		this.type = "Menu";
		this.backgroundMusic = "gameover";
	}
}
