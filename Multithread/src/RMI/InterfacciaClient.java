package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacciaClient extends Remote {
	
	public void Notifica(String parola) throws RemoteException;
		
		
	

}
