package RMI2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ThreadLocalRandom;

public class Giocatore extends UnicastRemoteObject implements ClientInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Lotteria stub;
	private String myName;
	public Giocatore(Lotteria e) throws RemoteException {
		stub = e;
		int n =ThreadLocalRandom.current().nextInt(1,101);
		myName="giocatore_"+n;
		String s = stub.compraBiglietto(this);
		System.out.println(myName+": ho comprato "+ s);
		
	}

	public static void main(String[] args) throws RemoteException  {
		try {
			Registry registro = LocateRegistry.getRegistry(1099);
			Lotteria stub = (Lotteria) registro.lookup("Lotteria");
			new Giocatore(stub);
		} catch (RemoteException | NotBoundException e) {	}  
	}
	
	
	public void notifica(String biglietto){
		
		System.out.println("Il biglietto vincente è: "+biglietto);
		
		
	}
}
