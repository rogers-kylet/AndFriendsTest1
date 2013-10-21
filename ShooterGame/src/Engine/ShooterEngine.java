package Engine;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import Background.Background;
import Background.BackgroundObject;
import Bullet.BasicBullet;
import Bullet.Bullet;
import Enemy.BasicEnemy;
import Enemy.Enemy;
import GameState.GameState;
//TODO gamestate shootergame state cant be resolved, fix
import GameState.ShooterGameState;
import Level.BasicLevel;
import Level.BasicMenu;
import Level.GameOverScreen;
import Level.Level;
import MenuItem.MenuItem;
import Player.Player;


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

	//Temporary background object for testing
	BackgroundObject background;
	// List that stores all active bullet objects
	List<Bullet> bulletList;
	// List that stores all active enemy objects
	List<Enemy> enemyList;
	// List that storse all active menu items
	List<MenuItem> menuItemList;
	
	// The current level
	Level level;
	
	private TrueTypeFont font;
	
	//TODO set this in a config, currently used for font rendering
	boolean antiAlias = false;
	
	public void start(){
		try{
			// TODO figure out a better way to do this, save settings to file, change in game etc etc etc
			resolutionWidth = 800;
			resolutionHeight = 600;
			Display.setDisplayMode(new DisplayMode(resolutionWidth, resolutionHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		initGL(); // init OpenGL
		getDelta(); // call once before loop to initilise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		
		//TODO weird config problem not getting recognized
		gameState = new ShooterGameState();
		
		//Initialize player
		player = new Player();
		
		//Temp for testing purposes
		background = new Background();
		background.setX(400);
		background.setY(300);
		
		//Temp for testing purposes
		bulletList = new ArrayList<Bullet>();
		// Temp for testing purposes
		enemyList = new ArrayList<Enemy>();
		
		enemyTimer = 60;
		
		level = new BasicMenu();
		
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
	}
	
	public void update(int delta){
		
		//TODO change to a switch
		if(level.getType().equals("Gameplay")){
			
			// TODO temp logic for making enemies randomly appear above
			if(enemyTimer == 0){
				enemyList.add(new BasicEnemy((float) (Math.random()*800), -10));
				enemyTimer = 60;
			}
			else{
				enemyTimer -= 1;
			}
			
			//rotate quad
			// TODO remove this code, rotation should be done, being left for now becuase it makes the basic tests more fun to see movement
			rotation += 0.15f * delta;
		
		
			
			// TODO verify/make sure the speed is normalized for all directional movement
			// TODO make this more elegant somehow
			if(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_W)){
				player.movePlayer(225);
			}
			
			else if(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_S)){
				player.movePlayer(135);
			}
			
			else if(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_W)) {
				player.movePlayer(315);
			}
			
			else if(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
				player.movePlayer(45);
			}
			
			//TODO check the order of the the singles to make sure it is optimal
			else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				//player.setPlayerX(player.getPlayerX() - 0.35f * delta);
				player.movePlayer(180);
			}
	
			//TODO make this more elegant somehow
			else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				//player.setPlayerX(player.getPlayerX() + 0.35f * delta);
				player.movePlayer(0);
			}
	
			//TODO make this more elegant somehow
			else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				//player.setPlayerY(player.getPlayerY() + 0.35f * delta);
				player.movePlayer(90);
			}
	
			//TODO make this more elegant somehow
			else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				//player.setPlayerY(player.getPlayerY() - 0.35f * delta);
				player.movePlayer(270);
			}
			
			// Press "Space" to shoot if the player can
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				if(player.isCanShoot()){
					// Add the bullet to the bullet list at the players position
					bulletList.add((new BasicBullet(player.getPlayerX(), player.getPlayerY())));
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
			
			// TODO verify/make sure the speed is normalized for all directional movement
			// TODO make this more elegant somehow
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) && Keyboard.isKeyDown(Keyboard.KEY_UP)){
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(225);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
			
			else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) && Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(135);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
			
			else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(315);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
			
			else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(45);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
			
			//TODO check the order of the the singles to make sure it is optimal
			else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(180);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
	
			//TODO make this more elegant somehow
			else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(0);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
	
			//TODO make this more elegant somehow
			else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if(player.isCanShoot()){
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(90);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
	
			//TODO make this more elegant somehow
			else if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				if(player.isCanShoot()) {
					BasicBullet bullet = new BasicBullet(player.getPlayerX(), player.getPlayerY());
					bullet.setAngle(270);
					bulletList.add(bullet);
					// Stop the player from shooting again and reset the bullet timer
					player.setCanShoot(false);
					player.resetShooterTimer();
				}
			}
			while(Keyboard.next()){
				if(Keyboard.getEventKeyState()){
					// Pres "F" to set the game to full screen mode
					// TODO probably remove from button command and put in an options menu
					if(Keyboard.getEventKey() == Keyboard.KEY_F){
						setDisplayMode(800,600, !Display.isFullscreen());
					}
					// Press V to toggle vsync
					// TODO remove from keyboard command and set in options
					else if (Keyboard.getEventKey() == Keyboard.KEY_V) {
						vsync = !vsync;
						Display.setVSyncEnabled(vsync);
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
		}
		
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
			GL11.glOrtho(0, 800, 600, 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
	}
	
	// Main render method
	// TODO figure out the nice way to render everything
	public void renderGL(){
		// Clear the screen adn the deph buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		// Initial color so pixels can be colored in individual classes, probably wont be needed once textures are being used
		GL11.glColor3f(0.0f, 0.0f, 0.0f);

		//TODO change to a switch
		if(level.getType().equals("Gameplay")){
		
			// Temp code for testing
			// TODO figure out the best order to render to keep things that should be on top on top
			////////////////////////////////
			background.render();
			////////////////////////////////
			
	
			// Loop through the list of active bullets 
			for(Iterator<Bullet> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
				Bullet bullet = bulletIt.next();
				// If the bullet has left the screen, remove it to preserve memory
				// TODO possibly need to change the logic if things change
				if(bullet.offScreen()){
					bulletIt.remove();
				}
				else{
					bullet.move();
					bullet.render();
				}
			}
			
			//GL11.glColor3f(1.0f, 0.0f, 0.0f);
	
			//TODO make collision detection general for any enemy/bullet size
			for(Iterator<Enemy> enemyIt = enemyList.iterator(); enemyIt.hasNext();){
				Enemy enemy = enemyIt.next();
				// Loop through each bullet (at least for now less bullets than enemies, if that changes possible reverse this logic)
				// and check to see if a collision has happened
				for(Iterator<Bullet> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
					Bullet bullet = bulletIt.next();
					// TODO remove 30's to be dependent on both the enemy and add a number for the bullet width/height
					if (enemy.collidWithBullet(bullet)) {
						
						enemyIt.remove();
						//TODO display score
						//TODO weird problem with importing gamestate
						gameState.addToScore(1);
						
						// If the bullet can not penetrate the object, remove it 
						if(!bullet.penetrate()){
							bulletIt.remove();
						}
					}
					
	
				}
				
				// Player collision with enemy
				if(enemy.collidWithPlayer(player)){
					player.hurtPlayer(1);
					// TODO figure out what should happen for enemy collision, probably shoudln't kill it, but should start invincibility timer for player
					enemyIt.remove();
					
					if(player.getHealth() == 0){
						changeLevel("Gameover");
					}
				}
				
				// TODO probably make this lower, possibly extract it to the enemy class and make it more general for garbage collection or situation dependent or something
				if(enemy.getY() > 610){
					enemyIt.remove();
				}
				else{
					enemy.move();
					enemy.render();
				}
			}
			
			// R, G, B, A Set the color to blue one time only
			// TODO move this code to object specific things, setting the proper colors and all that fun stuff, probably clear for the textures
			//GL11.glColor3f(0.0f, 1.0f, 0.0f);
			
			//temp extrapolate to player class
			// TODO remove once things get real and not just rotate the object based on delta
			player.setPlayerRotation(rotation);
			// Should probably be last to make sure that it appears on top of everything in game, but have things for the overlay after this to be on top
			player.render();
			
			// TODO replace this crappy text code with bitmapped fonts
			GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
						// Yeah... that to string is pretty awesome isn't it? 
						font.drawString(100, 10,"Health: " + ((Integer)player.getHealth()).toString(),Color.yellow);
						font.drawString(700, 10,"Score: " + ((Integer)gameState.getScore()).toString(),Color.yellow);
					GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		// TODO make this work for all menu types, not just a general one
		else if(level.getType().equals("Menu")){
			
			if(level.getName().equals("BasicMenu")){
			// TODO replace this crappy text code with bitmapped fonts
			GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
						// Yeah... that to string is pretty awesome isn't it? 
						font.drawString(300, 100, "Test Shooter Game", Color.green);
						font.drawString(300, 200,"To Start Press Enter",Color.yellow);
					GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			}
			else if(level.getName().equals("GameOverScreen")){
				// TODO replace this crappy text code with bitmapped fonts
				GL11.glPushMatrix();
						GL11.glEnable(GL11.GL_BLEND);
							GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							// Yeah... that to string is pretty awesome isn't it? 
							font.drawString(300, 100, "Game Over", Color.green);
							font.drawString(300, 200, "Your final score was: " + gameState.getScore(), Color.cyan);
							font.drawString(300, 300,"To Start Press Enter",Color.yellow);
						GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
	}
	
	public void changeLevel(String levelName){
		//TODO make this work for any kind of level
		if(levelName.equals("Gameplay")){
			// Switch the level
			this.level = new BasicLevel();
			
			//TODO most of this stuff will probably need to be changed to update to the current level stuff, or more specific for each level, gamestate souldn't be cleared from level to level
			// Reset the gamestate.
			this.gameState = new ShooterGameState();
			// Reset the player object
			this.player = new Player();
			// Reset the enemy list
			this.enemyList = new ArrayList<Enemy>();
			// Reset the bullet list
			this.bulletList = new ArrayList<Bullet>();
		} else if(levelName.equals("Menu")){
			// Swith the level to the main screen
			this.level = new BasicMenu();
		} else if(levelName.equals("Gameover")){
			// Swith the level to the game over screen
			this.level = new GameOverScreen();
		}
	}
	// It's a main method, you know?
	public static void main(String[] argv) {
		ShooterEngine shooterEngine = new ShooterEngine();
		shooterEngine.start();
	}
}

