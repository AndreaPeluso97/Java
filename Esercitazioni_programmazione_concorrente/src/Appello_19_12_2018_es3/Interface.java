package Appello_19_12_2018_es3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface extends Remote{
	
	
	public String getType() throws RemoteException;
	
	public double getAmount() throws RemoteException;




}
