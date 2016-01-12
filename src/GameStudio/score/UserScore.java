package GameStudio.score;

import java.io.Serializable;

public class UserScore implements Serializable, Comparable<UserScore> {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final int time;

	public UserScore(String name, int time) {
		this.name = name;
		this.time = time;
	}

	public String getName() {
		return name;
	}
	
	public int getTime() {
		return time;
	}

	@Override
	public int compareTo(UserScore o) {		
		return time - o.time;
//		if(time < o.time)
//			return -1;
//		else if(time > o.time)
//			return 1;
//		return 0;
	}
}
