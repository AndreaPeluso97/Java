package Appello_01_22_2019_es3;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends Thread{
	
	private BancaInterface stub;
	
	public Client() throws AccessException, RemoteException, NotBoundException{

		Registry reg=LocateRegistry.getRegistry(1099);
		stub=(BancaInterface)reg.lookup("banca");
		
		start();
		
	}
	
	
	public void run(){
		
		
		try {
			stub.aggiorna(10, 1);
			stub.leggi(1);
			stub.saldoTotale();
		} catch (RemoteException e) {
		}
		
	}
	
	

}
