package Appello_06_02_2018_es1;

import java.util.concurrent.ThreadLocalRandom;

public class Produttore extends Thread {
	MyBuffer thebuf;
	public Produttore(MyBuffer c){
		this.thebuf=c;
	}
	public void run(){
		String line;
		for(int i=1; i<=100; ++i){
			line="abcde"+i;
			try{
				thebuf.addNews(line);
				System.out.println("P added "+ line);
				Thread.sleep(ThreadLocalRandom.current().nextInt(10, 100));
			} catch(InterruptedException e) {}
		}
	}

}
