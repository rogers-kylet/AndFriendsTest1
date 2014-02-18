package timer;

// TODO if/when we move the game from being based on frames to based on time, timer also needs to be updated for time
/**
 * The basic implementation of a timer. 
 * @author Kyle
 *
 */
public class BasicTimer implements Timer {

	private int startValue, currentTime;
	boolean ticking;
	
	public BasicTimer(int startValue) {
		this.startValue = startValue;
		this.ticking = false;
	}
	
	@Override
	public boolean countDown() {
		if(ticking) {
			currentTime = currentTime - 1;
			if(currentTime == 0) {
				ticking = false;
				return true;
			}
		}
		return false;
	}

	@Override
	public void reset() { 
		this.currentTime = this.startValue; 
		this.ticking = true;
	}
	
	@Override
	public boolean isStopped() {
		return !this.ticking;
	}
	
	@Override
	public int getStartValue() { return startValue; }

	@Override
	public void setStartValue(int startValue) { this.startValue = startValue; }

	@Override
	public int getCurrentTime() { return currentTime; }

	@Override
	public void setCurrentTime(int currentTime) { this.currentTime = currentTime; }
	
}
