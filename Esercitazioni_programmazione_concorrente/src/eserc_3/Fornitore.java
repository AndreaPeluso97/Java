package eserc_3;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Fornitore {

	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException, InterruptedException{
		
		Registry r=LocateRegistry.getRegistry(null);
		Edicola e=(Edicola)r.lookup("Edicola");
		for(int i=0;i<15;i++){
			e.NuovoArrivo("rivista"+i);
			Thread.sleep(1000);
		}
		
	}
}
