package eserc_8;

import java.io.Serializable;


public class Statistiche implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	int falli,goal;
	
	public Statistiche(int goal,int falli){
		
		this.goal=goal;
		this.falli=falli;
		
	}
	
	public int getGoal(){
		return goal;
		
	}
	
	public int getFalli(){
		
		return falli;
		
	}	
	
	

}
