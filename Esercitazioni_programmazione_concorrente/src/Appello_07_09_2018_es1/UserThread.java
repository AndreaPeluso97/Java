package Appello_07_09_2018_es1;

import java.util.concurrent.ThreadLocalRandom;

public class UserThread extends Thread {
	int myId;
	Pool thePool;
	public UserThread(int id, Pool t){
		this.myId=id;
		this.thePool=t;
		this.start();
	}
	private int neededResources(){
		int howMany=0;
		int temp=(int)(5*Math.random());
		if(myId<2){
			howMany=temp+10; // i task con id basso vogliono tante risorse
		} else {
			howMany=temp;
		}
		return howMany;
	}
	public void run(){
		int need;
		while(true){
			need=neededResources();
			thePool.require(myId, need);
			try {
				sleep(ThreadLocalRandom.current().nextInt(10,21));
			} catch (InterruptedException e) {	}
			thePool.release(myId, need);				
		}
	}
}

