package Appello_21_06_2019_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GiocoInterf extends Remote{
	public void gioca(int id, String mossa) throws RemoteException;
	public boolean partitaFinita() throws RemoteException;
	public int aChiTocca() throws RemoteException;
	public int vincitore() throws RemoteException;
}
	