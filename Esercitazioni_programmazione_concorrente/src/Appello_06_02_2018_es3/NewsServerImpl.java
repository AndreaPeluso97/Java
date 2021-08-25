package Appello_06_02_2018_es3;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NewsServerImpl extends UnicastRemoteObject implements NewsServer {
	private static final long serialVersionUID = 1L;
	private int ncount=0;
	private MyBuffer newsBuf;
	private NewsUpdater newsUpd;
	
	public NewsServerImpl() throws RemoteException {
		super();
		newsBuf=new MyBuffer();
		newsUpd=new NewsUpdater(newsBuf);
	}
	public synchronized String readNews() throws RemoteException {
		String theNews="";
		try {
			theNews = newsBuf.getNews();
		} catch (InterruptedException e) { e.printStackTrace(); }
		System.out.println("Server: sending "+theNews);
		return theNews;
	}
	public static void main(String args[]) {
		try {
			NewsServerImpl obj = new NewsServerImpl();
			Registry registro = LocateRegistry.createRegistry(1099);
			registro.rebind("NEWS", obj);
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
	public void addObserver(NewsFetcher c) throws RemoteException {
		newsUpd.addClient(c);
	}
}
