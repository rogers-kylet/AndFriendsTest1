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

import entity.Background;
import entity.BasicBackground;
import entity.BasicPickup;
import entity.BasicWall;
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

/**
 * The starting point and main loop of the game.
 * @author Kyle Rogers
 *
 */
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
	
	// The options for the game
	Options options;
	
	//Player Object
	Player player;

	// List that stores all active bullets shot by the player
	List<Entity> playerBulletList;
	// List that stores all active bullets shot by enemies
	List<Entity> enemyBulletList;
	// List that stores all active enemy objects
	List<Entity> enemyList;
	// List that storse all active menu items
	List<MenuItem> menuItemList;
	// List that stores all the active pickups
	List<Entity> pickupList;
	
	// The current level
	Level level;
	
	TrueTypeFont font;
	
	Audio currentMusic;
	
	Map<String, Audio> sfxMap;
	
	//TODO set this in a config, currently used for font rendering
	boolean antiAlias = false;
	
	boolean pause = false;
	
	boolean changeLevel;
	
	int pauseTimer = 0;
	int pauseTimerStartValue = 10;
	int weaponSwitchTimer = 0;
	int weaponSwitchTimerStartValue = 15;
	
	PauseOverlay pauseOverlay;
	
	Background theBackground;
	
	public void start() throws IOException{
		
		//TODO Load options from file, setting default if none saved
		this.options = new Options();
		
		try{
			// TODO figure out a better way to do this, save settings to file, change in game etc etc etc
			resolutionWidth = 1280;
			resolutionHeight = 720;
			//Display.setDisplayMode(new DisplayMode(resolutionWidth, resolutionHeight));
			//TODO need to change this creation code to set more specific things, deff add some multisampling
			setDisplayMode(resolutionWidth, resolutionHeight, Display.isFullscreen());
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
		playerBulletList = new ArrayList<Entity>();
		// Temp for testing purposes
		enemyList = new ArrayList<Entity>();
		
		enemyTimer = 60;
		
		//TODO temp for checking background support
		theBackground = new Background(400,300,0, 0, resolutionWidth, resolutionHeight);
		theBackground.setTexture("horror level backround_CC");
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
				//rotation += 0.15f * delta;
			
				//BEGIN COMPLETED REFACTOR
				//------------------------
				
				//movementDelta is direction, in degrees, the player is currently moving.
				//TODO: Add movement amortization
				int movementDelta = -1;
				
				//Handle movement angle calculations
				//Angle of movement is read right to left as description above
				
				
				//Checks if player is moving up, up-left, or up-right respectively.
				if(Keyboard.isKeyDown(options.up)) {
					if(!Keyboard.isKeyDown(options.down)){
						movementDelta = (Keyboard.isKeyDown(options.right)?315:(Keyboard.isKeyDown(options.left)?215:270));
					}
				}
				
				//Checks if player is moving left or down-left.
				else if(Keyboard.isKeyDown(options.left)) {
					if(!Keyboard.isKeyDown(options.right)) {
						movementDelta = (Keyboard.isKeyDown(options.down)?135:180);
					}
				}
				//Checks if player is down or down-right.
				else if(Keyboard.isKeyDown(options.down)) {
					if(!Keyboard.isKeyDown(options.up)) {
						movementDelta = (Keyboard.isKeyDown(options.right)?45:90);
					}
				}
				//Checks if player is moving right
				// TODO do we need an additional check for other directions? 
				else if(Keyboard.isKeyDown(options.right)){
					if(!Keyboard.isKeyDown(options.left)) {
						movementDelta = 0;
					}
				}
				
				
				if(Keyboard.isKeyDown(options.jump)) {
					player.jump();	
				}
				
				//if(Keyboard.isKeyDown(Keyboard.KEY_A)) movementDelta = 180;
				//else if(Keyboard.isKeyDown(Keyboard.KEY_D)) movementDelta = 0;
				//Attempt movement if any keys were pressed
				if(movementDelta<=-1) {
					player.setAnimation("idle");
					
				} else {
					player.setAngle(movementDelta);
				}
				processPlayerMove(movementDelta);

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
						
						List<Entity> bulletList = player.attack(shotFireDelta);
						
						for(Entity bullet: bulletList){ playerBulletList.add(bullet); }

						//TODO get this from the players weapon
						sfxMap.get("shot").playAsSoundEffect(1.0f, 1.0f, false);
						
						// Stop the player from shooting again and reset the bullet timer
						player.setCanShoot(false);
						player.resetShooterTimer();
					}
				}
				
				//----------------------
				//END COMPLETED REFACTOR
				
				if(Keyboard.isKeyDown(options.meleeAttack)) {
					if(player.isCanMeleeAttack()) {
						List<Entity> bulletList = player.meleeAttack(player.getAngle());
						for(Entity bullet: bulletList) { playerBulletList.add(bullet); }
						
						sfxMap.get( player.getMeleeWeapon().getSfxName()).playAsSoundEffect(1.0f, 1.0f, false);
					}
				}
				
				if(Keyboard.isKeyDown(options.shoot)) {
					if(player.isCanShoot()){
						
						List<Entity> bulletList = player.attack(player.getAngle());
						
						for(Entity bullet: bulletList){ playerBulletList.add(bullet); }

						//TODO get this from the players weapon
						sfxMap.get("shot").playAsSoundEffect(1.0f, 1.0f, false);
						
						// Stop the player from shooting again and reset the bullet timer
						player.setCanShoot(false);
						player.resetShooterTimer();
					}
				}
				
				// I would hope there'sa better way to do this...but i guess it works... 
				if(weaponSwitchTimer == 0) {
					boolean wepSwitch = false;
					if(Keyboard.isKeyDown(Keyboard.KEY_Q)) { player.scrollWeaponDown(); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_E)) { player.scrollWeaponUp(); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_1)) { player.changeWeapon(0); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_2)) { player.changeWeapon(1); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_3)) { player.changeWeapon(2); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_4)) { player.changeWeapon(3); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_5)) { player.changeWeapon(4); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_6)) { player.changeWeapon(5); wepSwitch = true;}
					if(Keyboard.isKeyDown(Keyboard.KEY_7)) { player.changeWeapon(6); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_8)) { player.changeWeapon(7); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_9)) { player.changeWeapon(8); wepSwitch = true; }
					if(Keyboard.isKeyDown(Keyboard.KEY_0)) { player.changeWeapon(9); wepSwitch = true; }
					if(wepSwitch) { weaponSwitchTimer = weaponSwitchTimerStartValue; }
				}
				
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
						} else if(Keyboard.getEventKey() == Keyboard.KEY_F){
							setDisplayMode(resolutionWidth,resolutionHeight, !Display.isFullscreen());
						}
					}
				}
				
				if(Mouse.isButtonDown(0)) {
					int mouseX = Math.abs(Mouse.getX()), mouseY = Math.abs(resolutionHeight - Mouse.getY());
					
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
								AL.destroy();
								System.exit(0);
							} else if(menuItem.getButtonAction() == "FullScreen"){
							setDisplayMode(resolutionWidth,resolutionHeight, !Display.isFullscreen());
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
				} else if(Keyboard.getEventKey() == Keyboard.KEY_F){
					setDisplayMode(resolutionWidth,resolutionHeight, !Display.isFullscreen());
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
						} else if(menuItem.getButtonAction() == "MainMenu") {
							changeLevel("Menu");
						} else if (menuItem.getButtonAction() == "ExitButton") {
							Display.destroy();
							AL.destroy();
							System.exit(0);
						}
					}
				}
			}
			
		}
		
		// Hack to prevent multiple pause clicks
		// TODO shouldn't be needed, should probably be able to make this work in the key event holding somehow
		if(pauseTimer > 0){pauseTimer--;}
		if(weaponSwitchTimer > 0) { weaponSwitchTimer--;}
		updateFPS(); // update FPS Counter
	}

	private void processPlayerMove(float newAngle2) {
		if(newAngle2 != -1) {
			if(newAngle2 != 90 && newAngle2 != 270) {
				player.setAnimation("walk");
			}
			player.setAngle(newAngle2);
		}
		float oldX = player.getX(), oldY = player.getY(), newAngle = -1;
		boolean xChange = false, yChange = false;
		
		if(newAngle2 == -1) {
			player.move(null);
		} else {
			player.move();
		}
		
		boolean isOnGround = false;
		boolean isOnWall = false;
		
		for(Room room: this.level.getRoomList()) {
			if(!room.isEntered()) {
				//TODO maybe room should extend entity to use the onScreen method....
				// TODO possibly add fudge factor so that rooms that are directly outside the room 
				if(
						( room.getX() - room.getWidth() / 2 < ( player.getX() + resolutionWidth / 2 ) ) && 
						( room.getX() + room.getWidth() / 2 > ( player.getX() - resolutionWidth / 2 ) ) && 
						( room.getY() + room.getHeight() / 2 > ( player.getY() - resolutionHeight / 2 ) ) && 
						( room.getY() - room.getHeight() / 2 < ( player.getY() + resolutionHeight / 2 ) ) ) {
					
					for(Entity enemy : room.getEnemyList()) { this.enemyList.add(enemy); }
					room.setEntered(true);
					this.pickupList.addAll(room.getPickupList());
				}
			}
			
			// Loop through the walls in the room, and determine if 
			for(Entity wall: room.getWallList()) {
				// TODO add walls that you can conditionally move through (once you fully pass through it you can't move back
				// TODO you know, if we need it anyway
				if(wall.collisionDetection(player)) {
					
					// TODO need to figure out why the second part of the code wasn't working and fix it so it does
					//TODO this whole thing got screwed up at some point, so fix it
					if(player.getY() + player.getHeight()/2 >= wall.getY() - wall.getHeight()/2 
							&& oldY + player.getHeight()/2 <= wall.getY() - wall.getHeight()/2){
						//player.onGround();
						isOnGround = true;
						player.setY(wall.getY() - wall.getHeight()/2 - player.getHeight()/2);
					} 
					
					// TODO add support for walls that you can jump through from the buttom
					if(player.getY() - player.getHeight()/2 <= wall.getY() + wall.getHeight()/2 
							&& oldY - player.getHeight()/2 >= wall.getY() + wall.getHeight()/2){
						player.hitCeiling();
						player.setY(wall.getY() + wall.getHeight()/2 + player.getHeight()/2);

					}
					
					// TODO do we need an angle dectection here? play around with it and see if it matters
					// TODO add support for walls that you cna move through the left/right side of but not the other, if we need it anyway
					if(player.getX() + player.getWidth()/2 >= wall.getX() - wall.getWidth()/2 
							&& oldX + player.getWidth()/2 <= wall.getX() - wall.getWidth()/2){
						player.setX(wall.getX() - wall.getWidth()/2 - player.getWidth()/2);
						isOnWall = true;
					}
					
					if(player.getX() - player.getWidth()/2 <= wall.getX() + wall.getWidth()/2 
							&& oldX - player.getWidth()/2 >= wall.getX() + wall.getWidth()/2){
						player.setX(wall.getX() + wall.getWidth()/2 + player.getWidth()/2);
						isOnWall = true;
					}
				}
			}

		}
		if(isOnGround) {
			player.onGround();
		} else {
			player.notOnGround();
		}
		
		if(isOnWall) {
			player.onWall();
		} else {
			player.notOnWall();
		}
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
 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 10.0f);                
        GL11.glClearDepth(1);                                       
 
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        //GL11.glBlendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA); 
        
        GL11.glViewport(0,0,resolutionWidth,resolutionHeight);
        
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
		
		// Have the camera follow the player
		if(gameState.isCameraFollow()){ 
			GL11.glTranslatef(-player.getX() + (resolutionWidth / 2), -player.getY() + (resolutionHeight / 2), 0); 
		}
		//if(gameState.isCameraFollow()){ GL11.glTranslatef(-player.getX()+(resolutionWidth / 2), -player.getY()+(resolutionHeight / 2 + resolutionHeight/4), 0); } // Camera follow offset
		
		GL11.glPushMatrix();
			// Clear the screen adn the deph buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			// Initial color so pixels can be colored in individual classes, probably wont be needed once textures are being used
			GL11.glColor3f(0.0f, 0.0f, 0.0f);
	
			//TODO change to a switch
			if(level.getType().equals("Gameplay")){
				if(!pause) {

					processBackground();
					processRoom();
					processPickUps();
					processEnemyBullet();
					processPlayerBullet();
					processEnemy();
										
					//temp extrapolate to player class
					// TODO remove once things get real and not just rotate the object based on delta
					player.setRotation(rotation);
					// Should probably be last to make sure that it appears on top of everything in game, but have things for the overlay after this to be on top
					renderEntity(player);
					
					// Process and render menu items
					processMenuItems();
					
					// TODO replace this crappy text code with bitmapped fonts
					GL11.glPushMatrix();
					
							GL11.glEnable(GL11.GL_BLEND);
								GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
								// Yeah... that to string is pretty awesome isn't it? 
								font.drawString(player.getX() - (resolutionHeight / 2) - 90, player.getY() - (resolutionHeight / 2) + 10,"Health: " + ((Integer)Math.round(player.getHealth())).toString(),Color.yellow);
								font.drawString(player.getX() + (resolutionHeight / 2) - 10, player.getY() - (resolutionHeight / 2) + 10,"Money: " + ((Integer)gameState.getMoney()).toString(),Color.yellow);
								font.drawString(player.getX() - (resolutionHeight / 2) + 20, player.getY() - (resolutionHeight / 2) + 10,"Weapon: " + ((Integer)player.getWeaponIndex()).toString(),Color.yellow);

							GL11.glDisable(GL11.GL_BLEND);

					GL11.glPopMatrix();
					
					if(player.getHealth() == 0){ changeLevel("Gameover"); }
					// Need to update change level to be more specific
					if(this.changeLevel) { changeLevel("Gameplay"); }
				} 
				
				// Pause Screen Render
				else {
					processPauseScreen();
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
								font.drawString(300, 200, "Your final score was: " + gameState.getMoney(), Color.cyan);
								//font.drawString(300, 300,"To Start Press Enter",Color.yellow);
							GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
				
				processMenuItems();
			}
			
		GL11.glPopMatrix();
	}

	public void processPickUps() {
		for(Iterator<Entity> pickupIt = this.pickupList.iterator(); pickupIt.hasNext();) {
			BasicPickup pickup = (BasicPickup) pickupIt.next();
			renderEntity(pickup);
			
			if(player.collisionDetection(pickup)) {
				if(!pickup.getPickupType().equals("end")) {
					if(pickup.getPickupType().equals("heart")) {
						if(player.getHealth() < player.getMaxHealth()) {
							this.player.setHealth(this.player.getHealth() + pickup.getPickupValue());
							pickupIt.remove();
						} 
					} else if(pickup.getPickupType().equals("money")) {
						this.gameState.setMoney((int) (this.gameState.getMoney() + pickup.getPickupValue()));
						pickupIt.remove();
					}
				} else {
					this.changeLevel = true;
				}
			}
		}
	}
	
	public void processPauseScreen() {
		processBackground();
		// Process and render rooms
		processRoom();
		
		processPickUps();
		
		for(Iterator<Entity> bulletIt = playerBulletList.iterator(); bulletIt.hasNext();){
			Entity bullet = bulletIt.next();
			renderEntity(bullet);
		}
		
		for(Iterator<Entity> enemyIt = enemyList.iterator(); enemyIt.hasNext();){
			Entity enemy = enemyIt.next();
			renderEntity(enemy);
		}
		
		// Should probably be last to make sure that it appears on top of everything in game, but have things for the overlay after this to be on top
		renderEntity(player);
		
		
		processMenuItems();
		
		
		// TODO replace this crappy text code with bitmapped fonts
		GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					Color.transparent.bind();

					font.drawString(player.getX() - (resolutionHeight / 2) - 90, player.getY() - (resolutionHeight / 2) + 10,"Health: " + ((Integer)Math.round(player.getHealth())).toString(),Color.yellow);
					font.drawString(player.getX() + (resolutionHeight / 2) - 10, player.getY() - (resolutionHeight / 2) + 10,"Score: " + ((Integer)gameState.getMoney()).toString(),Color.yellow);
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

	public void processMenuItems() {
		for(Iterator<MenuItem> menuIt = menuItemList.iterator(); menuIt.hasNext();){
			MenuItem menuItem = menuIt.next();
			menuItem.render();
		}
	}

	public void processBackground() {
		this.theBackground.setX(player.getX());
		this.theBackground.setY(player.getY());
		//this.theBackground.setY(player.getY() - resolutionHeight/4); // Camera at the bottom of the scren
		this.theBackground.render(level.getxMin(), level.getxMax(), level.getyMin(), level.getyMax());
	}
	
	public void processRoom() {
		for(Room room : this.level.getRoomList()) {
			//for(Entity background : room.getBackground()) { renderEntity(background); }
			for(Entity wall : room.getWallList()) {
				
				//Only check for collision if the wall was rendered
				if(renderEntity(wall)) {
				
					//TODO need to extract current room determined from player movement code and then just check the walls in that room against the bullet instead of every room against the bullet
					//but then the wall won't be rendered....
					for(Iterator<Entity> bulletIt = playerBulletList.iterator(); bulletIt.hasNext();){
						Entity bullet = bulletIt.next();
						if(wall.collisionDetection(bullet) && bullet.getHealth() != -1){ bulletIt.remove(); }
					}
					for(Iterator<Entity> bulletIt = enemyBulletList.iterator(); bulletIt.hasNext();) {
						Entity bullet = bulletIt.next();
						if(wall.collisionDetection(bullet) && bullet.getHealth() != -1) { bulletIt.remove(); }
					}
				}
			}
		}
	}

	//TODO this
	public void processEnemyBullet(){
		for(Iterator<Entity> bulletIt = enemyBulletList.iterator(); bulletIt.hasNext();){
			Entity bullet = bulletIt.next();
				bullet.move();
				
				// Player collision with enemy
				if(bullet.collisionDetection(player)){
					if(!player.isInvincible()) {
						sfxMap.get(player.getHitSfx()).playAsSoundEffect(1.0f, 1.0f, false);
					}
					
					player.hurtPlayer(1);

					bullet.hurt(1);
				}
				
				renderEntity(bullet);
				
				// Check for 0 so negative health can be invincible
				if(bullet.getHealth() == 0) { bulletIt.remove(); }
		}	}
	
	public void processPlayerBullet() {
		for(Iterator<Entity> bulletIt = playerBulletList.iterator(); bulletIt.hasNext();){
			Entity bullet = bulletIt.next();
				bullet.move(player);
				renderEntity(bullet);
				
				// Check for 0 so negative health can be invincible
				if(bullet.getHealth() == 0) { bulletIt.remove(); }
		}
	}

	public boolean processEnemy() throws IOException {
		for(Iterator<Entity> enemyIt = enemyList.iterator(); enemyIt.hasNext();){
			Entity enemy = enemyIt.next();
			
			// Dont process enemy if it is off screen
			if(onScreen(enemy)){} 
			else { continue; }
			
			// Loop through each bullet (at least for now less bullets than enemies, if that changes possible reverse this logic)
			// and check to see if a collision has happened
			for(Iterator<Entity> bulletIt = playerBulletList.iterator(); bulletIt.hasNext();){
				Entity bullet = bulletIt.next();
				// TODO remove 30's to be dependent on both the enemy and add a number for the bullet width/height
				if (enemy.collisionDetection(bullet)) {
					// TODO replace enemyhit with enemy.getHitSfx() once new entity enemy is being used
					sfxMap.get("enemyhit").playAsSoundEffect(1.0f, 1.0f, false);
					enemy.hurt(1);
					bullet.hurt(1);
					
					if(bullet.getHealth() == 0) { bulletIt.remove(); }
				}
			}
			
			// Player collision with enemy
			if(enemy.collisionDetection(player)){
				
				if(!player.isInvincible()) {
					sfxMap.get(player.getHitSfx()).playAsSoundEffect(1.0f, 1.0f, false);
				}
				
				player.hurtPlayer(1);

				// TODO figure out what should happen for enemy collision, probably shoudln't kill it, but should start invincibility timer for player

				//enemy.setHealth(enemy.getHealth() -1);
			}
			
			if(enemy.getHealth() < 1) {
				float enemyX = enemy.getX();
				float enemyY = enemy.getY();
				Double rand = Math.random();
				BasicPickup pickup = new BasicPickup(enemyX, enemyY, 0, 0, 25, 25);
				if(rand >= .5) {
					pickup.setPickupType("heart");
					pickup.setPickupValue(1);
				} else {
					pickup.setPickupType("money");
					pickup.setPickupValue(10);
				}
				this.pickupList.add(pickup);
				
				enemyIt.remove();

			} else {
				/*
				float oldX = enemy.getX();
				float oldY = enemy.getY();
				
				enemy.move(player);

				for(Room room: this.level.getRoomList()) {
					for(Entity wall: room.getWallList()) {
						if(wall.collisionDetection(enemy)) {
							//TODO change this from not moving to pointing in the direction of the closest edge of the wall
							//enemy.setX(oldX);
							//enemy.setY(oldY);
							if(enemy.getX() != oldX || enemy.getY() != oldY){
								if(enemy.getAngle() == 0 || enemy.getAngle() == 90 || enemy.getAngle() == 180 || enemy.getAngle() == 270) {
									enemy.setX(oldX);
									enemy.setY(oldY);
								} else if(enemy.getAngle() > 0 && enemy.getAngle() < 90){
									//TODO refactor when non-squares come into play
									// TODO change from reseting to oldX oldY to reseting both and switching the angle to preserve momentum
									if(enemy.getY() + enemy.getHeight()/2 > wall.getY() - wall.getHeight()/2 
											&& oldY + enemy.getHeight()/2 < wall.getY() - wall.getHeight()/2){
										enemy.setY(oldY);
									}
									if(enemy.getX() + enemy.getWidth()/2 > wall.getX() - wall.getWidth()/2 
											&& oldX + enemy.getWidth()/2 < wall.getX() - wall.getWidth()/2){
										enemy.setX(oldX);
									}
								} else if(enemy.getAngle() > 90  && enemy.getAngle() < 180) {
									if(enemy.getY() + enemy.getHeight()/2 > wall.getY() - wall.getHeight()/2 
											&& oldY + enemy.getHeight()/2 < wall.getY() - wall.getHeight()/2){
										enemy.setY(oldY);
									}
									if(enemy.getX() - enemy.getWidth()/2 < wall.getX() + wall.getWidth()/2 
											&& oldX - enemy.getWidth()/2 > wall.getX() + wall.getWidth()/2){
										enemy.setX(oldX);
									}
								} else if(enemy.getAngle() > 180 && enemy.getAngle() < 270) {
									if(enemy.getY() - enemy.getHeight()/2 < wall.getY() + wall.getHeight()/2 
											&& oldY - enemy.getHeight()/2 > wall.getY() + wall.getHeight()/2){
										enemy.setY(oldY);
									}
									if(enemy.getX() - enemy.getWidth()/2 < wall.getX() + wall.getWidth()/2 
											&& oldX - enemy.getWidth()/2 > wall.getX() + wall.getWidth()/2){
										enemy.setX(oldX);
									}
								} else if (enemy.getAngle() > 270 && enemy.getAngle() < 360) {
									if(enemy.getY() - enemy.getHeight()/2 < wall.getY() + wall.getHeight()/2 
											&& oldY - enemy.getHeight()/2 > wall.getY() + wall.getHeight()/2){
										enemy.setY(oldY);
									}
									if(enemy.getX() + enemy.getWidth()/2 > wall.getX() - wall.getWidth()/2 
											&& oldX + enemy.getWidth()/2 < wall.getX() - wall.getWidth()/2){
										enemy.setX(oldX);
									}
								}
							}
						}
					}
				}
							*/	
				List<Entity> enemyBullets = enemy.attack(player);
				if(enemyBullets != null) {
					this.enemyBulletList.addAll(enemyBullets);
				}
				enemy.processMovementTick(player, this.level.getRoomList());

				renderEntity(enemy);

			}
		}
		return true;
	}
	
	public void changeLevel(String levelName) throws IOException{

		// Reset the enemy list
		this.enemyList = new ArrayList<Entity>();
		// Reset the bullet list
		this.playerBulletList = new ArrayList<Entity>();
		// Reset the enemy bullet list.
		this.enemyBulletList = new ArrayList<Entity>();
		// Reset the Menu Item List
		this.menuItemList = new ArrayList<MenuItem>();
		// Reset the pickup list
		this.pickupList = new ArrayList<Entity>();
		
		
		this.changeLevel = false;
		//TODO make this work for any kind of level
		if(levelName.equals("Gameplay")){
			
			//TODO change this to be specific to the levels start room
			player.setX(400); player.setY(200);
			
			// Switch the level
			this.level = LevelGeneration.generateLevel("level", 0);
			
			//TODO most of this stuff will probably need to be changed to update to the current level stuff, or more specific for each level, gamestate souldn't be cleared from level to level

			// Set the camera to follow the player
			gameState.setCameraFollow(true);
			playMusic(level.getBackgroundMusic(), 0.2f);
			
		} else if(levelName.equals("Menu")){
			// Switch the level to the main screen
			try {
				this.level = new BasicMenu();
				this.menuItemList = this.level.getMenuItems();
			} catch (IOException e) { e.printStackTrace(); }
			gameState.setCameraFollow(false);
			playMusic(level.getBackgroundMusic());

		} else if(levelName.equals("Gameover")){
			// Switch the level to the game over screen
			try {
				this.level = new GameOverScreen();
				this.menuItemList = this.level.getMenuItems();
				this.player = new Player(400, 300, 0, 0);
			} catch (IOException e) { e.printStackTrace(); }
			gameState.setCameraFollow(false);
			playMusic(level.getBackgroundMusic());
		}
	}

	public void playMusic(String musicName){ playMusic(musicName, 0.5f); }
	
	public void playMusic(String musicName, float volume) {
		try{ currentMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("assets/music/" + musicName + ".wav")); } 
		catch (IOException e) { e.printStackTrace(); }
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
	
	public boolean renderEntity(Entity entity) {
		if(onScreen(entity)) {
			entity.render();
			return true;
		} else{ return false; }
	}
	
	public boolean onScreen(Entity entity) {
		if(
				( entity.getX() - entity.getWidth() / 2 < ( player.getX() + resolutionWidth / 2 ) ) && 
				( entity.getX() + entity.getWidth() / 2 > ( player.getX() - resolutionWidth / 2 ) ) && 
				( entity.getY() + entity.getHeight() / 2 > ( player.getY() - resolutionHeight / 2 ) ) && 
				( entity.getY() - entity.getHeight() / 2 < ( player.getY() + resolutionHeight / 2 ) ) ) {
			return true;
		} else{ return false; }
	}
	
}

