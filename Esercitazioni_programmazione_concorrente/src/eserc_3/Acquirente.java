package eserc_3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Acquirente extends Remote{
	
	public void Notifica(String rivista) throws RemoteException;

}
