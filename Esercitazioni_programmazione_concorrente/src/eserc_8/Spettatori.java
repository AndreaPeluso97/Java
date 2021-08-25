package eserc_8;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Spettatori extends UnicastRemoteObject implements SpettatoriInterf{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Spettatori(Registry r) throws RemoteException, NotBoundException {
		PartitaInterf stub=(PartitaInterf)r.lookup("partita");
		stub.add(this);
		
	}
	
	public static void main(String[] args) throws RemoteException, NotBoundException  {
		Registry r=LocateRegistry.getRegistry(null);
		new Spettatori(r);
	}

	public void statistiche(Statistiche obj){
		
		System.out.println("Falli: "+obj.falli+" Goal: "+obj.goal);
		
		
	}
	
}
