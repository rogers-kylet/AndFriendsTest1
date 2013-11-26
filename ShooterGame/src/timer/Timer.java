package timer;

public interface Timer {

	boolean countDown();

	void reset();

	int getStartValue();

	void setStartValue(int startValue);

	void setCurrentTime(int currentTime);

	int getCurrentTime();

}
