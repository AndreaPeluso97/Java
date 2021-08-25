package Appello_2018_07_18_es1;

public class Pool {

	
	private int disponibili=0;
	public Pool(int numResources) {
		disponibili=numResources;
	}

	public synchronized void require(int myId, int need) {
		
		if(need>disponibili)
		{
			
			try{wait();}catch(Exception e){}
		}
		
		disponibili-=need;
		
	}

	public synchronized void release(int myId, int need) {
		
		disponibili+=need;

		notifyAll();
		
	}

}
