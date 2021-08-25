package Appello_2018_07_18_es3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class EdicolaImpl extends UnicastRemoteObject implements Edicola{

	ArrayList<Acquirente> lista;

	public EdicolaImpl() throws RemoteException {
		lista=new ArrayList<Acquirente>();
	}

	public static void main(String[] args) {
		try {
			Edicola obj = new EdicolaImpl();
			Registry registro = LocateRegistry.createRegistry(1099);
			registro.rebind("Edicola", obj);
		} catch (RemoteException e) {	}
		System.err.println("Server ready");
	} 
	public void nuovoArrivo(String rivista) throws RemoteException {
		System.out.println("Edicola: arrivata "+rivista);
		for(Acquirente a:lista){
			a.Notifica(rivista);
		}	
	}

	@Override
	public void add(Acquirente obj) throws RemoteException {
		if(!lista.contains(obj))
			lista.add(obj);

	}
}

