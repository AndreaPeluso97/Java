package Appello_2018_07_18_es3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AcquirenteImpl extends UnicastRemoteObject implements Acquirente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected AcquirenteImpl(Registry registro) throws RemoteException, NotBoundException {
		
		Edicola stub=(Edicola) registro.lookup("Edicola");
		
		stub.add(this);
		
	}

	@Override
	public void Notifica(String s) throws RemoteException {

		System.out.println(s);
		
	}
	
	public static void main(String[] args) throws RemoteException, NotBoundException {
		
		Registry reg=LocateRegistry.getRegistry(null);
		
		new AcquirenteImpl(reg);
		
		
		
	}

}
