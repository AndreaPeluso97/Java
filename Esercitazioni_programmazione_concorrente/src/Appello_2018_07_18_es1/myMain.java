package Appello_2018_07_18_es1;


public class myMain {
	public static void main(String[] args) throws InterruptedException {
		final int NumResources=20;
		Pool resourcePool = new Pool(NumResources);
		for(int i=0; i<5; i++){
			new UserThread(i, resourcePool);			
		}
	}
}

