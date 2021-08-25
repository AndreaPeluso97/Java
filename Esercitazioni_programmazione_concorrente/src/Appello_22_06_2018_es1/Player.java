package Appello_22_06_2018_es1;

import java.util.concurrent.ThreadLocalRandom;

public class Player extends Thread {
	int myId;
	Table myTable;
	public Player(int id, Table t){
		this.myId=id;
		this.start();
		this.myTable=t;
	}
	public void run(){
		boolean doppio=false;
		int dado1;
		int dado2;
		while(!myTable.finita()){
			doppio=true;
			while(doppio){
				dado1=(int)(6*Math.random());
				dado2=(int)(6*Math.random());
				doppio=(dado1==dado2);
				myTable.mossa(myId, dado1, dado2);
				System.out.println("Giocatore "+myId+" ha mosso ");
			}
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
			} catch (InterruptedException e1) {	}

		}
	}
	
	
}

