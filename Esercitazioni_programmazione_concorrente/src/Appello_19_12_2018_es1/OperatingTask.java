package Appello_19_12_2018_es1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class OperatingTask extends Thread {
	int myId;
	Semaphore[] sem;
	public OperatingTask(int id,Semaphore[] s){
		this.myId=id;
		this.start();
		this.sem=s;
		
	}
	public void run(){
		int Random;
		
		while(true){
			try{
				
				sem[myId].acquire();
				
			}catch(Exception e){}
			System.out.println("Operator "+myId+" starts elaboration ");
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
			} catch (InterruptedException e1) {	}
			System.out.println("Operator "+myId+" ends elaboration ");
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
			} catch (InterruptedException e1) {	}
			
			Random=ThreadLocalRandom.current().nextInt(0,3);
			sem[Random].release();
		}
	}
}

