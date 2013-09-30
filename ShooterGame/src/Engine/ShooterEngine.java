package Engine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Background.Background;
import Background.BackgroundObject;
import Bullet.BasicBullet;
import Bullet.Bullet;
import Enemy.BasicEnemy;
import Enemy.Enemy;
import GameState.ShooterGameState;
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
	ShooterGameState gameState;
	
	//Player Object
	Player player;

	//Temporary background object for testing
	BackgroundObject background;
	// List that stores all active bullet objects
	List<Bullet> bulletList;
	// List that stores all active enemy objects
	List<Enemy> enemyList;
	
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
		
		if(!player.isCanShoot()){
			player.countDownShooterTimer();
		}
		
		if(enemyTimer == 0){
			enemyList.add(new BasicEnemy((float) (Math.random()*800), 610));
			enemyTimer = 60;
		}
		else{
			enemyTimer -= 1;
		}
		
		//rotate quad
		// TODO remove this code, rotation should be done, being left for now becuase it makes the basic tests more fun to see movement
		rotation += 0.15f * delta;
		
		
		// TODO verify/make sure the speed is normalized for all directional movement
		//TODO make this more elegant somehow
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			player.setPlayerX(player.getPlayerX() - 0.35f * delta);
		}

		//TODO make this more elegant somehow
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			player.setPlayerX(player.getPlayerX() + 0.35f * delta);
		}

		//TODO make this more elegant somehow
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			player.setPlayerY(player.getPlayerY() - 0.35f * delta);
		}

		//TODO make this more elegant somehow
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			player.setPlayerY(player.getPlayerY() + 0.35f * delta);
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
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	// Main render method
	// TODO figure out the nice way to render everything
	public void renderGL(){
		// Clear the screen adn the deph buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		// Initial color so pixels can be colored in individual classes, probably wont be needed once textures are being used
		GL11.glColor3f(0.0f, 0.0f, 0.0f);

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
				if(
						( bullet.getX() < ( enemy.getX() + 30 ) ) && 
						( bullet.getX() > ( enemy.getX() - 30 ) ) && 
						( bullet.getY() > ( enemy.getY() - 30 ) ) && 
						( bullet.getY() < ( enemy.getY() + 30 ) ) ){
					
					enemyIt.remove();
					//TODO display score
					gameState.addToScore(1);
					
					// If the bullet can not penetrate the object, remove it 
					if(!bullet.penetrate()){
						bulletIt.remove();
					}
				}
			}
			// TODO probably make this lower, possibly extract it to the enemy class and make it more general for garbage collection or situation dependent or something
			if(enemy.getY() < 0){
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
		
	}
	
	// It's a main method, you know?
	public static void main(String[] argv) {
		ShooterEngine shooterEngine = new ShooterEngine();
		shooterEngine.start();
	}
}

