package animation;

/**
 * Basic Implementation of the frame data object, used for information related to the current animation frame
 * @author Kyle Rogers
 *
 */
public class BasicFrameData implements FrameData {

	float texX, texY, texWidth, texHeight;
	int delay, frameNumber;

	BasicFrameData(float texX, float texY, float texWidth, float texHeight, int delay, int frameNumber) {
		this.texX = texX;
		this.texY = texY;
		this.texWidth = texWidth;
		this.texHeight = texHeight;
		this.delay = delay;
		this.frameNumber = frameNumber;
	}
	
	public float getTexX() {
		return texX;
	}

	public void setTexX(float texX) {
		this.texX = texX;
	}

	public float getTexY() {
		return texY;
	}

	public void setTexY(float texY) {
		this.texY = texY;
	}

	public float getTexWidth() {
		return texWidth;
	}

	public void setTexWidth(float texWidth) {
		this.texWidth = texWidth;
	}

	public float getTexHeight() {
		return texHeight;
	}

	public void setTexHeight(float texHeight) {
		this.texHeight = texHeight;
	}

	@Override
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	
}
