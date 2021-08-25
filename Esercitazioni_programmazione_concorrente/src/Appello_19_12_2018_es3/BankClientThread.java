package Appello_19_12_2018_es3;

import java.io.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class BankClientThread extends Thread{
	
	Interface stub;
	
	public BankClientThread() throws AccessException, RemoteException, NotBoundException {
		Registry reg =LocateRegistry.getRegistry(null);
		stub=(Interface)reg.lookup("server");
		start();
	}
	
	public void run(){
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println("Client op1: (" + stub.getType() + stub.getAmount() + ")");

				System.out.println("Client_ op2: (" + stub.getType() + " " + stub.getAmount() + ")");
			}
		} catch (IOException e) {
			System.err.println("IO Exception");
		} 
	}
}

