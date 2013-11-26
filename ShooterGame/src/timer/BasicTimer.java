package timer;

public class BasicTimer implements Timer {

	private int StartValue, currentTime;
	boolean ticking;
	
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
		this.currentTime = this.StartValue; 
		this.ticking = true;
	}
	
	@Override
	public int getStartValue() { return StartValue; }

	@Override
	public void setStartValue(int startValue) { StartValue = startValue; }

	@Override
	public int getCurrentTime() { return currentTime; }

	@Override
	public void setCurrentTime(int currentTime) { this.currentTime = currentTime; }
	
}
