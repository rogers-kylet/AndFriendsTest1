package timer;

/**
 * The object used for tracking time, typically used to set how long to wait until an action can be performed again.
 * @author Kyle Rogers
 *
 */
public interface Timer {

	boolean countDown();

	void reset();

	int getStartValue();

	void setStartValue(int startValue);

	void setCurrentTime(int currentTime);

	int getCurrentTime();

	boolean isStopped();

}
