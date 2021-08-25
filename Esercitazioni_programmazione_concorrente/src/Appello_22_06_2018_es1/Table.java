package Appello_22_06_2018_es1;

import java.util.concurrent.ThreadLocalRandom;

public class Table {
	private boolean isFinita=false;
	private int numPlayers;
	private int nextPlayer;
	Table(int n, int first){
		isFinita=false;
		numPlayers=n;
		nextPlayer=first;
	}
	private int whoIsNext(int currentPlayer){
		int p=nextPlayer+1;
		if(p>numPlayers){
			p=1;
		}
		return p; 
	}
	synchronized void mossa(int playerId, int dado1, int dado2){
		System.out.println("Table: giocatore "+playerId+" richiede mossa con "+dado1+" "+dado2);
		while(playerId != nextPlayer){
			try {
				wait();
			} catch (InterruptedException e) {	e.printStackTrace(); }
		}
		if(!isFinita){
			double x = Math.random();
			isFinita=(x<0.2);
			System.out.println("Table: giocatore "+playerId+" ha mosso e la partita "+(isFinita?"":"non")+" e` finita");
			if(dado1!=dado2){
				nextPlayer=whoIsNext(playerId);
			}
		} else {
			System.out.println("Table: giocatore "+playerId+" non ha mosso perche' la partita e` finita");			
			nextPlayer=whoIsNext(playerId);
		}
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
		} catch (InterruptedException e1) {	}
		System.out.println("Table: adesso tocca a giocatore "+nextPlayer);
		notifyAll();
	}
	boolean finita(){
		return isFinita;
	}
}
