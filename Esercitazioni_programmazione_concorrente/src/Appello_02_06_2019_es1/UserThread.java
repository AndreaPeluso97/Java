package Appello_02_06_2019_es1;

import java.util.concurrent.ThreadLocalRandom;

public class UserThread extends Thread {
	int myId;
	Pool thePool;
	public UserThread(int id, Pool t){
		this.myId=id;
		this.thePool=t;
		this.start();
	}
	public void run(){
		boolean finito=false;
		int need;
		while(!finito){
			need=(int)(10*Math.random());
			if(need<1){
				finito=true;
				System.out.println("User "+myId+" termina");
			} else {
				thePool.require(myId, need);
				try {
					sleep(ThreadLocalRandom.current().nextInt(10,21));
				} catch (InterruptedException e) {	}
				thePool.release(myId, need);				
			}
		}
	}
}


