package Appello_02_06_2019_es3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Acquirente extends UnicastRemoteObject implements AcquirenteInterf{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Acquirente(Edicola e) throws RemoteException {
		super();
		e.add(this);
	}

	public void Notifica(String rivista) throws RemoteException {
		System.out.println(rivista);
	}
	
	public static void main(String[] args) throws RemoteException, NotBoundException{
		Registry r=LocateRegistry.getRegistry(1099);
		Edicola e=(Edicola)r.lookup("Edicola");
		new Acquirente(e);
	}

}
