package RMI;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements InterfacciaClient{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static InterfacciaClient rf;


	public Client()throws RemoteException{
		
		rf=this;

	}


	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException{

		Registry reg=LocateRegistry.getRegistry("localhost");

		Interface interf=(Interface)reg.lookup("Server");

		
		new Client();


		for(int i=0;i<2;i++){

			boolean r=interf.add("salve",rf);

			if(r) System.out.println("aggiunto"); else System.out.println("non aggiunto");


			boolean r2=interf.cerca("ciao");

			if(r2) System.out.println("trovato"); else System.out.println("non trovato");

		}
		
		
	}


	public void Notifica(String parola) {
		System.out.println(parola);		
	}

}
