package gioco;


import java.io.Serializable;

public class QueryReturn implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float score;
	private String owner;
	private String name;
	private String letter;
	
	public QueryReturn(float score,String owner,String name){
		this.score=score;
		this.owner=owner;
		this.name=name;
	}
	
	public QueryReturn(String letter,String owner,String name){
		this.letter=letter;
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
	
	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}
	
	

}
