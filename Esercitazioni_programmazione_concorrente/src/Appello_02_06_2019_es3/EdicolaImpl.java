package Appello_02_06_2019_es3;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class EdicolaImpl extends UnicastRemoteObject implements Edicola{
	
	ArrayList<AcquirenteInterf> list=new ArrayList<AcquirenteInterf>();
	public EdicolaImpl() throws RemoteException {
		super();
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
		for(AcquirenteInterf a:list){
			a.Notifica(rivista);
		}
	}

	public void add(AcquirenteInterf a) throws RemoteException {
		list.add(a);
	}
}

