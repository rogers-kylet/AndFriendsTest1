import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class ShooterEngine {

	/* position of quad */
	float x = 400, y = 300;
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
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(player.isCanShoot()){
				bulletList.add((new BasicBullet(player.getPlayerX(), player.getPlayerY())));
				player.setCanShoot(false);
				player.resetShooterTimer();
			}
		}
		
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				if(Keyboard.getEventKey() == Keyboard.KEY_F){
					setDisplayMode(800,600, !Display.isFullscreen());
				}
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
	//TODO comment and turn from copied code to project specific
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
		// TODO should every render method run this, or should it be used here only
		// TODO can sepereate objects actually be rendered in their own classes? I hope so
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor3f(0.0f, 0.0f, 0.0f);

		// Temp code for testing
		// TODO figure out the best order to render to keep things that should be on top on top
		// TODO color doesn't seem to work inside the actual classes, probably need to figure out somewhere else to do it (although textures should take care of that)
		////////////////////////////////
		//GL11.glColor3f(0.5f, 0.5f, 0.5f);
		background.render();
		////////////////////////////////
		
		//GL11.glColor3f(0.0f, 0.0f, 1.0f);

		for(Iterator<Bullet> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
			Bullet bullet = bulletIt.next();
			if(bullet.offScreen()){
				bulletIt.remove();
			}
			else{
				bullet.move();
				bullet.render();
			}
		}
		
		//GL11.glColor3f(1.0f, 0.0f, 0.0f);

		for(Iterator<Enemy> enemyIt = enemyList.iterator(); enemyIt.hasNext();){
			Enemy enemy = enemyIt.next();
			for(Iterator<Bullet> bulletIt = bulletList.iterator(); bulletIt.hasNext();){
				Bullet bullet = bulletIt.next();
				if(
						( bullet.getX() < ( enemy.getX() + 30 ) ) && 
						( bullet.getX() > ( enemy.getX() - 30 ) ) && 
						( bullet.getY() > ( enemy.getY() - 30 ) ) && 
						( bullet.getY() < ( enemy.getY() + 30 ) ) ){
					enemyIt.remove();
					if(!bullet.penetrate()){
						bulletIt.remove();
						player.resetShooterTimer();
						player.setCanShoot(true);
					}
				}
			}
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

