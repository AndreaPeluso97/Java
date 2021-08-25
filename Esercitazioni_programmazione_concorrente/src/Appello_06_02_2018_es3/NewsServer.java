package Appello_06_02_2018_es3;



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NewsServer extends Remote{
	public String readNews() throws RemoteException;
	public void addObserver(NewsFetcher c) throws RemoteException;
}

