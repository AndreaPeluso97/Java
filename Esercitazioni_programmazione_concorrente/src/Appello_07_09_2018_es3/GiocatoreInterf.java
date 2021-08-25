package Appello_07_09_2018_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GiocatoreInterf extends Remote{
	
	public void stampa(String s) throws RemoteException;

}
