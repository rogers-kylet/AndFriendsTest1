package animation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import timer.BasicTimer;
import timer.Timer;

// TODO should basic animation hold a type o animation, or all animations.. probaly all..
/**
 * The object used to handle the players animation. 
 * Includes all of the information related to setting up the frames for the spritesheet.
 * @author Kyle Rogers
 *
 */
public class PlayerAnimation {

	boolean isLeft;
	Map<String, List<FrameData>> frameListMap;
	int frameDelay;
	String currentAnimation;
	FrameData currentFrame;
	
	private Timer frameTimer;
	
	private static String IDLEFRAMELIST = "idle";
	private static String WALKFRAMELIST = "walk";
	
	public PlayerAnimation() {
		frameListMap = new HashMap<String, List<FrameData>>();
				
		frameListMap.put(IDLEFRAMELIST, createIdleFrameList());
		frameListMap.put(WALKFRAMELIST, createWalkFrameList());
		
		this.currentAnimation = IDLEFRAMELIST;
		this.currentFrame = frameListMap.get(this.currentAnimation).get(0);
		
		this.frameTimer = new BasicTimer(this.currentFrame.getDelay());
		this.frameTimer.reset();
	}

	private List<FrameData> createIdleFrameList() {
		List<FrameData> idleFrameList = new LinkedList<FrameData>();
		FrameData idleFrame1 = new BasicFrameData(165f, 215f, 310f, 430f, 5, 0);
		FrameData idleFrame2 = new BasicFrameData(495f, 215f, 310f, 430f, 10, 1);
		FrameData idleFrame3 = new BasicFrameData(825f, 215f, 310f, 430f, 25, 2);
		FrameData idleFrame4 = new BasicFrameData(1155f, 215f, 310f, 430f, 10, 3);
		FrameData idleFrame5 = new BasicFrameData(1485f, 215f, 310f, 430f, 10, 4);
		FrameData idleFrame6 = new BasicFrameData(1815f, 215f, 310f, 430f, 10, 5);
		FrameData idleFrame7 = new BasicFrameData(2145f, 215f, 310f, 430f, 10, 6);
		FrameData idleFrame8 = new BasicFrameData(2475f, 215f, 310f, 430f, 10, 7);
		FrameData idleFrame9 = new BasicFrameData(2805f, 215f, 310f, 430f, 5, 8);
		
		idleFrameList.add(idleFrame1);
		idleFrameList.add(idleFrame2);
		idleFrameList.add(idleFrame3);
		idleFrameList.add(idleFrame4);
		idleFrameList.add(idleFrame5);
		idleFrameList.add(idleFrame6);
		idleFrameList.add(idleFrame7);
		idleFrameList.add(idleFrame8);
		idleFrameList.add(idleFrame9);
		return idleFrameList;
	}
	
	private List<FrameData> createWalkFrameList() {
		List<FrameData> walkFrameList = new LinkedList<FrameData>();
		
		FrameData walkFrame1 = new BasicFrameData(165f, 645f, 310f, 430f, 3, 0);
		FrameData walkFrame2 = new BasicFrameData(495f, 645f, 310f, 430f, 3, 1);
		FrameData walkFrame3 = new BasicFrameData(825f, 645f, 310f, 430f, 3, 2);
		FrameData walkFrame4 = new BasicFrameData(1155f, 645f, 310f, 430f, 3, 3);
		FrameData walkFrame5 = new BasicFrameData(1485f, 645f, 310f, 430f, 3, 4);
		FrameData walkFrame6 = new BasicFrameData(1815f, 645f, 310f, 430f, 3, 5);
		FrameData walkFrame7 = new BasicFrameData(2145f, 645f, 310f, 430f, 3, 6);
		FrameData walkFrame8 = new BasicFrameData(2475f, 645f, 310f, 430f, 3, 7);
		FrameData walkFrame9 = new BasicFrameData(2805f, 645f, 310f, 430f, 3, 8);
		FrameData walkFrame10 = new BasicFrameData(3135f, 645f, 310f, 430f, 3, 9);
		FrameData walkFrame11 = new BasicFrameData(3465f, 645f, 310f, 430f, 3, 10);
		FrameData walkFrame12 = new BasicFrameData(3795f, 645f, 310f, 430f, 3, 11);
		FrameData walkFrame13 = new BasicFrameData(4125f, 645f, 310f, 430f, 3, 12);
		FrameData walkFrame14 = new BasicFrameData(4455f, 645f, 310f, 430f, 3, 13);
		FrameData walkFrame15 = new BasicFrameData(4785f, 645f, 310f, 430f, 3, 14);
		FrameData walkFrame16 = new BasicFrameData(5115f, 645f, 310f, 430f, 3, 15);
		
		walkFrameList.add(walkFrame1);
		walkFrameList.add(walkFrame2);
		walkFrameList.add(walkFrame3);
		walkFrameList.add(walkFrame4);
		walkFrameList.add(walkFrame5);
		walkFrameList.add(walkFrame6);
		walkFrameList.add(walkFrame7);
		walkFrameList.add(walkFrame8);
		walkFrameList.add(walkFrame9);
		walkFrameList.add(walkFrame10);
		walkFrameList.add(walkFrame11);
		walkFrameList.add(walkFrame12);
		walkFrameList.add(walkFrame13);
		walkFrameList.add(walkFrame14);
		walkFrameList.add(walkFrame15);
		walkFrameList.add(walkFrame16);
		
		return walkFrameList;
	}
	public FrameData getCurrentFrame() { 
		if(!frameTimer.isStopped()) {
			this.frameTimer.countDown();
		} else {
			List<FrameData> currentFrameDataList = frameListMap.get(currentAnimation);
			if(this.currentFrame.getFrameNumber() == currentFrameDataList.size()-1) {
				this.currentFrame = currentFrameDataList.get(0);
			} else {
				this.currentFrame = currentFrameDataList.get(currentFrame.getFrameNumber() + 1);
			}
			this.frameTimer.setStartValue(currentFrame.getDelay());
			this.frameTimer.reset();
			//System.out.println("The current frame is: " + (this.currentFrame.getFrameNumber() + 1));
		}
		return currentFrame; 
	}
	
	public void setCurrentAnimation(String currentAnimation) { 
		if(!this.getCurrentAnimation().equals(currentAnimation)) {
			this.currentAnimation = currentAnimation; 
			this.currentFrame = this.frameListMap.get(this.currentAnimation).get(0);
		}
	}

	public boolean isLeft() { return isLeft; }
	public void setLeft(boolean isLeft) { this.isLeft = isLeft; }
	public int getFrameDelay() { return frameDelay; }
	public void setFrameDelay(int frameDelay) { this.frameDelay = frameDelay; }
	public Map<String, List<FrameData>> getFrameListMap() {	return frameListMap; }
	public void setFrameListMap(Map<String, List<FrameData>> frameListMap) { this.frameListMap = frameListMap; }
	public String getCurrentAnimation() { return currentAnimation; }
	public void setCurrentFrame(FrameData currentFrame) { this.currentFrame = currentFrame; }

	
}
