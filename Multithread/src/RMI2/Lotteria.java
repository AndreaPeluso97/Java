package RMI2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Lotteria extends Remote {
	public String compraBiglietto(ClientInterface c) throws RemoteException;
}
