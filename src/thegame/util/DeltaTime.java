package thegame.util;

public class DeltaTime {
	
	public static long getTargetTime(long millisToTarget) {
		return System.currentTimeMillis() + millisToTarget;
	}
	
}
