package org.lightcodex.plugin.game;

public class TimeCounter {
	
	private int second;
	private int minute;
	
	private int useCount;
	
	public TimeCounter() {
		second = 0;
		minute = 0;
		useCount = 0;
	}
	
	public void incUseCount() {
		useCount++;
	}
	
	public void decUseCount() {
		useCount--;
		if (useCount <= 0) {
			reset();
		}
	}
	
	public int getUseCount() {
		return useCount;
	}
	
	public void reset() {
		second = 0;
		minute = 0;
		useCount = 0;
	}
	
	public void incOneSecond() {
		second++;

		if (second >= 60) {
			second = 0;
			minute++;
			if (minute >= 60) {
				second = 0;
				minute = 0;
			}
		}
	}
	
	public int getSecond() {
		return second;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public String toString() {
		return String.format("%02d : %02d", minute, second);
	}
}
