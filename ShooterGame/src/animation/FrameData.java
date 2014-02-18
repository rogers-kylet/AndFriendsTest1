package animation;

/**
 * The object used to store information related to the current animation frame.
 * @author Kyle Rogers
 *
 */
public interface FrameData {

	/**
	 * Gets the length of time that the current frame should be displayed.
	 * @return The length of time that the current frame should be displayed.
	 */
	int getDelay();

	/**
	 * Gets the number of the current frame relative to it's animation cycle.
	 * @return The position of the frame relative to it's animation cycle.
	 */
	int getFrameNumber();

	/**
	 * Gets the x position for the texture
	 * @return The x position for the texture.
	 */
	float getTexX();

	float getTexY();

	float getTexWidth();

	float getTexHeight();

}
