import java.io.Serializable;

//�ǦC�ƪ�Grade���󥻤H
public class Grade implements Serializable {
	private int id;
	private int score;
	
	public Grade(int id, int score) {
		this.id = id;
		this.score = score;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
}
