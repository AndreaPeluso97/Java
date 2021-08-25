package Appello_03_06_2019_es3;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RubricaThread extends Thread{
	
	
	public RubricaThread() throws AccessException, RemoteException, NotBoundException{
		
		start();
	}
	
	
	public void run(){

		try{
		
		Registry reg=LocateRegistry.getRegistry(1099);
		
		RubricaInterface stub=(RubricaInterface)reg.lookup("RMI");
		
		stub.aggiungiNumero("Zia Pina", "+390212345678");
		stub.aggiungiNumero("Giorgio", "+390213579");
		stub.aggiungiNumero("Adalberto", "+390224680");
		stub.inRubrica("Zia Pina");
		stub.eliminaNumero("Zia Pina");
		stub.inRubrica("Giorgio");
		stub.eliminaNumero("Giorgio");



		String num = stub.trova("Zia Pina");
		System.out.println("Il numero della zia Pina e` "+ num);
		
		}catch(Exception e){}

		
	}

}
