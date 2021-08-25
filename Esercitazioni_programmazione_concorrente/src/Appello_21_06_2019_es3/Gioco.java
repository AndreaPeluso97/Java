package Appello_21_06_2019_es3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class Gioco extends UnicastRemoteObject implements GiocoInterf {
	private static final long serialVersionUID = 1L;
	Random rand = new Random();
	int chiEdiTurno;  // a chi tocca dei due giocatori
	boolean finito;
	int chiHaVinto;
	public Gioco(int t) throws RemoteException{
		chiEdiTurno = t;
		chiHaVinto=-99;
		finito=false;
	}
	public synchronized void gioca(int id, String mossa) {
		while(chiEdiTurno!=id){
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		// effetto della mossa
		if (rand.nextInt(10)>7){
			finito=true;
			chiHaVinto=id;
		}
		System.out.println("Gioco: giocatore "+id+" ha giocato");
		chiEdiTurno = 1-chiEdiTurno; // adesso tocca all'altro	
		System.out.println("Gioco: tocca a giocatore "+ chiEdiTurno);
	}
	public synchronized int aChiTocca() {
		return chiEdiTurno;
	}
	public synchronized boolean partitaFinita() {
		return finito;
	}
	public synchronized int vincitore() {
		return chiHaVinto;
	}
	
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.createRegistry(1099);
			Gioco obj = new Gioco(0);
			reg.rebind("Gioco", obj);
			System.out.println("Server bounded in registry");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
