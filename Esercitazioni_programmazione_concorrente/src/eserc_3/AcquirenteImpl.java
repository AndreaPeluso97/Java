package eserc_3;

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

	protected AcquirenteImpl(Registry r) throws RemoteException, NotBoundException {
		Edicola stub=(Edicola)r.lookup("Edicola");
		stub.add(this);
	}

	public static void main(String[] args) throws RemoteException, NotBoundException{
		
		Registry r=LocateRegistry.getRegistry(null);
		new AcquirenteImpl(r);
		
	}

	@Override
	public void Notifica(String rivista) {
		System.out.println(rivista);		
	}
	

}
