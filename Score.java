import java.util.*;

public class Score implements Comparable {
	public double score;
	public long time;
	
	public Score(double score) {
		this.score = score;
		this.time = System.currentTimeMillis();
	}
	
	public Score(double score, long time) {
		this.score = score;
		this.time = time;
	}
	
	public int compareTo(Object o) {
		return (int)(((Score)o).score-this.score);
	}
	
	public String toString() {
		return score+" "+time;
	}
}
