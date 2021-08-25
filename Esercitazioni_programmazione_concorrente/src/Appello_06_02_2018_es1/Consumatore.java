package Appello_06_02_2018_es1;

import java.util.concurrent.ThreadLocalRandom;

public class Consumatore extends Thread {
	MyBuffer thebuf;
	int Id;
	public Consumatore(int id, MyBuffer c){
		this.thebuf=c;
		this.Id=id;
	}
	public void run(){
		String line;
		for(int i=1; i<=33; ++i){
			try{
				line=thebuf.getNews(this.Id);
				System.out.println("Cons."+Id+" read "+line);
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
			} catch(InterruptedException e) {}
		}
	}

}
