package Appello_06_02_2018_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NewsFetcher extends Remote{
	public void newsNotify() throws RemoteException;
}