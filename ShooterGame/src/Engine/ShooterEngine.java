package Engine;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import entity.BasicBackground;
import entity.BasicProjectile;
import entity.Entity;
import entity.Player;
import entity.MenuItem;

import room.Room;

import GameState.GameState;
import GameState.ShooterGameState;
import Level.BasicMenu;
import Level.GameOverScreen;
import Level.Level;
import Level.LevelGeneration;
import Level.PauseOverlay;


public class ShooterEngine {

	/* position of quad */
	float x = 400, y = 300;
	
	// TODO just used to give some animation to the player object to make it feel more than just squares moving around, remove
	/* angle of quad roation */
	float rotation = 0;
	
	/* time at last frame */
	long lastFrame;
	
	/* frames per second */
	int fps;
	/* last fps time */
	long lastFPS;
	
	/* is Vsync Enabled */
	boolean vsync;
	
	// Resolution Holders 
	int resolutionWidth;
	int resolutionHeight;
	
	// temp for testing purposes
	int enemyTimer;
	
	// GameState Object
	GameState gameState;
	
	//Player Object
	Player player;

	// List that stores all active bullet objects
	List<Entity> bulletList;
	// List that stores all active enemy objects
	List<Entity> enemyList;
	// List that storse all active menu items
	List<MenuItem> menuItemList;
	
	// The current level
	Level level;
	
	TrueTypeFont font;
	
	Audio currentMusic;
	
	Map<String, Audio> sfxMap;
	
	//TODO set this in a config, currently used for font rendering
	boolean antiAlias = false;
	
	boolean pause = false;
	
	int pauseTimer = 0;
	int pauseTimerStartValue = 10;
	
	PauseOverlay pauseOverlay;
	
	public void start() throws IOException{
		try{
			// TODO figure out a better way to do this, save settings to file, change in game etc etc etc
			resolutionWidth = 800;
			resolutionHeight = 600;
			Display.setDisplayMode(new DisplayMode(resolutionWidth, resolutionHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		initGL(); // init OpenGL
		getDelta(); // call once before loop to initilise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		
		//TODO weird config problem not getting recognized
		gameState = new ShooterGameState();
		
		//Initialize player
		player = new Player(400, 300, 0, 0);
		
		//Temp for testing purposes
		bulletList = new ArrayList<Entity>();
		// Temp for testing purposes
		enemyList = new ArrayList<Entity>();
		
		enemyTimer = 60;
		
		changeLevel("Menu");
		
		playMusic(level.getBackgroundMusic());
		
		sfxMap = new HashMap<String, Audio>();
		//TODO temp logic to load test sfxMap, names should be stored in entities 
		try {
			sfxMap.put("shot", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/sfx/" + "shot" + ".wav")));
			sfxMap.put("enemyhit", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/sfx/" + "enemyhit" + ".wav")));
			sfxMap.put("playerhit", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/sfx/" + "playerhit" + ".wav")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO temp code for displaying the ugly unusable fonts
		Font theFont = new Font("Times New Roman", Font.PLAIN, 24);
		font = new TrueTypeFont(theFont, antiAlias);
		
		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			
			update(delta);
			renderGL();
			
			Display.update();
			Display.sync(60); // cap fps to 60 fps
		}
		
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}
	
	public void update(int delta) throws IOException{
		
		//TODO change to a switch
		if(level.getType().equals("Gameplay")){
			if(!pause) {
				//rotate quad
				// TODO remove this code, rotation should be done, being left for now becuase it makes the basic tests more fun to see movement
				rotation += 0.15f * delta;
			
				//BEGIN COMPLETED REFACTOR
				//------------------------
				
				//movementDelta is direction, in degrees, the player is currently moving.
				//TODO: Add movement amortization
				int movementDelta = -1;
				
				//Handle movement angle calculations
				//Angle of movement is read right to left as description above
				
				//Checks if player is moving up, up-left, or up-right respectively.
				if(Keyboard.isKeyDown(Keyboard.KEY_W)) movementDelta = (Keyboard.isKeyDown(Keyboard.KEY_D)?315:(Keyboard.isKeyDown(Keyboard.KEY_A)?215:270));
				//Checks if player is moving left or down-left.
				else if(Keyboard.isKeyDown(Keyboard.KEY_A)) movementDelta = (Keyboard.isKeyDown(Keyboard.KEY_S)?135:180);
				//Checks if player is down or down-right.
				else if(Keyboard.isKeyDown(Keyboard.KEY_S)) movementDelta = (Keyboard.isKeyDown(Keyboard.KEY_D)?45:90);
				//Checks if player is moving right
				else if(Keyboard.isKeyDown(Keyboard.KEY_D)) movementDelta = 0;
				
				//Attempt movement if any keys were pressed
				if(movementDelta>-1) {
					player.setAngle(movementDelta);
					player.move();
					for(Room room: this.level.getRoomList()) {
						if(!room.isEntered()) {
							if(
									( player.getX() - player.getWidth() / 2 < ( room.getX() + room.getWidth() / 2 ) ) && 
									( player.getX() + player.getWidth() / 2 > ( room.getX() - room.getWidth() / 2 ) ) && 
									( player.getY() + player.getHeight() / 2 > ( room.getY() - room.getHeight() / 2 ) ) && 
									( player.getY() - player.getHeight() / 2 < ( room.getY() + room.getHeight() / 2 ) ) ) {
								for(Entity enemy : room.getEnemyList()) {
									this.enemyList.add(enemy);
								}
								room.setEntered(true);
							}
						}
					}
				}
				
				//shotFireDelta - Direction shot should fire, if allowed.
				int shotFireDelta = -1;
				
				//Handle firing angle calculations
				//Angle of firing is read right to left as description above
				
				//Checks if shot should move up, up-left, or up-right respectively.
				if(Keyboard.isKeyDown(Keyboard.KEY_UP)) shotFireDelta = (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)?315:(Keyboard.isKeyDown(Keyboard.KEY_LEFT)?215:270));
				//Checks if shot should move left or down-left.
				else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) shotFireDelta = (Keyboard.isKeyDown(Keyboard.KEY_DOWN)?135:180);
				//Checks if shot should move down or down-right.
				else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) shotFireDelta = (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)?45:90);
				//Checks if shot should move right
				else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) shotFireDelta = 0;
				
				//Attempt to fire shot if direction is chosen
				if(shotFireDelta>-1){
					
					//Check player shot cooldown
					if(player.isCanShoot()){
						
						//Add bullet to scene
						BasicProjectile bullet = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
						bullet.setAngle(shotFireDelta);
						bulletList.add(bullet);
						sfxMap.get("shot").playAsSoundEffect(1.0f, 1.0f, false);
						// Stop the player from shooting again and reset the bullet timer
						player.setCanShoot(false);
						player.resetShooterTimer();
					}
				}
				
				//----------------------
				//END COMPLETED REFACTOR
				
				while(Keyboard.next()){
					if(Keyboard.getEventKeyState()){
						// Pres "F" to set the game to full screen mode
						// TODO probably remove from button command and put in an options menu
						if(Keyboard.getEventKey() == Keyboard.KEY_F){
							setDisplayMode(resolutionWidth,resolutionHeight, !Display.isFullscreen());
						}
						// Press V to toggle vsync
						// TODO remove from keyboard command and set in options
						else if (Keyboard.getEventKey() == Keyboard.KEY_V) {
							vsync = !vsync;
							Display.setVSyncEnabled(vsync);
						} else if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && pauseTimer == 0){
							if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
								pauseTimer = pauseTimerStartValue;
								this.pause = true;
								this.pauseOverlay = new PauseOverlay(player);
							}
						}
					}
				}
			} else {
				while(Keyboard.next()) {
					if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && pauseTimer == 0) {
						if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
							this.pause = false;
							pauseTimer = pauseTimerStartValue;
						}
					}
				}
				
				if(Mouse.isButtonDown(0)) {
					int mouseX = Math.abs(Mouse.getX());
					int mouseY = Math.abs(resolutionHeight - Mouse.getY());
					for(Iterator<MenuItem> menuIt = pauseOverlay.getMenuItems().iterator(); menuIt.hasNext();){
						MenuItem menuItem = menuIt.next();
						//TODO might just want to project pause overlay to avoid this ugly calculation, might not be worth the effort though depending on how things work out
						if(menuItem.mouseClick(Math.round(mouseX + player.getX() - resolutionWidth / 2), Math.round(mouseY + player.getY() - resolutionHeight / 2))) {
							if(menuItem.getButtonAction() == "StartButton") {
								changeLevel("Gameplay");
								pause = false;
							} else if(menuItem.getButtonAction() == "MainMenu") {
								changeLevel("Menu");
								pause = false;
							} else if (menuItem.getButtonAction() == "ExitButton") {
								Display.destroy();
								System.exit(0);
							}
						}
					}
				}
				
			}
		}
		
		else if(level.getType().equals("Menu")){
			
			while(Keyboard.next()){
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
					changeLevel("Gameplay");
				}
			}
			
			if(Mouse.isButtonDown(0)) {
				int mouseX = Math.abs(Mouse.getX());
				int mouseY = Math.abs(resolutionHeight - Mouse.getY());
				for(Iterator<MenuItem> menuIt = menuItemList.iterator(); menuIt.hasNext();){
					MenuItem menuItem = menuIt.next();
					if(menuItem.mouseClick(mouseX, mouseY)) {
						if(menuItem.getButtonAction() == "StartButton") {
							changeLevel("Gameplay");
						} else if (menuItem.getButtonAction() == "ExitButton") {
							Display.destroy();
							System.exit(0);
						}
					}
				}
			}
			
		}
		
		// Hack to prevent multiple pause clicks
		// TODO shouldn't be needed, should probably be able to make this work in the key event holding somehow
		if(pauseTimer > 0){pauseTimer--;}
		
		updateFPS(); // update FPS Counter
	}
	
	/*
	 * Set the display mode to be used
	 * 
	 * @param width The width of the display required
	 * @param heigth The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	//TODO comment and make it better
	//TODO make it work for multiple resolution
	//TODO make fullscreen be able to go wide or have the bars if possible
	public void setDisplayMode(int width, int height, boolean fullscreen){
		// return if requested DisplayMode is already set 
		if(
			(Display.getDisplayMode().getWidth() == width) && 
			(Display.getDisplayMode().getHeight() == height) && 
			(Display.isFullscreen() == fullscreen)){ 
				return;
				}
		try{
			DisplayMode targetDisplayMode = null;
			
			if(fullscreen){
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for(int i=0; i<modes.length; i++){
					DisplayMode current = modes[i];
					
					if((current.getWidth() == width) && (current.getHeight() == height)) {
						if((targetDisplayMode == null || (current.getFrequency() >= freq))){
							if((targetDisplayMode == null || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))){
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
						
						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else{
				targetDisplayMode = new DisplayMode(width, height);
			}
			
			if(targetDisplayMode == null){
				System.out.println("Failed to find value mode: "+width+"x"+height+"fs="+fullscreen);
				return;
			}
			
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		
		} catch(LWJGLException e){
			System.out.println("Unable to setup mode " + width + "x" + height + "fullscreen=" + fullscreen + e);
		}
	}
	
	/*
	 * Calculate how many milliseconds have passed
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta(){
		long time = getTime();
		int delta = (int)(time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	
	/*
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/*
	 * Calculate the FPS and set it in the title bar
	 */
	//TODO change title display to something meaningful
	public void updateFPS(){
		if(getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	// Used to initialize opengl to work for 2d graphics
	// TODO specific comments as to what each does
	public void initGL(){
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);                    
 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
        GL11.glClearDepth(1);                                       
 
        //GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
 
        //GL11.glViewport(0,0,800,600);
        
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glOrtho(0, resolutionWidth, resolutionHeight, 0, -1, 1);
			//GL11.glOrtho(0, 800, 6, 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
	}
	
	// Main render method
	// TODO figure out the nice way to render everything
	public void renderGL() throws IOException{
		
		GL11.glLoadIdentity();
		GL11.glTranslatef(0f, 0f, 0f);
		
		if(gameState.isCameraFollow()){
			GL11.glTranslatef(-player.getX()+(resolutionWidth / 2), -player.getY()+(resolutionHeight / 2), 0);
		}
		
		GL11.glPushMatrix();
			// Clear the screen adn the deph buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			// Initial color so pixels can be colored in individual classes, probably wont be needed once textures are being used
			GL11.glColor3f(0.0f, 0.0f, 0.0f);
	
			//TODO change to a switch
			if(level.getType().equals("Gameplay")){
				if(!pause) {

					for(Room room : this.level.getRoomList()) {
						for(Entity background : room.getBackground()) {
							renderEntity(background);
						}
					}
					
			
					// Loop through the list of active bullets 
					for(Iterator<Entity> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
						Entity bullet = bulletIt.next();
							//TODO add logic to remove the bullet once it has traveled x amount of distance
							bullet.move();
							renderEntity(bullet);
							
							if(bullet.getHealth() < 1) {
								bulletIt.remove();
							}
		
					}
					
					//GL11.glColor3f(1.0f, 0.0f, 0.0f);
			
					//TODO make collision detection general for any enemy/bullet size
					for(Iterator<Entity> enemyIt = enemyList.iterator(); enemyIt.hasNext();){
						Entity enemy = enemyIt.next();
						// Loop through each bullet (at least for now less bullets than enemies, if that changes possible reverse this logic)
						// and check to see if a collision has happened
						for(Iterator<Entity> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
							Entity bullet = bulletIt.next();
							// TODO remove 30's to be dependent on both the enemy and add a number for the bullet width/height
							if (enemy.collisionDetection(bullet)) {
								// TODO replace enemyhit with enemy.getHitSfx() once new entity enemy is being used
								sfxMap.get("enemyhit").playAsSoundEffect(1.0f, 1.0f, false);
								enemy.setHealth(enemy.getHealth() - 1);
								bullet.setHealth(bullet.getHealth()-1);
								
								if(bullet.getHealth() < 1) {
									bulletIt.remove();
								}
	
								gameState.addToScore(1);
							}
						}
						
						// Player collision with enemy
						if(enemy.collisionDetection(player)){
							player.hurtPlayer(1);
							
							// TODO replace with player.getHitSfx() once using entity player
							sfxMap.get(player.getHitSfx()).playAsSoundEffect(1.0f, 1.0f, false);
							// TODO figure out what should happen for enemy collision, probably shoudln't kill it, but should start invincibility timer for player
	
							enemy.setHealth(enemy.getHealth() -1);
							if(player.getHealth() == 0){
								changeLevel("Gameover");
							}
						}
						
	
						if(enemy.getHealth() < 1) {
							enemyIt.remove();
						} else {
							enemy.move(player);
							renderEntity(enemy);
						}
					}
					
					// R, G, B, A Set the color to blue one time only
					// TODO move this code to object specific things, setting the proper colors and all that fun stuff, probably clear for the textures
					//GL11.glColor3f(0.0f, 1.0f, 0.0f);
					
					//temp extrapolate to player class
					// TODO remove once things get real and not just rotate the object based on delta
					player.setRotation(rotation);
					// Should probably be last to make sure that it appears on top of everything in game, but have things for the overlay after this to be on top
					renderEntity(player);
					
					
					for(Iterator<MenuItem> menuIt = menuItemList.iterator(); menuIt.hasNext();){
						MenuItem menuItem = menuIt.next();
						menuItem.render();
					}
					// TODO replace this crappy text code with bitmapped fonts
					GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
								GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
								// Yeah... that to string is pretty awesome isn't it? 
								font.drawString(player.getX() - (resolutionHeight / 2) - 90, player.getY() - (resolutionHeight / 2) + 10,"Health: " + ((Integer)Math.round(player.getHealth())).toString(),Color.yellow);
								font.drawString(player.getX() + (resolutionHeight / 2) - 10, player.getY() - (resolutionHeight / 2) + 10,"Score: " + ((Integer)gameState.getScore()).toString(),Color.yellow);
							GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				} else {
					for(Room room : this.level.getRoomList()) {
						for(Entity background : room.getBackground()) {
							renderEntity(background);
						}
					}
					
					for(Iterator<Entity> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
						Entity bullet = bulletIt.next();
							renderEntity(bullet);
					}
					
					for(Iterator<Entity> enemyIt = enemyList.iterator(); enemyIt.hasNext();){
						Entity enemy = enemyIt.next();
						renderEntity(enemy);
					}
					
					// Should probably be last to make sure that it appears on top of everything in game, but have things for the overlay after this to be on top
					renderEntity(player);
					
					
					for(Iterator<MenuItem> menuIt = menuItemList.iterator(); menuIt.hasNext();){
						MenuItem menuItem = menuIt.next();
						menuItem.render();
					}
					
					
					// TODO replace this crappy text code with bitmapped fonts
					GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
								GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
								Color.transparent.bind();

								font.drawString(player.getX() - (resolutionHeight / 2) - 90, player.getY() - (resolutionHeight / 2) + 10,"Health: " + ((Integer)Math.round(player.getHealth())).toString(),Color.yellow);
								font.drawString(player.getX() + (resolutionHeight / 2) - 10, player.getY() - (resolutionHeight / 2) + 10,"Score: " + ((Integer)gameState.getScore()).toString(),Color.yellow);
							GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					
					// Render pause screen background
					for(Iterator<BasicBackground> backgroundIt = pauseOverlay.getBackgroundItems().iterator(); backgroundIt.hasNext();){
						BasicBackground background = backgroundIt.next();
						background.render();
					}
					
					// Render menu selection items
					for(Iterator<MenuItem> menuIt = pauseOverlay.getMenuItems().iterator(); menuIt.hasNext();){
						MenuItem menuItem = menuIt.next();
						menuItem.render();
					}
				}
			}
			// TODO make this work for all menu types, not just a general one
			else if(level.getType().equals("Menu")){
				
				if(level.getName().equals("BasicMenu")){
				// TODO replace this crappy text code with bitmapped fonts
				GL11.glPushMatrix();
						GL11.glEnable(GL11.GL_BLEND);
							GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							font.drawString(300, 100, "Test Shooter Game", Color.green);
							//font.drawString(300, 200,"To Start Press Enter",Color.yellow);
						GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
				}
				else if(level.getName().equals("GameOverScreen")){
					// TODO replace this crappy text code with bitmapped fonts
					GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
								GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
								font.drawString(300, 100, "Game Over", Color.green);
								font.drawString(300, 200, "Your final score was: " + gameState.getScore(), Color.cyan);
								font.drawString(300, 300,"To Start Press Enter",Color.yellow);
							GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
				
				for(Iterator<MenuItem> menuIt = menuItemList.iterator(); menuIt.hasNext();){
					MenuItem menuItem = menuIt.next();
					menuItem.render();
				}
			}
			
		GL11.glPopMatrix();
	}
	
	public void changeLevel(String levelName) throws IOException{

		// Reset the enemy list
		this.enemyList = new ArrayList<Entity>();
		// Reset the bullet list
		this.bulletList = new ArrayList<Entity>();
		// Reset the Menu Item List
		this.menuItemList = new ArrayList<MenuItem>();
		
		//TODO make this work for any kind of level
		if(levelName.equals("Gameplay")){
			// Reset the player object
			this.player = new Player(400, 300, 0, 0);
			
			// Switch the level
			this.level = LevelGeneration.generateLevel("level", 0);
			
			//TODO most of this stuff will probably need to be changed to update to the current level stuff, or more specific for each level, gamestate souldn't be cleared from level to level
			// Reset the gamestate.
			this.gameState = new ShooterGameState();

			// Set the camera to follow the player
			gameState.setCameraFollow(true);
			playMusic(level.getBackgroundMusic(), 0.2f);
			
		} else if(levelName.equals("Menu")){
			// Switch the level to the main screen
			try {
				this.level = new BasicMenu();
				this.menuItemList = this.level.getMenuItems();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameState.setCameraFollow(false);
			playMusic(level.getBackgroundMusic());

		} else if(levelName.equals("Gameover")){
			// Swith the level to the game over screen
			try {
				this.level = new GameOverScreen();
				this.menuItemList = this.level.getMenuItems();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameState.setCameraFollow(false);
			playMusic(level.getBackgroundMusic());
		}
	}

	public void playMusic(String musicName){
		playMusic(musicName, 0.5f);
	}
	public void playMusic(String musicName, float volume) {
		try{
			currentMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/music/" + musicName + ".wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentMusic.playAsMusic(1.0f, 1.0f, true);
		//TODO when should polling occur elsewhere?
		SoundStore.get().poll(0);
		SoundStore.get().setMusicVolume(volume);
	}
	// It's a main method, you know?
	public static void main(String[] argv) throws IOException {
		ShooterEngine shooterEngine = new ShooterEngine();
		shooterEngine.start();
	}
	
	public void renderEntity(Entity entity) {
		if(
				( entity.getX() - entity.getWidth() / 2 < ( player.getX() + resolutionWidth / 2 ) ) && 
				( entity.getX() + entity.getWidth() / 2 > ( player.getX() - resolutionWidth / 2 ) ) && 
				( entity.getY() + entity.getHeight() / 2 > ( player.getY() - resolutionHeight / 2 ) ) && 
				( entity.getY() - entity.getHeight() / 2 < ( player.getY() + resolutionHeight / 2 ) ) ) {
			entity.render();
		} 
	}
}

