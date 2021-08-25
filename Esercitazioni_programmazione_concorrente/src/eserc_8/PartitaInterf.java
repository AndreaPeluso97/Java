package eserc_8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PartitaInterf extends Remote{
	public void add(SpettatoriInterf obj) throws RemoteException;

}
