 package Appello_01_22_2019_es1;


import java.util.concurrent.ThreadLocalRandom;

public class Operazione extends Thread {

	int myId;
	Banca laBanca;
	public Operazione(int id, Banca b){
		this.laBanca=b;
		this.myId=id;
		this.start();
	}
	public void run(){
		int valoreDaTrasferire;
		int sorgente, destinazione;

		for(int j=0; j<=2000; j++){
			for(int i=1; i<=2000; i++){
				valoreDaTrasferire = ThreadLocalRandom.current().nextInt(1, 10);
				sorgente = ThreadLocalRandom.current().nextInt(0, Banca.numCC);
				destinazione=sorgente;
				while(destinazione==sorgente){
					destinazione = ThreadLocalRandom.current().nextInt(0, Banca.numCC);	
				}
				laBanca.trasferimento(valoreDaTrasferire, sorgente, destinazione);
				//			System.out.println("op "+myId+": Trasferito "+valoreDaTrasferire+" da "+conti[sorgente].leggiNome()+" a "+conti[destinazione].leggiNome());
				//			try {Thread.sleep(ThreadLocalRandom.current().nextInt(1, 300));} catch (InterruptedException e) { }	
			}
		}
	}
}

