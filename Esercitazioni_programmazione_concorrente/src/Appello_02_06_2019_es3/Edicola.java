package Appello_02_06_2019_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Edicola extends Remote {
	public void nuovoArrivo(String rivista) throws RemoteException;
	public void add(AcquirenteInterf a) throws RemoteException;
}


