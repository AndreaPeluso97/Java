package Appello_07_09_2018_es1;


public class myMain {
	final static int NumTasks=7;
	public static void main(String[] args) throws InterruptedException {
		final int NumResources=20;
		Pool resourcePool = new Pool(NumResources,NumTasks);
		for(int i=0; i<NumTasks; i++){
			new UserThread(i, resourcePool);			
		}
	}
}
