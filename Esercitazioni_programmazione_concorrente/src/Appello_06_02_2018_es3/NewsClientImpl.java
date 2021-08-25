package Appello_06_02_2018_es3;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException; 

public class NewsClientImpl {
	private CliBuffer theBuf;
	private NewsService myServ;
	NewsClientImpl(NewsService news) {
		myServ= news;
		theBuf=new CliBuffer();
		try {
			NewsFetcherImpl newsMon=new NewsFetcherImpl(theBuf, (NewsServer) myServ);			
			System.out.println("client: fetcher created");
			((NewsServer) news).addObserver(newsMon);
			System.out.println("client: fetcher added to observers");
		} catch (RemoteException e1) { e1.printStackTrace(); }
		while(true){
			try {
				Thread.sleep(550);
			} catch (InterruptedException e) { e.printStackTrace(); }
			if(theBuf.newsIsFresh()){
				String st;
				try {
					st = theBuf.getNews();
					System.out.println("Client read "+st);
				} catch (InterruptedException e) { e.printStackTrace(); }
			} else {
				System.out.println("client: no fresh news");
			}
		}
//		System.out.println("Finito");
	}
	public static void main(String[] args) throws InterruptedException, RemoteException, NotBoundException {
		Registry registro = LocateRegistry.getRegistry(1099);
		NewsService news = (NewsService) registro.lookup("NEWS");
		new NewsClientImpl(news);
	}
}
