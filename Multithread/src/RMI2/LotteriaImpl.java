package RMI2;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LotteriaImpl extends UnicastRemoteObject implements Lotteria{
	private static final long serialVersionUID = 1L;
	private String bigliettoVincente;
	
	ArrayList<ClientInterface> l;
	public LotteriaImpl() throws RemoteException {
		super();
		l=new ArrayList<ClientInterface>();
	}
	private void goOn() throws RemoteException{
		System.out.println("Lotteria: inizio ciclo estrazioni!");
		
		while(true){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {	}
		int x =(int) (1000000*Math.random());
		bigliettoVincente="biglietto"+x;
		System.out.println("Lotteria: estratto: "+bigliettoVincente);
		
		for(ClientInterface k:l){
			k.notifica(bigliettoVincente);
			
		}
		}
	}
	public static void main(String[] args) throws RemoteException {
		LotteriaImpl obj = null;
		
		
		try {
			obj = new LotteriaImpl();
			Registry registro = LocateRegistry.createRegistry(1099);
			registro.rebind("Lotteria", obj);
		} catch (RemoteException e) {
			System.err.println("Server: problema con registry RMI");
		}
		
		obj.goOn();
		
		System.err.println("Server ready");
	} 

	public String compraBiglietto(ClientInterface c) throws RemoteException {
		
		if(!l.contains(c)){
			
			l.add(c);
		}
		
		
		
		int x =(int)(1000000*Math.random());
		return "biglietto"+x;
	}
	
	
	
}

