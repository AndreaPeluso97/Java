package gioco;


import java.io.Serializable;

public class QueryReturn implements Serializable{
	private static final long serialVersionUID = 1L;
	private float score;
	private String owner;
	private String name;
	
	public QueryReturn(float score,String owner,String name){
		this.score=score;
		this.owner=owner;
		this.name=name;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}

