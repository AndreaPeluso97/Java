package eserc_3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Edicola extends Remote{
	
	public void add(Acquirente obj) throws RemoteException;
	public void NuovoArrivo(String rivista) throws RemoteException;
}
