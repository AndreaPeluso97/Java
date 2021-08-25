package Appello_06_02_2018_es2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ThreadLocalRandom;

public class NewsUpdater extends Thread{
	private MyBuffer buf;
	private ObjectOutputStream outs;
	NewsUpdater(MyBuffer b, ObjectOutputStream os) {
		this.buf=b;
		this.outs=os;
		start();
	}
	public void run(){
		String str;
		System.out.println("Updater starts");
		while(true){
			System.out.println("Updater in ciclo");
			if(ThreadLocalRandom.current().nextInt(1, 10)<6){
				try {
					str="abc"+ThreadLocalRandom.current().nextInt(1, 100);
					buf.addNews(str);
					System.out.println("Updater generated "+str);
					outs.writeObject("newsAvailable");
				} catch (InterruptedException | IOException e) {e.printStackTrace();}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {	e.printStackTrace(); }
		}
	}
}
