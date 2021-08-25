package RMI2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
	
	public void notifica(String biglietto) throws RemoteException;

}
