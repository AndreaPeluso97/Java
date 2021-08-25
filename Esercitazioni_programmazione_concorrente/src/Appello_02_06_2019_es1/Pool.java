package Appello_02_06_2019_es1;

public class Pool {

	int Nr=0;

	public Pool(int numResources) {
		Nr=numResources;
	}

	public synchronized void require(int myId, int need) {
		System.out.println("Pool: user "+myId+" richiede "+need+" risorse");
		while(need>=Nr){
			System.out.println("Pool: user "+myId+" attende");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Nr-=need;
		System.out.println("Pool: user "+myId+" ottiene "+need+" risorse; ne restano "+Nr);
	}

	public synchronized void release(int myId, int need) {
		System.out.println("Pool: user "+myId+" rilascia "+need+" risorse");
		Nr+=need;
		System.out.println("Pool: user "+myId+" rilascia "+need+" risorse; disponibili ora "+Nr);
		notifyAll();
	}

}
