package Appello_21_06_es1;


public class Giocatore extends Thread {
	int mioId;
	Gioco ilGioco;
	public Giocatore(int id, Gioco g){
		mioId=id;
		ilGioco=g;
		System.out.println("Giocatore "+mioId+": creato");								
		start();
	}
	public void run() {
		boolean finito=false;
		while(!finito) {						
			if(ilGioco.partitaFinita()){
				if(ilGioco.vincitore()==mioId){
					System.out.println("Giocatore "+mioId+": Ho vinto!");						
				} else {
					System.out.println("Giocatore "+mioId+": Ho perso!");						
				}
				finito=true;				
			} else {
				try {
					Thread.sleep(200); // pensa la mossa
				} catch (InterruptedException e) {	}
				//System.out.println("Giocatore "+mioId+": gioco");						
				ilGioco.gioca(mioId, "la mia mossa");											
			}
		}
	}
}
