package Appello_2018_07_18_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Edicola extends Remote {
	public void nuovoArrivo(String rivista) throws RemoteException;
	public void add(Acquirente obj) throws RemoteException;
}
