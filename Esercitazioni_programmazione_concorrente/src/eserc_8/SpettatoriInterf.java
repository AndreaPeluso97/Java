package eserc_8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpettatoriInterf extends Remote{
	public void statistiche(Statistiche obj) throws RemoteException;

}
