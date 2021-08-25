package Appello_02_06_2019_es3;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.ThreadLocalRandom;

public class Fornitore {

	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Edicola ed = (Edicola) registry.lookup("Edicola");
			for(int i=0; i<10; i++){
				int m = ThreadLocalRandom.current().nextInt(1,4);
				ed.nuovoArrivo("rivista"+m);
				try { Thread.sleep(ThreadLocalRandom.current().nextInt(1000,2001)); } catch (InterruptedException e) {	}
			}
		} catch (Exception e) {
			System.err.println("Fornitore: exception " + e.toString());
		}
	}
}
