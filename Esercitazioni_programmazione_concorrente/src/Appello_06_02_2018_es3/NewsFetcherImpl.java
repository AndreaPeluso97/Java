package Appello_06_02_2018_es3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NewsFetcherImpl extends UnicastRemoteObject implements NewsFetcher{
	private static final long serialVersionUID = 1L;
	private CliBuffer theBuf;
	private NewsServer myServ;
	NewsFetcherImpl(CliBuffer buf, NewsServer serv) throws RemoteException{
		theBuf=buf;
		myServ=serv;
	}
	public void newsNotify() throws RemoteException {
		try {
			String st=myServ.readNews();
			System.out.println("fetcher read: "+st);
			theBuf.addNews(st);
			System.out.println("fetcher wrote in buffer: "+st);					
		} catch (RemoteException | InterruptedException e1) { 
			System.err.println("fetcher: problem!");
			e1.printStackTrace();
		}
	}
}

