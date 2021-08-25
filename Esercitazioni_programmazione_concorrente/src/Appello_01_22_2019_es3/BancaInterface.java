package Appello_01_22_2019_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancaInterface extends Remote{
	
	public int leggi(int cc) throws RemoteException;
	
	public void aggiorna(int valore, int cc) throws RemoteException;
	
	public void saldoTotale() throws RemoteException;
	
	

}
