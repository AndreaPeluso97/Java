package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface extends Remote{
	
	
	public boolean add(String parola,InterfacciaClient rf) throws RemoteException;
	public boolean cerca(String parola) throws RemoteException;
	
	
}
