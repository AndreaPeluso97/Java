package Appello_21_06_2019_es1;

import java.util.Random;

public class Gioco implements GiocoInterf {
	Random rand = new Random();
	int chiEdiTurno;  // a chi tocca dei due giocatori
	boolean finito;
	int chiHaVinto;
	public Gioco(int t){
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
		if(!finito){
			System.out.println("Gioco: giocatore "+id+" ha giocato");
			chiEdiTurno = 1-chiEdiTurno; // adesso tocca all'altro	
			
			System.out.println("Gioco: tocca a giocatore "+ chiEdiTurno);
		if (rand.nextInt(10)>8){
			finito=true;
			chiHaVinto=id;
		}
		notifyAll();
		}		
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
}
