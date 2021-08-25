package Appello_21_06_2019_es3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Giocatore extends Thread {
	int mioId;
	GiocoInterf ilGioco;
	public Giocatore(int id){
		mioId=id;
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry();
			ilGioco = (GiocoInterf)reg.lookup("Gioco");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		System.out.println("Giocatore "+mioId+": creato");								
		start();
	}
	public void run() {
		boolean finito=false;
		while(!finito) {
			try {
				if(ilGioco.partitaFinita()){
					if(ilGioco.vincitore()==mioId){
						System.out.println("Giocatore "+mioId+": Ho vinto!");						
					} else {
						System.out.println("Giocatore "+mioId+": Ho perso!");						
					}
					finito=true;
					break;
				}
				if(!finito){
					// pensa la mossa
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {	}
					//System.out.println("Giocatore "+mioId+": gioco");						
					ilGioco.gioca(mioId, "la mia mossa");							
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
