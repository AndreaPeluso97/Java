package Appello_19_12_2018_es1;

import java.util.concurrent.Semaphore;


public class MyMain {
	public static void main(String[] args) throws InterruptedException {
		
		Semaphore[] s=new Semaphore[3];
		
		s[0]=new Semaphore(1);
		s[1]=new Semaphore(0);
		s[2]=new Semaphore(0);


		Thread player1 = new OperatingTask(1,s);
		Thread player2 = new OperatingTask(2,s);
		Thread player3 = new OperatingTask(3,s);
		player1.join();
		player2.join();
		player3.join();		
		System.out.println("Operations completed");
	}
}
