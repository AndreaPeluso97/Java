package eserc_3;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class EdicolaImpl extends UnicastRemoteObject implements Edicola{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Acquirente> lista;

	protected EdicolaImpl() throws RemoteException {
		lista=new ArrayList<Acquirente>();
	}

	@Override
	public void add(Acquirente obj) {
		
		if(!lista.contains(obj)){
			lista.add(obj);
		}
		
	}

	@Override
	public void NuovoArrivo(String rivista) throws RemoteException {
		System.out.println(rivista);	
		for(Acquirente a:lista){
			a.Notifica(rivista);
		}
	}
	
	public static void main(String[] args) throws AccessException, RemoteException{
		
		Registry r=LocateRegistry.createRegistry(1099);
		Edicola obj=new EdicolaImpl();
		r.rebind("Edicola", obj);
		
	}

}
