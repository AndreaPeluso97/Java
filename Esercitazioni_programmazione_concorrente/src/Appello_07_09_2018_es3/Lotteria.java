package Appello_07_09_2018_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Lotteria extends Remote {
	public String compraBiglietto(GiocatoreInterf gInterf) throws RemoteException;
}
