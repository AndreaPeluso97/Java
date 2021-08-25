package Appello_07_09_2018_es1;


public class Pool implements PoolInterface {
	private final int maxWait=10;

	private int available=0;
	private int[] waitingList;
	private int numTasks;
	Pool(int n,int nTasks){
		available=n;
		numTasks=nTasks;
		waitingList= new int[nTasks];
		for(int i=0; i<nTasks; i++){
			waitingList[i]=0;
		}
		System.out.println("Pool inizializzata con "+available+" risorse");
	}
	
	private boolean someoneElseStarving(int userId){
		for(int i=0; i<numTasks; i++){
			if(waitingList[i]>=maxWait && i!=(userId)){
				return true;
			}
		}
		return false;
	}
	
	public synchronized void require(int userId, int qty){
		System.out.println("Pool: user "+userId+" richiede "+qty+" risorse");
		while(available<qty || someoneElseStarving(userId)){
			System.out.println("Pool: user "+userId+" attende");
			try {
				if(!someoneElseStarving(userId)){
					waitingList[userId]++;
					System.out.println(waitingList[userId]);
			}
				wait();
			} catch (InterruptedException e) {	e.printStackTrace(); }
		}
		available-=qty;
		System.out.println("Pool: user "+userId+" ottiene "+qty+" risorse; ne restano "+available);
	}
	public synchronized void release(int userId, int qty){
		System.out.println("Pool: user "+userId+" rilascia "+qty+" risorse");
		available+=qty;
		waitingList[userId]=0;

		System.out.println("Pool: user "+userId+" rilascia "+qty+" risorse; disponibili ora "+available);
		notifyAll();
	}
}
