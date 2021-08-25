package Appello_2018_07_18_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Acquirente extends Remote{
	
	public void Notifica(String s) throws RemoteException;

}
