package Appello_19_12_2018_es3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Operation extends UnicastRemoteObject implements Interface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	double amount;
	

	public Operation(String t, double a) throws RemoteException{
		type=t; amount=a;
	}
	
	public String getType() {
		return this.type;
	}
	public double getAmount() {
		return this.amount;
	}
	
	public static void main(String[] args) throws RemoteException{
		
		Registry reg=LocateRegistry.createRegistry(1099);
		Operation obj = new Operation("prelievo", 100*Math.random());
		reg.rebind("server", obj);
		
	}
	
		
}
