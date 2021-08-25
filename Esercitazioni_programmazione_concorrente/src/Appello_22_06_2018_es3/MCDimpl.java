package Appello_22_06_2018_es3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class MCDimpl extends UnicastRemoteObject implements MCD{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected MCDimpl() throws RemoteException {
		super();
	}

	public int mcd(int n, int m){
		int r;
		while(m != 0) {
			r = n % m;
			n = m; 
			m = r;
		}
		return n;
	}
	
	public static void main(String[] args) throws RemoteException{
		
		Registry reg=LocateRegistry.createRegistry(1099);
		MCDimpl obj=new MCDimpl();
		reg.rebind("server", obj);
		
		
	}
}
