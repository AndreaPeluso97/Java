package eserc_8;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Partita extends UnicastRemoteObject implements PartitaInterf{
	
	static ArrayList<SpettatoriInterf> lista;
	private static final long serialVersionUID = 1L;

	protected Partita() throws RemoteException {
		lista=new ArrayList<SpettatoriInterf>();
	}


	static Statistiche stat;
	
	public static void main(String[] args) throws RemoteException, InterruptedException{
		
		Registry r=LocateRegistry.createRegistry(1099);
		PartitaInterf p= new Partita();
		r.rebind("partita", p);
		while(true){
		stat=new Statistiche(ThreadLocalRandom.current().nextInt(0,10), ThreadLocalRandom.current().nextInt(0,10));
		for(SpettatoriInterf s:lista){
			
			s.statistiche(stat);
			
		}
		Thread.sleep(1000);
		}
	}
	
	

	@Override
	public void add(SpettatoriInterf obj) throws RemoteException {
		if(!lista.contains(obj)){
			lista.add(obj);
		}
	}

}
