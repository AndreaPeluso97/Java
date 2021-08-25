package Appello_02_06_2019_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AcquirenteInterf extends Remote{
	public void Notifica(String rivista) throws RemoteException;
}
