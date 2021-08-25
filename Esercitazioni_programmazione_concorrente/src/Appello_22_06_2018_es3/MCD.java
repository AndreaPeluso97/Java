package Appello_22_06_2018_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface MCD extends Remote{
	public int mcd(int n, int m) throws RemoteException;
}
