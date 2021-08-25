package Appello_06_02_2018_es3;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NewsUpdater extends Thread{
	private MyBuffer buf;
	private List<NewsFetcher> flist;
	NewsUpdater(MyBuffer b) {
		this.buf=b;
		this.flist=new LinkedList<NewsFetcher>();
		start();
	}
	public void addClient(NewsFetcher c){
		flist.add(c);
		System.out.println("updater: added observer");
	}
	public void run(){
		while(true){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {	e.printStackTrace(); }
			if(ThreadLocalRandom.current().nextInt(1, 10)<4){
				try {
					String st="abc"+ThreadLocalRandom.current().nextInt(1, 100);
					buf.addNews(st);
					System.out.println("updater: stored "+st);
					for(NewsFetcher nf: flist){
						try {
							nf.newsNotify();
							System.out.println("updater: notification sent!");
						} catch (RemoteException e) { e.printStackTrace(); }
					}
				} catch (InterruptedException e) {	}
			}
		}
	}
}